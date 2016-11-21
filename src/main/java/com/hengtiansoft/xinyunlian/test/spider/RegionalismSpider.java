package com.hengtiansoft.xinyunlian.test.spider;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegionalismSpider {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private HttpClient httpclient = new DefaultHttpClient();;
	private final String charset = "UTF-8";
	
	private final String url = "http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201608/t20160809_1386477.html";
	private final String streetURL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/index.html";
	private String filePath="D:/temp/";
	
	public void execute() throws Exception{
		String html = htmlObtain();
		Map<String,Province> map = JsoupParse(html);
		fillStreet(map);
		createExcel(map);
	}
	
	private String htmlObtain()throws Exception{
		logger.info("---信息获取URL:" + url + "---");
		HttpPost httppost = new HttpPost(url);
		//set Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("TEST", "VALUE"));
		
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
	
	
	private Map<String,Province> JsoupParse(String html){
		Document doc = Jsoup.parse(html,charset);
		Elements els = doc.getElementsByClass("MsoNormal");
		Map<String,Province> pMap = new HashMap<String,Province>();
		if(CollectionUtils.isNotEmpty(els)){
			int i=1;
			for(Iterator<Element> it = els.iterator();it.hasNext();){
				Element el = it.next();
				Elements spanList = el.getElementsByTag("span");
				String gbCode = spanList.get(0).childNode(0).toString().trim();
				String value = spanList.get(2).text().trim();
				logger.info("序号="+(i++)+",gbCode="+gbCode+",value="+value);
				if(gbCode.endsWith("0000")){//省
					Province p = new Province(value,gbCode);
					if(!pMap.containsKey(gbCode)){
						pMap.put(gbCode, p);
					}
				}else if(gbCode.endsWith("00")){//地级市
					City c = new City(value,gbCode);
					
					Province p = pMap.get(gbCode.substring(0, 2)+"0000");
					if(p!=null){
						Map cMap = p.getCityMap();
						if(!cMap.containsKey(gbCode)){
							cMap.put(gbCode, c);
						}
					}
					
				}else{//区
					District d = new District(value,gbCode);
					Province p = pMap.get(gbCode.substring(0, 2)+"0000");
					if(p!=null){
						Map<String,City> cMap = p.getCityMap();
						City c = cMap.get(gbCode.substring(0, 4)+"00");
						if(c!=null){
							Map dMap = c.getDistrictMap();
							if(!dMap.containsKey(gbCode)){
								dMap.put(gbCode, d);
							}
						}
						
					}
				}
			}
		}
		
		return pMap;
	}
	
	private void fillStreet(Map<String,Province> map)throws Exception{
		StreetSpider sp = new StreetSpider();
		Map<String,Street> streets = sp.execute();
		if(!streets.isEmpty()){
			for(Iterator<String> it = streets.keySet().iterator();it.hasNext();){
				String gbCode = it.next();
				Street street = streets.get(gbCode);
				String firstTwo = gbCode.substring(0,2);
				String midTwo = gbCode.substring(2,4);
				String thirdTwo = gbCode.substring(4,6);
				String lastThree = gbCode.substring(6,9);
				Province p = map.get(firstTwo+"0000");
				if(p!=null){
					City c = p.getCityMap().get(firstTwo+midTwo+"00");
					if(c!=null){
						District d = c.getDistrictMap().get(firstTwo+midTwo+thirdTwo);
						if(d!=null){
							Map<String,Street> streetMap = d.getStreets();
							if(!streetMap.containsKey(firstTwo+midTwo+thirdTwo+lastThree)){
								streetMap.put(firstTwo+midTwo+thirdTwo+lastThree, street);
							}
						}
					}
				}
				
			}
		}
	}
	
	private void createExcel(Map<String,Province> map)throws Exception{
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmsss");
    	String fileName = format.format(new Date()) + ".xls";
    	File directory = new File(filePath);
    	if(!directory.exists()){
    		directory.mkdir();
    	}
    	File file = new File(directory,fileName);
    	FileOutputStream out = new FileOutputStream(file);
    	
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	Sheet sheet = workbook.createSheet("最新行政区划");
    	int rowNum=0;
    	Row titleRow = sheet.createRow(rowNum++);
    	titleRow.createCell(0).setCellValue("gbCode");
    	titleRow.createCell(1).setCellValue("省");
    	titleRow.createCell(2).setCellValue("市");
    	titleRow.createCell(3).setCellValue("区");
    	titleRow.createCell(4).setCellValue("街道");
    	
    	if(!map.isEmpty()){
    		for(Iterator<String> it = map.keySet().iterator();it.hasNext();){
    			String key = it.next();
    			Province p = map.get(key);
    			Row pRow = sheet.createRow(rowNum++);
    			pRow.createCell(0).setCellValue(p.getGbCode().trim());
    			pRow.createCell(1).setCellValue(p.getName().trim());
    			
    			Map<String,City> cMap = p.getCityMap();
    			if(!cMap.isEmpty()){
    				for(Iterator<String> it2 = cMap.keySet().iterator();it2.hasNext();){
    					String key2 = it2.next();
    					City c = cMap.get(key2);
    					Row cRow = sheet.createRow(rowNum++);
    					cRow.createCell(0).setCellValue(c.getGbCode().trim());
    					cRow.createCell(1).setCellValue(p.getName().trim());
    					cRow.createCell(2).setCellValue(c.getName().trim());
    	    			
    	    			Map<String,District> dMap = c.getDistrictMap();
    	    			if(!dMap.isEmpty()){
    	    				for(Iterator<String> it3 = dMap.keySet().iterator();it3.hasNext();){
    	    					String key3 = it3.next();
    	    					District d = dMap.get(key3);
    	    					Row dRow = sheet.createRow(rowNum++);
    	    					dRow.createCell(0).setCellValue(d.getGbCode().trim());
    	    					dRow.createCell(1).setCellValue(p.getName().trim());
    	    					dRow.createCell(2).setCellValue(c.getName().trim());
    	    					dRow.createCell(3).setCellValue(d.getName().trim());
    	    					
    	    					Map<String,Street> streetMap = d.getStreets();
    	    					if(!streetMap.isEmpty()){
    	    						for(Iterator<String> it4 = streetMap.keySet().iterator();it4.hasNext();){
    	    							String key4 = it4.next();
    	    							Street st = streetMap.get(key4);
    	    							Row sRow = sheet.createRow(rowNum++);
    	    							sRow.createCell(0).setCellValue(st.getGbCode().trim());
    	    							sRow.createCell(1).setCellValue(p.getName().trim());
    	    							sRow.createCell(2).setCellValue(c.getName().trim());
    	    							sRow.createCell(3).setCellValue(d.getName().trim());
    	    							sRow.createCell(4).setCellValue(st.getName().trim());
    	    						}
    	    					}
    	    				}
    	    			}
    				}
    			}
    		}
    	}
    	workbook.write(out);
    	out.close();
	}
	public static void main(String[] args) {
	
		try {
			RegionalismSpider spider = new RegionalismSpider();
			spider.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
