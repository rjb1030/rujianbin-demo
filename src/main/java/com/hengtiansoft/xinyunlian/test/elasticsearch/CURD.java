package com.hengtiansoft.xinyunlian.test.elasticsearch;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component("curd")
public class CURD implements InitializingBean{

	private Bean1 bean1;
	
	@Resource(name="elasticsearchTemplate")
	private ElasticsearchTemplate template;

	@Override
	public void afterPropertiesSet() throws Exception {
//		bean1 = new Bean1();
//		bean1.setId(1L);
//		bean1.setCrerateDate(new Date());
//		bean1.setName("加多宝 凉茶 火锅伴侣");
//		bean1.setIntroduction("加多宝凉茶可以清热解毒，是旅行家居的必备良药，欢迎采购 库存充足");
//		bean1.setPrice(new BigDecimal(3.5));
//		bean1.setCost(2.5D);
//		bean1.setScore(98.3F);
//		bean1.setIsGift(false);
//		bean1.setInventoryNum(1000);
//		
//		Bean2 bean2_1 = new Bean2("中国",100L,new Date());
//		Bean2 bean2_2 = new Bean2("日本",200L,new Date());
//		Bean2 bean2_3 = new Bean2("德国",300L,new Date());
//		bean1.setBean2(bean2_1);
//		bean1.setBean2List(Lists.newArrayList(bean2_1,bean2_2,bean2_3));
//		
//		Bean3 bean3_1 = new Bean3("名称1",31);
//		Bean3 bean3_2 = new Bean3("名称2",32);
//		Bean3 bean3_3 = new Bean3("名称3",33);
//		Map<String,Bean3> map = new HashMap<String,Bean3>();
//		map.put(bean3_1.getName(), bean3_1);
//		map.put(bean3_2.getName(), bean3_2);
//		map.put(bean3_3.getName(), bean3_3);
//		bean1.setBeansMap(map);
//		
//		
//		IndexQuery indexQuery = new IndexQueryBuilder().withObject(bean1)
//				.withIndexName("es-bean1").withId(bean1.getId().toString())
//				.withType("es-bean1-type").build();
//		
//		template.bulkIndex(Arrays.asList(indexQuery));
//		template.refresh("es-bean1");
//		
//		
//		BoolQueryBuilder builder = new BoolQueryBuilder();
//		builder.must(nestedQuery("manuscripts", termQuery("manuscripts.status", "ACCEPTED")))
//		.must(nestedQuery("manuscripts.role", termQuery("manuscripts.role.name", "role3")));
//
//		SearchQuery searchQuery = new NativeSearchQueryBuilder()
//				.withQuery(builder).build();
//
//		Page<Bean1> page  = template.queryForPage(searchQuery, Bean1.class);
	}
	
	
}
