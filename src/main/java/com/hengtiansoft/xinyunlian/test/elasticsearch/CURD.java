package com.hengtiansoft.xinyunlian.test.elasticsearch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import com.google.common.collect.Lists;

@SuppressWarnings({"unchecked","rawtypes"})
public class CURD{


	private ElasticsearchTemplate template;
	
	public CURD(ElasticsearchTemplate template){
		this.template=template;
	}

	public Bean1 initBean(Long id) {
		Bean1 bean1 = new Bean1();
		bean1.setId(id);
		bean1.setCrerateDate(new Date());
		bean1.setName(id+"_加多宝 凉茶 火锅伴侣-update");
		bean1.setIntroduction(id+"_加多宝凉茶可以清热解毒，是旅行家居的必备良药，欢迎采购 库存充足");
		bean1.setPrice(new BigDecimal(Math.random()*100));
		bean1.setCost(Math.random()*100);
		bean1.setScore(Float.valueOf(String.valueOf(Math.random()*100)));
		bean1.setIsGift(true);
		bean1.setInventoryNum(id.intValue());
		
		Bean2 bean2_1 = new Bean2("中国",id,new Date());
		Bean2 bean2_2 = new Bean2("日本",id,new Date());
		Bean2 bean2_3 = new Bean2("德国",id,new Date());
		bean1.setBean2(bean2_1);
		bean1.setBean2List(Lists.newArrayList(bean2_1,bean2_2,bean2_3));
		
		Bean3 bean3_1 = new Bean3("_名称1",id.intValue());
		Bean3 bean3_2 = new Bean3("_名称2",id.intValue());
		Bean3 bean3_3 = new Bean3("_名称3",id.intValue());
		bean2_1.setBean3(bean3_1);
		Map<String,Bean3> map = new HashMap<String,Bean3>();
		map.put(bean3_1.getName(), bean3_1);
		map.put(bean3_2.getName(), bean3_2);
		map.put(bean3_3.getName(), bean3_3);
		bean1.setBeansMap(map);
		
		return bean1;
	}
	
	/**
	 * 更新和新增 其实一样。
	 * @param ids
	 * 2016年11月14日
	 * author rujianbin
	 */
	public void insertOrUpdate(List<Long> ids){
		List<IndexQuery> indexQueryList = new ArrayList<IndexQuery>();
		for(Long id:ids){ 
			Bean1 bean1 = initBean(id);
			IndexQuery indexQuery = new IndexQueryBuilder().withObject(bean1)
					.withIndexName("es-bean1").withId(bean1.getId().toString())
					.withType("es-bean1-type").build();
			indexQueryList.add(indexQuery);
		}
		
		
		template.bulkIndex(indexQueryList);
		template.refresh("es-bean1");
	}
	
	/**
	 * 删除某个索引的某个类型的某个id文档
	 * @param id
	 * 2016年11月14日
	 * author rujianbin
	 */
	
	public void deleteById(Class clazz,String id){
		template.delete(clazz, id);
	}
	
	/**
	 * 删除某个索引
	 * @param indexName
	 * 2016年11月14日
	 * author rujianbin
	 */
	public void deleteByIndexName(String indexName){
		template.deleteIndex(indexName);
	}
	
	/**
	 * 删除某个索引的某个类型
	 * @param clazz
	 * 2016年11月14日
	 * author rujianbin
	 */
	public void deleteByClass(Class clazz){
		template.deleteIndex(clazz);
	}
	
	public Page<Bean1> queryForBool(Map<String,Object> map,Class clazz){
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		for(Iterator<Entry<String,Object>> it = map.entrySet().iterator();it.hasNext();){
			Entry<String,Object> entry = it.next();
//			TermQueryBuilder query = QueryBuilders.termQuery(entry.getKey(), entry.getValue());
//			MatchQueryBuilder query = QueryBuilders.matchQuery(entry.getKey(), entry.getValue()).operator(MatchQueryBuilder.Operator.OR);
			WildcardQueryBuilder query = QueryBuilders.wildcardQuery(entry.getKey(), "*"+entry.getValue().toString()+"*").boost(100F);
			boolQueryBuilder.must(query);
		}
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(boolQueryBuilder).withPageable(new PageRequest(1, 20)).build();
		System.out.println(searchQuery.getQuery());
		Page<Bean1> page  = template.queryForPage(searchQuery, clazz);

		return page;
	}
}
