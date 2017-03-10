package com.rujianbin.provider.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rujianbin.provider.mongo.dao.IProductDao;
import com.rujianbin.provider.mongo.entity.Product;
import com.rujianbin.provider.mongo.entity.Product.ProductAttr;
import com.rujianbin.provider.mybatis.entity.Person;

@RestController
@RequestMapping("/provider")
public class ProviderController {

		@Autowired
		private RedisCacheManager manage;
		
		@Resource(name="productDaoImpl")
		private IProductDao productDao;
	
		//127.0.0.1:7070/rujianbin-provider/provider/hellow
		@RequestMapping("/hellow")  
	    public String hellow(){ 
			Query query = new Query();
			List<Product> list =  productDao.find(query);
	        return "哈喽，Spring Boot ！- rujianbin-provider";  
	    }  
		
		private List<Product> getProduct(){
			Product p1 = new Product();
			p1.setId(1000L);
			p1.setName("拉菲 至尊系列葡萄酒  500ml");
			p1.setCategroyId(5L);
			p1.setCategoryName("葡萄酒");
			List<ProductAttr> attrList1 = new ArrayList<ProductAttr>();
			ProductAttr attr1 = new ProductAttr("brand",1L,"拉菲");
			ProductAttr attr2 = new ProductAttr("series",3L,"拉菲至尊系列");
			attrList1.add(attr1);
			attrList1.add(attr2);
			p1.setAttrList(attrList1);
			
			Product p2 = new Product();
			p2.setId(1001L);
			p2.setName("张裕 经典系列葡萄酒  600ml");
			p2.setCategroyId(5L);
			p2.setCategoryName("葡萄酒");
			List<ProductAttr> attrList2 = new ArrayList<ProductAttr>();
			ProductAttr attr11 = new ProductAttr("brand",1L,"张裕");
			ProductAttr attr22 = new ProductAttr("series",3L,"张裕经典系列");
			attrList2.add(attr11);
			attrList2.add(attr22);
			p2.setAttrList(attrList2);
			
			Product p3 = new Product();
			p3.setId(1002L);
			p3.setName("农夫果园 胡萝卜果蔬  600ml");
			p3.setCategroyId(7L);
			p3.setCategoryName("果蔬饮料");
			List<ProductAttr> attrList3 = new ArrayList<ProductAttr>();
			ProductAttr attr111 = new ProductAttr("brand",5L,"农夫果园");
			ProductAttr attr222 = new ProductAttr("juice-content",8L,"70%");
			attrList3.add(attr111);
			attrList3.add(attr222);
			p3.setAttrList(attrList3);
			
			List<Product> pList = new ArrayList<Product>();
			pList.add(p1);
			pList.add(p2);
			pList.add(p3);
			return pList;
		}
		
		//127.0.0.1:7070/rujianbin-provider/provider/session
		@RequestMapping("/session")  
	    public String session(HttpServletRequest request){  
			HttpSession session = request.getSession();
			session.setAttribute("address", "三江口杨善路");
			session.setAttribute("person-info", new Person("张三",12));
			Cache cache = manage.getCache("namespace1");
			cache.put("my-current-date", System.currentTimeMillis());
	        return "session id ---->"+session.getId(); 
	    }
		
		//127.0.0.1:7070/rujianbin-provider/provider/freemark
		@RequestMapping("/freemark")
	    public String welcome(ModelMap model) {
	        model.put("time", new Date());
	        model.put("message", "hello freemark");
	        return "index";
	    }
}
