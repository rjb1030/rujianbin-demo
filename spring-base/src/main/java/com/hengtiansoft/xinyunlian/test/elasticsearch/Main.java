package com.hengtiansoft.xinyunlian.test.elasticsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@SuppressWarnings({"resource"})
public class Main {

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		ElasticsearchTemplate template = context.getBean("elasticsearchTemplate", ElasticsearchTemplate.class);
		CURD<Bean1> curd = new CURD<Bean1>(template,Bean1.class);
		
//		curd.deleteByClass(Bean1.class);
//		List<Long> ids = new ArrayList<Long>();
//		for(long i=1;i<=10;i++){
//			ids.add(i);
//		}
//		Long l1 = System.currentTimeMillis();
//		curd.insertOrUpdate(ids);
//		Long l2 = System.currentTimeMillis();
//		System.out.println("耗时="+(l2-l1));

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("cn_name","凉茶");
		Page<Bean1> page = curd.queryForBool(map);
		if(page!=null && page.getContent()!=null && !page.getContent().isEmpty()){
			for(Bean1 b : page.getContent()){
				System.out.println("id="+b.getId());
			}
				
		}else{
			System.out.println("查询结果为空");
		}
		System.exit(1);
		
	}
	
	
}
