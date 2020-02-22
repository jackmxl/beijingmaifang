package com.study.boot1.datahelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.study.boot1.bean.House;
import com.study.boot1.util.OkHttpUtil;

@RestController
public class HouseDataHelper {

	Logger log = LoggerFactory.getLogger(HouseDataHelper.class);
    private static String gdmapkey="43af1d9a6a03e001dd1f7a0812b46da5";
    private static String gdmapurl="https://restapi.amap.com/v3/geocode/geo";


	@Async("taskExecutor")
    public CompletableFuture<List<househelp> > findHouse(String url)  {

		List<househelp> hl =   getinfo(url);
        return CompletableFuture.completedFuture(hl);
    }

	@Async("taskExecutor")
	public static String getLAL(String q, String s, String x)
	{
		String lal ="";
		 Map<String, String> params = new HashMap<String, String>();
		 params.put("key", gdmapkey);
		 params.put("address", q+s+x);
		 params.put("city", "北京");
		 JSONObject jo = JSONObject.parseObject(OkHttpUtil.doGet(gdmapurl, params, null));
		 if(jo.getInteger("status") == 1)
		 {
            JSONArray ja = jo.getJSONArray("geocodes");
            if(ja.size()>=1)
            {
            	JSONObject datajo = ja.getJSONObject(0);
            	lal = datajo.getString("location");
            }
		 }
		return lal;
	}


	public  void houseinfo(String url ,House oHouse, StringBuilder name1, StringBuilder name2, StringBuilder name3)
	{
		 Document document = Jsoup.parse(OkHttpUtil.doGet(url));
		 Elements regin = document.getElementsByClass("container");

		 String[] regionstr = regin.get(1).text().split(">");
		 String r1 = StringUtils.substringBefore(regionstr[2], "二手房成交");
		 String r2 = StringUtils.substringBefore(regionstr[3], "二手房成交");
		 String r3 = StringUtils.substringBefore(regionstr[4], "二手房成交");
		 name1.append(r1);
		 name2.append(r2);
		 name3.append(r3);

		 Elements gpjg = document.getElementsByClass("msg");
		 Elements allgp = gpjg.get(0).getElementsByTag("span");
		 for(int i=0; i<allgp.size(); i++)
		 {
			 Elements inf = allgp.get(i).select("label");
			 if(i == 0)
			 {
				 oHouse.setListedprice(inf.text());
			 }else if(i == 1)
			 {
				 oHouse.setClosingcycle(Integer.valueOf(inf.text()));
			 }else if(i == 2)
			 {
				 oHouse.setModifyprice(Integer.valueOf(inf.text()));
			 }else if(i == 3)
			 {
				 oHouse.setLooks(inf.text().equals("暂无数据")?0:Integer.valueOf(inf.text()));
			 }
		 }

		 Elements fwlx = document.getElementsByClass("base");
		 Elements fwlxi =fwlx.select("li");
		 for(Element o: fwlxi)
		 {
			 String info = o.text();
			 int result1 = info.indexOf("房屋户型");
			 int result2 = info.indexOf("所在楼层");
			 int result3 = info.indexOf("建筑面积");
			 int result4 = info.indexOf("户型结构");
			 int result5 = info.indexOf("套内面积");
			 int result6 = info.indexOf("建筑类型");
			 int result7 = info.indexOf("房屋朝向");
			 int result8 = info.indexOf("建成年代");
			 int result9 = info.indexOf("装修情况");
			 int result10 = info.indexOf("梯户比例");
			 int result11 = info.indexOf("产权年限");
			 int result12 = info.indexOf("配备电梯");
			 if(result1 != -1){
				 oHouse.setLayout(info.substring(4, info.length()));
			 }else if(result2 != -1){
				 oHouse.setFloor(info.substring(4, info.length()));
			 }else if(result3 != -1){
				 oHouse.setBuiltuparea(info.substring(4, info.length()));
			 }else if(result4 != -1){
				 oHouse.setApartmentlayout(info.substring(4, info.length()));
			 }else if(result5 != -1){
				 oHouse.setJacketarea(info.substring(4, info.length()));
			 }else if(result6 != -1){
				 oHouse.setArchitecturaltype(info.substring(4, info.length()));
			 }else if(result7 != -1){
				 oHouse.setHouseorientation(info.substring(4, info.length()));
			 }else if(result8 != -1){
				 if(info.indexOf("未知") != -1)
				 {

				 }else
				 {
					 oHouse.setAgeofcompletion(Integer.valueOf(info.substring(4, info.length())));
				 }
			 }else if(result9 != -1){
				 oHouse.setDecoration(info.substring(4, info.length()-1));
			 }else if(result10 != -1){
				 oHouse.setLadderhouseholdratio(info.substring(4, info.length()));
			 }else if(result11 != -1){
				 if(info.indexOf("未") != -1)
				 {

				 }else
				 {
					 oHouse.setRropertyrightyears(Integer.valueOf(info.substring(4, info.length()-1)));
				 }

			 }else if(result12 != -1){
				 oHouse.setLift(info.substring(4, info.length()-1).equals("有")?1:0 );
			 }
		 }

		 Elements jyqs = document.getElementsByClass("transaction");
		 Elements jyqsi =jyqs.select("li");
		 for(Element o: jyqsi)
		 {
			 String info = o.text();
			 int result1 = info.indexOf("交易权属");
			 int result2 = info.indexOf("挂牌时间");
			 int result3 = info.indexOf("房屋用途");
			 int result4 = info.indexOf("房屋年限");
			 if(result1 != -1){
				 oHouse.setTradingright(info.substring(4, info.length()));
			 }else if(result2 != -1){
				 oHouse.setListingtime(info.substring(4, info.length()));
			 }else if(result3 != -1){
				 oHouse.setHousinguse(info.substring(4, info.length()));
			 }else if(result4 != -1){
				 oHouse.setHousinglife(info.substring(4, info.length()));
			 }

		 }

		 Elements himg = document.getElementsByClass("thumbnail");
		 Elements himgi =himg.select("li");
		 StringBuilder sbhimg = new StringBuilder("");
		 for(Element o: himgi)
		 {
			 sbhimg.append(o.attr("data-src")+";");
		 }
		 oHouse.setHouseimg(sbhimg.toString());

		 Element tsc = document.getElementById("house_feature");
		 Elements tscall = null;
		 if(tsc != null)
		 {
			 tscall = tsc.getElementsByClass("introContent showbasemore");
		 }
		 if(tscall != null)
		 {
			 Elements tscalls1 = tscall.get(0).getElementsByClass("tags clear");
			 Elements tscalls2 = tscall.get(0).getElementsByClass("baseattribute clear");
			 for(Element o: tscalls1)
			 {
				 oHouse.setSpecial1(o.text());
			 }
			 int spei = 2;
			 for(int i=0; i<tscalls2.size(); i++)
			 {
				 Element o = tscalls2.get(i);
				 switch(i){
		         case 0:
		        	 oHouse.setSpecial2(o.text());
		        	 break;
		         case 1:
		        	 oHouse.setSpecial3(o.text());
		        	 break;
		         case 2:
		        	 oHouse.setSpecial4(o.text());
		        	 break;
		         case 3:
		        	 oHouse.setSpecial5(o.text());
		        	 break;
		         case 4:
		        	 oHouse.setSpecial6(o.text());
		        	 break;
		         default:
		        	 break;
		      }
			 }

		 }

	}

