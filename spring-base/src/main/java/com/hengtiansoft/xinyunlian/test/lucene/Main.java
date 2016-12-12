package com.hengtiansoft.xinyunlian.test.lucene;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import com.hengtiansoft.xinyunlian.test.util.Util;



public class Main {

	private static String dir1=Util.getWEBINFPath()+File.separator+"lucene"+File.separator;
	
	private static String indexName1 = "index1";
	private static String indexName2 = "index2";
	

	/**
	 * 1   add nihao china good
	 * 2   good job,jams value
	 * 3   love is the most important value
	 * @param args
	 */
	
	
	public static void main(String[] args){	
//		createIndex(true,indexName1);
//		appendIndex(1);
//		deleteLucene("id_str","15");
//		searchIndex_fenye("id_str","999",1,10);
//		searchIndex_Range("publish","1402313439775","1402313839775");//��һ��ʱ��=1402308215781
//		searchIndex_combine(null,null,1,10);
		searchIndex_moreIndex("id_str","999",1,10);//
	}
	
	/**
	 * 创建索引
	 * @param isCreate  是否创建索引还是追加索引
	 * @param indexPath 索引路径
	 */
	public static void createIndex(boolean isCreate,String indexPath){
		try{
			Date start = new Date();
			System.out.println("Indexing to directory '" + dir1 + "'...");
			//打开存储index的文件路径
			Path path = FileSystems.getDefault().getPath(dir1, indexPath);

			Directory dir = FSDirectory.open(path);
			//创建标准分词器
			Analyzer analyzer = new StandardAnalyzer();
			//创建config
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			//设置config的模式：新建or更新
			if (isCreate){
		        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		    }else {
		        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);//会扩展索引
		    }
			//创建IndexWriter对象
			IndexWriter writer = new IndexWriter(dir, iwc);
			//将字段值放入IndexWriter
			Long ll = new Date().getTime();
			System.out.println("存入时间="+ll);
			for(int i=0; i < 3000; i++) {
				ll = ll+60000;
	            Document doc=new Document();  
            	doc.add(new StringField("id_str", String.valueOf(i), Store.YES));
 	            doc.add(new NumericDocValuesField("id", i));  
 	            doc.add(new TextField("content", i+"olymtech of china", Store.YES));  //TextField是一大块需要经过分词的文本；
 	            doc.add(new StringField("loadport", i+"NingBoL", Store.YES));  //StringField是一个不需要分词，而直接用于索引的字符串；
 	            doc.add(new StringField("dischargPort", i+"NingBoD", Store.YES));
 	            doc.add(new DoubleField("basePrice", Math.random(), Store.YES));
 	            doc.add(new DoubleField("sellPrice", Math.random(), Store.YES));
 	            doc.add(new DoubleField("internetPrice", Math.random(), Store.YES));
 	            doc.add(new StringField("bookingAgent", i+"bookingAgent", Store.YES));
 	            doc.add(new StringField("carrier", i+"carrier", Store.YES));//YES表示字段值存入lucene.  NO表示未存入lucene,但是依然可以当条件检索，只是搜出的document的carrier值为null
 	            doc.add(new LongField("publish", ll, Store.YES));
	           
	            writer.addDocument(doc);  
	        }  
			writer.commit();
			writer.close();

		    Date end = new Date();
		    System.out.println("创建lucene耗时："+(end.getTime() - start.getTime()) + " total milliseconds");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//追加索引
	public static void appendIndex(int i){
		try{
			Date start = new Date();
			System.out.println("append Indexing to directory '" + dir1 + "'...");
			//打开存储index的文件路径
			Path path = FileSystems.getDefault().getPath(dir1, indexName1);
			Directory dir = FSDirectory.open(path);
			//创建标准分词器
			Analyzer analyzer = new StandardAnalyzer();
			//创建标准分词器
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			//设置config的模式：新建or更新
		    iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);//����չ����
			//创建IndexWriter对象
			IndexWriter writer = new IndexWriter(dir, iwc);
			//将字段值放入IndexWriter
			Document doc=new Document();  
            
            doc.add(new LongField("id_int", i, Store.YES));
            doc.add(new StringField("id", String.valueOf(i), Store.YES));  
            doc.add(new TextField("content", i+"olymtech of china", Store.YES));  //TextField��һ�����Ҫ����ִʵ��ı���
            doc.add(new StringField("loadport", i+"NingBoL", Store.YES));  //StringField��һ������Ҫ�ִʣ���ֱ������������ַ�
            doc.add(new StringField("dischargPort", i+"NingBoD", Store.YES));
            doc.add(new DoubleField("basePrice", Math.random(), Store.YES));
            doc.add(new DoubleField("sellPrice", Math.random(), Store.YES));
            doc.add(new DoubleField("internetPrice", Math.random(), Store.YES));
            doc.add(new StringField("bookingAgent", i+"bookingAgent", Store.YES));
            doc.add(new StringField("carrier", i+"carrier", Store.YES));
            doc.add(new LongField("publish", new Date().getTime()+60000, Store.YES));
            writer.addDocument(doc); 
             
			writer.commit();
			writer.close();

		    Date end = new Date();
		    System.out.println("append INdex"+(end.getTime() - start.getTime()) + " total milliseconds");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 删除某个索引记录 
	 * @param field_str  字段名
	 * @param text  字段值
	 */
	public static void deleteLucene(String field_str,String text){
		try{
			Date start = new Date();
			//打开目录
			Path path = FileSystems.getDefault().getPath(dir1, indexName1);
			Directory dir=FSDirectory.open(path);
			//创建标准分词器
			Analyzer analyzer = new StandardAnalyzer();
			//创建config
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			boolean isRead = false;
			//设置config的模式：新建or更新
			if (isRead){
		        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		    }else {
		        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);//����չ����
		    }
			//创建IndexWriter对象
			IndexWriter writer = new IndexWriter(dir, iwc);
			//创建搜索内容对象(不通配)词条搜索
			Term term=new Term(field_str, text);  
			writer.deleteDocuments(term); 
			
			writer.commit();
			writer.close();

		    Date end = new Date();
		    System.out.println("删除lucene耗时："+(end.getTime() - start.getTime()) + " total milliseconds");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//通配查询
		public static void searchIndex_Wildcard(String field_str,String text,int limit){
			try{
				//打开目录
				Path path = FileSystems.getDefault().getPath(dir1, indexName1);
				Directory dir=FSDirectory.open(path);
				//读取目录
				IndexReader reader = DirectoryReader.open(dir);
				//创建搜索引擎
				IndexSearcher searcher = new IndexSearcher(reader);
				//创建搜索内容对象(不通配)词条搜索
//				Term term=new Term(field_str, text);  
//		        TermQuery query=new TermQuery(term);  
		        //创建搜索内容对象(通配)
		        Term term = new Term(field_str, "*" + text + "*");//？通配一个字符，*通配多个字符
				WildcardQuery query = new WildcardQuery(term); 
		        //执行搜索，取前5条
		        TopDocs topdocs=searcher.search(query, limit); 
		        ScoreDoc[] scoreDocs=topdocs.scoreDocs;
		        System.out.println("查询结果总数---" + topdocs.totalHits+"最大的评分--"+topdocs.getMaxScore());
		        //循环获得单个结果
		        for(int i=0; i < scoreDocs.length; i++) {  
		            int doc = scoreDocs[i].doc;  
		            Document document = searcher.doc(doc);  
		            System.out.println("【"+(i+1)+"】content="+document.get("content")+",id="+document.get("id")+",city="+document.get("city"));  
		            
		            System.out.println("id--" + scoreDocs[i].doc + "---scors--" + scoreDocs[i].score+"---index--"+scoreDocs[i].shardIndex);  
		        } 
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		//词条查询,精确查询
		public static void searchIndex_Term(String field_str,String text){
			try{
				//打开目录
				Path path = FileSystems.getDefault().getPath(dir1, indexName1);
				Directory dir=FSDirectory.open(path);
				//读取目录
				IndexReader reader = DirectoryReader.open(dir);
				//创建搜索引擎
				IndexSearcher searcher = new IndexSearcher(reader);
				//创建搜索内容对象(不通配)词条搜索
				Term term=new Term(field_str, text);  
		        TermQuery query=new TermQuery(term);  
		        //创建搜索内容对象(通配)
//		        Term term = new Term(field_str, "*" + text + "*");
//				WildcardQuery query = new WildcardQuery(term); 
		        //执行搜索，取前5条
		        TopDocs topdocs=searcher.search(query, 5); 
		        ScoreDoc[] scoreDocs=topdocs.scoreDocs;
		        System.out.println("查询结果总数---" + topdocs.totalHits+"最大的评分--"+topdocs.getMaxScore());
		        //循环获得单个结果
		        for(int i=0; i < scoreDocs.length; i++) {  
		            int doc = scoreDocs[i].doc;  
		            Document document = searcher.doc(doc);  
		            System.out.println("【"+(i+1)+"】content="+document.get("content")+",id="+document.get("id")+",city="+document.get("city"));  
		            
		            System.out.println("id--" + scoreDocs[i].doc + "---scors--" + scoreDocs[i].score+"---index--"+scoreDocs[i].shardIndex);  
		        } 
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		//组合查询，与或
		public static void searchIndex_Boolean(String field_str,String text1,String text2){
			try{
				//打开目录
				Path path = FileSystems.getDefault().getPath(dir1, indexName1);
				Directory dir=FSDirectory.open(path);
				//读取目录
				IndexReader reader = DirectoryReader.open(dir);
				//创建搜索引擎
				IndexSearcher searcher = new IndexSearcher(reader);
				//创建搜索内容对象
		        TermQuery query1=new TermQuery(new Term(field_str, text1));  
		        TermQuery query2=new TermQuery(new Term(field_str, text2));  
		        BooleanQuery query = new BooleanQuery();
		        query.add(query1, Occur.MUST);
		        query.add(query2,Occur.MUST);//表明两个都是必须满足的,且处理的都是TermQuery词条查询
		        //执行搜索，取前5条
		        TopDocs topdocs=searcher.search(query, 5); 
		        ScoreDoc[] scoreDocs=topdocs.scoreDocs;
		        System.out.println("查询结果总数---" + topdocs.totalHits+"最大的评分--"+topdocs.getMaxScore());
		        //循环获得单个结果
		        for(int i=0; i < scoreDocs.length; i++) {  
		            int doc = scoreDocs[i].doc;  
		            Document document = searcher.doc(doc);  
		            System.out.println("【"+(i+1)+"】content="+document.get("content")+",id="+document.get("id")+",city="+document.get("city"));  
		            
		            System.out.println("id--" + scoreDocs[i].doc + "---scors--" + scoreDocs[i].score+"---index--"+scoreDocs[i].shardIndex);  
		        } 
		        reader.close();
		        dir.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		//范围查询有TermRangeQuery(非数字型的范围)，NumericRangeQuery（数字型范围）
		public static void searchIndex_Range(String field_str,String text1,String text2){
			try{
				//打开目录
				Path path = FileSystems.getDefault().getPath(dir1, indexName1);
				Directory dir=FSDirectory.open(path);
				//读取目录
				IndexReader reader = DirectoryReader.open(dir);
				//创建搜索引擎
				IndexSearcher searcher = new IndexSearcher(reader);
				//创建搜索内容对象
				BytesRef term1 = new BytesRef(text1);
				BytesRef term2 = new BytesRef(text2);
		        //TermRangeQuery query = new TermRangeQuery(field_str,term1,term2,true,true);
		        NumericRangeQuery query = NumericRangeQuery.newLongRange(field_str, Long.valueOf(text1), Long.valueOf(text2), true, true);
		        //执行搜索，取前5条
		        TopDocs topdocs=searcher.search(query, 10); 
		        ScoreDoc[] scoreDocs=topdocs.scoreDocs;
		        System.out.println("查询结果总数---" + topdocs.totalHits+"最大的评分--"+topdocs.getMaxScore());
		        //循环获得单个结果
		        for(int i=0; i < scoreDocs.length; i++) {  
		            int doc = scoreDocs[i].doc;  
		            Document document = searcher.doc(doc);  
		            System.out.println("【"+(i+1)+"】content="+document.get("content")+",id="+document.get("id")+",city="+document.get("city"));  
		            
		            System.out.println("id--" + scoreDocs[i].doc + "---scors--" + scoreDocs[i].score+"---index--"+scoreDocs[i].shardIndex);  
		        } 
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		//前缀查询PrefixQuery
		public static void searchIndex_Prefix(String field_str,String text1){
			try{
				//打开目录
				Path path = FileSystems.getDefault().getPath(dir1, indexName1);
				Directory dir=FSDirectory.open(path);
				//读取目录
				IndexReader reader = DirectoryReader.open(dir);
				//创建搜索引擎
				IndexSearcher searcher = new IndexSearcher(reader);
				//创建搜索内容对象
				Term pre1 = new Term(field_str, text1);
				PrefixQuery query =new PrefixQuery(pre1);
		        //执行搜索，取前5条
		        TopDocs topdocs=searcher.search(query, 5); 
		        ScoreDoc[] scoreDocs=topdocs.scoreDocs;
		        System.out.println("查询结果总数---" + topdocs.totalHits+"最大的评分--"+topdocs.getMaxScore());
		        //循环获得单个结果
		        for(int i=0; i < scoreDocs.length; i++) {  
		            int doc = scoreDocs[i].doc;  
		            Document document = searcher.doc(doc);  
		            System.out.println("【"+(i+1)+"】content="+document.get("content")+",id="+document.get("id")+",city="+document.get("city"));  
		            
		            System.out.println("id--" + scoreDocs[i].doc + "---scors--" + scoreDocs[i].score+"---index--"+scoreDocs[i].shardIndex);  
		        } 
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		//多关键字的搜索PhraseQuery
		//关键词text1 text2的中间有几个词，则坡度slop必须大于该数值才可以找到，of this等词属于非常规词
		//比如词条 i love you hello,则输入i和hello的关键词时，坡度必须大于等于2
		public static void searchIndex_Phrase(String field_str,String text1,String text2,int slop){
			try{
				//打开目录
				Path path = FileSystems.getDefault().getPath(dir1, indexName1);
				Directory dir=FSDirectory.open(path);
				//读取目录
				IndexReader reader = DirectoryReader.open(dir);
				//创建搜索引擎
				IndexSearcher searcher = new IndexSearcher(reader);
				//创建搜索内容对象
				 Term term1 = new Term(field_str, text1);
		         Term term2 = new Term(field_str, text2);
		         PhraseQuery query =new PhraseQuery();
		         query.add(term1);
		         query.add(term2);
		         query.setSlop(slop);
		         //排序
		         SortField[] sortFields = new SortField[2];
		         SortField sortField = new SortField("Id", Type.STRING, true);
		         SortField FIELD_SEX = new SortField("city", Type.STRING, false);
		         sortFields[0] = sortField;
		         sortFields[1] = FIELD_SEX;
		         Sort sort = new Sort(sortFields);
		        //执行搜索，取前5条
		        TopDocs topdocs=searcher.search(query, 5,sort); 
		        ScoreDoc[] scoreDocs=topdocs.scoreDocs;
		        System.out.println("查询结果总数---" + topdocs.totalHits+"最大的评分--"+topdocs.getMaxScore());
		        //循环获得单个结果
		        for(int i=0; i < scoreDocs.length; i++) {  
		            int doc = scoreDocs[i].doc;  
		            Document document = searcher.doc(doc);  
		            System.out.println("【"+(i+1)+"】content="+document.get("content")+",id="+document.get("id")+",city="+document.get("city"));  
		            
		            System.out.println("id--" + scoreDocs[i].doc + "---scors--" + scoreDocs[i].score+"---index--"+scoreDocs[i].shardIndex);  
		        } 
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		//相近词查询FuzzyQuery 如"Venic1", "Venice","2venice","japan"  用2Venicee去查可以查出2venice，Venice
		public static void searchIndex_Fuzzy(String field_str,String text1){
			try{
				//打开目录
				Path path = FileSystems.getDefault().getPath(dir1, indexName1);
				Directory dir=FSDirectory.open(path);
				//读取目录
				IndexReader reader = DirectoryReader.open(dir);
				//创建搜索引擎
				IndexSearcher searcher = new IndexSearcher(reader);
				//创建搜索内容对象
				 Term term1 = new Term(field_str, text1);

				 FuzzyQuery query =new FuzzyQuery(term1);
		        //执行搜索，取前5条
		        TopDocs topdocs=searcher.search(query, 5); 
		        ScoreDoc[] scoreDocs=topdocs.scoreDocs;
		        System.out.println("查询结果总数---" + topdocs.totalHits+"最大的评分--"+topdocs.getMaxScore());
		        //循环获得单个结果
		        for(int i=0; i < scoreDocs.length; i++) {  
		            int doc = scoreDocs[i].doc;  
		            Document document = searcher.doc(doc);  
		            System.out.println("【"+(i+1)+"】content="+document.get("content")+",id="+document.get("id")+",city="+document.get("city"));  
		            
		            System.out.println("id--" + scoreDocs[i].doc + "---scors--" + scoreDocs[i].score+"---index--"+scoreDocs[i].shardIndex);  
		        } 
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		//QueryParser自己会根据输入的值构建查询Query对象
		//对输入的关键词先分词，然后通过词条搜索（TermQuery）查询，如输入china hello会查出2条记录，分词后其实就是构建Boolean的“或型Query”
		public static void searchIndex_QueryParser(String field_str,String text1){
			try{
				//打开目录
				Path path = FileSystems.getDefault().getPath(dir1, indexName1);
				Directory dir=FSDirectory.open(path);
				//读取目录
				IndexReader reader = DirectoryReader.open(dir);
				//创建搜索引擎
				IndexSearcher searcher = new IndexSearcher(reader);
				//创建搜索内容对象
				 Term term1 = new Term(field_str, text1);
				 Analyzer analyzer = new StandardAnalyzer();
				 QueryParser p = new QueryParser(field_str, analyzer);
				 Query query = p.parse(text1);
				 //排序，先以sort1排序，再以sort2排序
		         SortField[] sortFields = new SortField[2];
		         SortField sort1 = new SortField("id", Type.STRING, false);
		         SortField sort2 = new SortField("city", Type.STRING, false);
		         sortFields[0] = sort1;
		         sortFields[1] = sort2;
		         Sort sort = new Sort(sortFields);
			     //执行搜索，取前5条
		        TopDocs topdocs=searcher.search(query, 5,sort); 
		        ScoreDoc[] scoreDocs=topdocs.scoreDocs;
		        System.out.println("查询结果总数---" + topdocs.totalHits+"最大的评分--"+topdocs.getMaxScore());
		        //循环获得单个结果
		        for(int i=0; i < scoreDocs.length; i++) {  
		            int doc = scoreDocs[i].doc;  
		            Document document = searcher.doc(doc);  
		            System.out.println("【"+(i+1)+"】content="+document.get("content")+",id="+document.get("id")+",city="+document.get("city"));  
		            
		            System.out.println("id--" + scoreDocs[i].doc + "---scors--" + scoreDocs[i].score+"---index--"+scoreDocs[i].shardIndex);  
		        } 
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		//分页，通配查询，排序查询
		public static void searchIndex_fenye(String field_str,String text,int currentPage,int pageSize){
			try{
				//打开目录
				Path path = FileSystems.getDefault().getPath(dir1, indexName1);
				Directory dir=FSDirectory.open(path);
				//读取目录
				IndexReader reader = DirectoryReader.open(dir);
				System.out.println("reader是否可用"+reader.getRefCount());//返回reader的对象引用数量  0表示不可用（可能已关闭），1以上表示可用
				//创建搜索引擎
				IndexSearcher searcher = new IndexSearcher(reader);
				//创建搜索内容对象(不通配)词条搜索
//				Term term=new Term(field_str, text);  
//		        TermQuery query=new TermQuery(term);  
		        //创建搜索内容对象(通配)
		        Term term = new Term(field_str, "*" + text + "*");//？通配一个字符，*通配多个字符
				WildcardQuery query = new WildcardQuery(term); 
				//排序，分页容器
				//排序
				SortField[] sortFields = new SortField[1];
				SortField sort1 = new SortField("id", Type.LONG, false);//第三个参数正排翻排
				sortFields[0] = sort1;
				Sort sort = new Sort(sortFields);
				Integer first = (currentPage-1)*pageSize;
				TopFieldCollector collect = TopFieldCollector.create(sort, first+pageSize,  false, false, false);
		        //执行搜索，取前5条
		        searcher.search(query, collect); 
		        ScoreDoc[] scoreDocs=collect.topDocs(first, pageSize).scoreDocs;
		       // System.out.println("查询结果总数---" + topdocs.totalHits+"最大的评分--"+topdocs.getMaxScore());
		        //循环获得单个结果
		        for(int i=0; i < scoreDocs.length; i++) {  
		            int doc = scoreDocs[i].doc;  
		            Document document = searcher.doc(doc);  
		            System.out.println("【"+(i+1)+"】content="+document.get("content")+",id="+document.get("id")+",carrier="+document.get("carrier"));  
		            
		            System.out.println("id--" + scoreDocs[i].doc + "---scors--" + scoreDocs[i].score+"---index--"+scoreDocs[i].shardIndex);  
		        } 
		        
		        reader.close();
		        System.out.println("reader是否可用"+reader.tryIncRef()+"   "+reader.getRefCount());
		        dir.close();
		        System.out.println("reader是否可用"+reader.tryIncRef());
			}catch(Exception e){
				e.printStackTrace();
			}	
		}
		
		//综合搜索，集通配，与或，范围，翻页搜索为一体
		public static void searchIndex_combine(Map<String,Object> orMap,Map<String,Object> andMap ,int currentPage,int pageSize){
			try{
				//打开目录
				Path path = FileSystems.getDefault().getPath(dir1, indexName1);
				Directory dir=FSDirectory.open(path);
				//读取目录
				IndexReader reader = DirectoryReader.open(dir);
				
				//创建搜索引擎
				IndexSearcher searcher = new IndexSearcher(reader);
				//与或query为最外层
				BooleanQuery query = new BooleanQuery();
				
		        //构造下一级  2个通配query又组成一个boolean query
				BooleanQuery subquery = new BooleanQuery();
		        Term term = new Term("id", "*" + "1" + "*");//？通配一个字符，*通配多个字符
				WildcardQuery Wquery = new WildcardQuery(term); 
				Term term2 = new Term("id", "*" + "2" + "*");//？通配一个字符，*通配多个字符
				WildcardQuery Wquery2 = new WildcardQuery(term2); 
				subquery.add(Wquery,Occur.SHOULD);
				subquery.add(Wquery2,Occur.SHOULD);
				//构造下一级 范围的query
				NumericRangeQuery Rquery = NumericRangeQuery.newLongRange("publish", Long.valueOf("1402313439775"), Long.valueOf("1402313839775"), true, true);
				
				query.add(subquery,Occur.MUST);
				query.add(Rquery,Occur.MUST);
				//排序，分页容器
				//排序
				SortField[] sortFields = new SortField[1];
				SortField sort1 = new SortField("id", Type.LONG, false);//第三个参数正排翻排
				sortFields[0] = sort1;
				Sort sort = new Sort(sortFields);
				Integer first = (currentPage-1)*pageSize;
				TopFieldCollector collect = TopFieldCollector.create(sort, first+pageSize,false, false, false);
		        //执行搜索，取前5条
		        searcher.search(query, collect); 
		        ScoreDoc[] scoreDocs=collect.topDocs(first, pageSize).scoreDocs;
		       // System.out.println("查询结果总数---" + topdocs.totalHits+"最大的评分--"+topdocs.getMaxScore());
		        //循环获得单个结果
		        for(int i=0; i < scoreDocs.length; i++) {  
		            int doc = scoreDocs[i].doc;  
		            Document document = searcher.doc(doc);  
		            System.out.println("【"+(i+1)+"】content="+document.get("content")+",id="+document.get("id")+",carrier="+document.get("carrier"));  
		            
		            System.out.println("id--" + scoreDocs[i].doc + "---scors--" + scoreDocs[i].score+"---index--"+scoreDocs[i].shardIndex);  
		        } 
		        
		        reader.close();
		        dir.close();
			}catch(Exception e){
				e.printStackTrace();
			}	
		}
		
		//多个索引文件联查
		public static void searchIndex_moreIndex(String field_str,String text,int currentPage,int pageSize){
			try{
				//打开目录
				Path path1 = FileSystems.getDefault().getPath(dir1, indexName1);
				Path path2 = FileSystems.getDefault().getPath(dir1, indexName2);
				Directory dir=FSDirectory.open(path1);
				Directory dir2=FSDirectory.open(path2);
				//读取目录
				IndexReader reader = DirectoryReader.open(dir);
				IndexReader reader2 = DirectoryReader.open(dir2);
				MultiReader mreader = new MultiReader(new IndexReader[]{reader,reader2});
				System.out.println("reader是否可用"+reader.getRefCount());//返回reader的对象引用数量  0表示不可用（可能已关闭），1以上表示可用
				
				//创建搜索引擎
				IndexSearcher searcher = new IndexSearcher(mreader);
		        //创建搜索内容对象(通配)
		        Term term = new Term(field_str, "*" + text + "*");//？通配一个字符，*通配多个字符
				WildcardQuery query = new WildcardQuery(term); 
				//排序，分页容器
				//排序
				SortField[] sortFields = new SortField[1];
				SortField sort1 = new SortField("id", Type.LONG, false);//第三个参数正排翻排
				sortFields[0] = sort1;
				Sort sort = new Sort(sortFields);
				Integer first = (currentPage-1)*pageSize;
				TopFieldCollector collect = TopFieldCollector.create(sort, first+pageSize,false, false, false);
				 
				//执行搜索，取前5条
		        searcher.search(query, collect); 
		        ScoreDoc[] scoreDocs=collect.topDocs(first, pageSize).scoreDocs;
		       // System.out.println("查询结果总数---" + topdocs.totalHits+"最大的评分--"+topdocs.getMaxScore());
		        //循环获得单个结果
		        for(int i=0; i < scoreDocs.length; i++) {  
		            int doc = scoreDocs[i].doc;  
		            Document document = searcher.doc(doc);  
		            System.out.println("【"+(i+1)+"】content="+document.get("content")+",id="+document.get("id")+",carrier="+document.get("carrier"));  
		            
		            System.out.println("id--" + scoreDocs[i].doc + "---scors--" + scoreDocs[i].score+"---index--"+scoreDocs[i].shardIndex);  
		        } 
		        
		        reader.close();
		        System.out.println("reader是否可用"+reader.tryIncRef()+"   "+reader.getRefCount());
		        dir.close();
		        System.out.println("reader是否可用"+reader.tryIncRef());
			}catch(Exception e){
				e.printStackTrace();
			}	
		}
	
}
