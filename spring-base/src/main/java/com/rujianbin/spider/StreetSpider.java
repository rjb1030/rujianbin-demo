package com.rujianbin.spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreetSpider {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private HttpClient httpclient = new DefaultHttpClient();
	private final String charset = "gbk";
	private final String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/index.html";
	private String tempUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/index.html";
	private Map<String,Street> streetMap = new HashMap<String,Street>(); 
	
	public Map<String,Street> execute()throws Exception{
		String indexHtml = htmlObtain(url);
		parseIndex(indexHtml);
		return streetMap;
	}
	
	private String htmlObtain(String url)throws Exception{
		logger.info("---信息获取URL:" + url + "---");
		HttpPost httppost = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		httpclient = new DefaultHttpClient();
		httppost.setEntity(new UrlEncodedFormEntity(params, charset));
		HttpResponse response = httpclient.execute(httppost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK){ 
			HttpEntity entity = response.getEntity();
			logger.info("目标页获取成功！");
			String html =  EntityUtils.toString(entity, charset);
			return html;
		} else if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
            || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
            || (statusCode == HttpStatus.SC_SEE_OTHER)
            || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {//跳转
			HttpEntity entity = response.getEntity();
			logger.info("目标页获取异常，页面发生跳转！");
			logger.info(EntityUtils.toString(entity, charset));
			return "";
		}
		return "";
	}
	

	private void parseIndex(String html)throws Exception{
		
		Document doc = Jsoup.parse(html,charset);
		Elements provinces = doc.getElementsByClass("provincetr");
		if(!provinces.isEmpty()){
			for(Iterator<Element> it = provinces.iterator();it.hasNext();){
				Element el = it.next();
				Elements aList= el.getElementsByTag("a");
				for(Iterator<Element> iter = aList.iterator();iter.hasNext();){
					Element p = iter.next();
					String uri = p.attr("href").trim();
					String name = p.text().trim();
					String newUrl = getNewUrl(url,uri);
//					if(!name.equals("浙江省")){
//						continue;
//					}
					logger.info("省份："+name);
					logger.info("URL："+newUrl);
					String newHtml = htmlObtain(newUrl);
					parseProvince(newHtml,newUrl);
				}
				
			}
		}
	}
	
	private void parseProvince(String html,String parentUrl)throws Exception{
		Document doc = Jsoup.parse(html,charset);
		Elements citys = doc.getElementsByClass("citytr");
		if(!citys.isEmpty()){
			for(Iterator<Element> it = citys.iterator();it.hasNext();){
				Element el = it.next();
				String uri = el.getElementsByTag("a").get(0).attr("href").trim();
				String gbCode = el.getElementsByTag("a").get(0).text().trim();
				String name = el.getElementsByTag("a").get(1).text();
				String newUrl = getNewUrl(parentUrl,uri);
				logger.info("城市："+name);
				logger.info("URL："+newUrl);
				String newHtml = htmlObtain(newUrl);
				parseCity(newHtml,newUrl);
			}
		}
	}
	
	private void parseCity(String html,String parentUrl)throws Exception{
		Document doc = Jsoup.parse(html,charset);
		Elements districts = doc.getElementsByClass("countytr");
		if(!districts.isEmpty()){
			for(Iterator<Element> it = districts.iterator();it.hasNext();){
				Element el = it.next();
				String uri="";
				String gbCode="";
				String name="";
				if(!el.getElementsByTag("a").isEmpty()){
					uri = el.getElementsByTag("a").get(0).attr("href").trim();
					gbCode = el.getElementsByTag("a").get(0).text().trim();
					name = el.getElementsByTag("a").get(1).text().trim();
				}else{
					uri = el.getElementsByTag("td").get(0).attr("href").trim();
					gbCode = el.getElementsByTag("td").get(0).text().trim();
					name = el.getElementsByTag("td").get(1).text().trim();
				}
				if(StringUtils.isNotBlank(uri)){
					String newUrl = getNewUrl(parentUrl,uri);
					logger.info("区："+name);
					logger.info("URL："+newUrl);
					String newHtml = htmlObtain(newUrl);
					parseDistrict(newHtml,newUrl);
				}
			}
		}
	}
	
	private void parseDistrict(String html,String parentUrl)throws Exception{
		Document doc = Jsoup.parse(html,charset);
		Elements districts = doc.getElementsByClass("towntr");
		if(!districts.isEmpty()){
			for(Iterator<Element> it = districts.iterator();it.hasNext();){
				Element el = it.next();
				String uri = el.getElementsByTag("a").get(0).attr("href").trim();
				String gbCode = el.getElementsByTag("a").get(0).text().trim();
				if(gbCode.length()>9){
					gbCode = gbCode.substring(0, 9);
				}
				String name = el.getElementsByTag("a").get(1).text().trim();
				if(!streetMap.containsKey(gbCode)){
					streetMap.put(gbCode, new Street(name,gbCode));
				}
			}
		}
	}
	
	private String getNewUrl(String oldUrl,String uri){
		String u = oldUrl.substring(0, oldUrl.lastIndexOf("/"));
		return u+"/"+uri;
	}
	
	public static void main(String[] args) {
		StreetSpider spider = new StreetSpider();
		try {
			spider.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