	public static void main(String[] args) {
		System.out.print(getLAL("大兴","西红门","兴海家园星苑"));
	}

	public class househelp
	{
		public House oHouse;
		public String name1;
		public String name2;
		public String name3;

		public househelp(House oHouse, String name1, String name2, String name3)
		{
				super();
				this.oHouse=oHouse;
				this.name1=name1;
				this.name2=name2;
				this.name3=name3;
		}
	}

	public  List<househelp>  getinfo(String url)
	{
		List<househelp> listhouse = new ArrayList<househelp >();


	        Document document = Jsoup.parse(OkHttpUtil.doGet(url));
	        Elements oElements = document.getElementsByClass("listContent");
	        Elements infoElements = oElements.first().getElementsByClass("info");
	        StringBuilder sb = new StringBuilder("");
	        for(Element o : infoElements)
	        {
	        	House oHouse = new House();
	        	oHouse.setName(o.getElementsByClass("title").text());
	        	oHouse.setCharacteristic(o.getElementsByClass("dealHouseInfo").text());
	        	oHouse.setPricecycle(o.getElementsByClass("dealCycleeInfo").text());
	        	oHouse.setClosingdate(o.getElementsByClass("dealDate").text());
	        	 String regEx = "[^0-9]";
	        	 Pattern p = Pattern.compile(regEx);
	        	 Matcher m = p.matcher(o.getElementsByClass("totalPrice").text());
	        	oHouse.setClosingprice(Integer.valueOf(m.replaceAll("")));

	        	StringBuilder name1= new StringBuilder("");
	        	StringBuilder name2= new StringBuilder("");
	        	StringBuilder name3= new StringBuilder("");
	        	houseinfo(o.getElementsByClass("title").select("a").attr("href"), oHouse,  name1,  name2,  name3);

	        	househelp ohousehelp = new househelp(oHouse, name1.toString(), name2.toString(), name3.toString());

	        	listhouse.add(ohousehelp);
	        }
	     return  listhouse;

	}


}
