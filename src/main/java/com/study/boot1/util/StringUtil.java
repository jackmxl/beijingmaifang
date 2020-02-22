package com.study.boot1.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtil {
    private static final int DEFAULT_DIV_SCALE = 10;

	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	public static boolean isNotEmpty(String str) {
		return ((str != null) && (str.length() > 0) && (str !=""));
	}

	public static boolean iseqnull(String str){
		return (str != "null" &&  !str.equals("null"));
	}

	public static boolean isBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}
    /**
     *
     * @Title: selectTimeGap
     * @Description: TODO(2个时间的时间差)
     * @param newtime
     * @param oldtime
     * @return
     * @author jack
     * @date 2015年12月30日 上午11:42:07
     * @throws
     */
	public static String selectTimeGap(String newtime,String oldtime){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
		 Date d1= null;
		 Date d2 = null;
		try {
			d1 = df.parse(oldtime);
			d2 = df.parse(newtime);
			 long diff = d2.getTime() - d1.getTime();
	         long diffSeconds = diff / 1000 % 60;
	         long diffMinutes = diff / (60 * 1000) % 60;
	         long diffHours = diff / (60 * 60 * 1000) % 24;
	         long diffDays = diff / (24 * 60 * 60 * 1000);


	     	if(0 !=  diffDays){
				 return df1.format(df.parse(oldtime));
			}else if(0 !=diffHours){
				if(isSameDate(d1, d2))
				{
					return  df2.format(df.parse(oldtime));
				}
				else
				{
					 return df1.format(df.parse(oldtime));
				}

			}else if(0 !=diffMinutes){
				return diffMinutes + "分钟前";
			}else if(0 !=diffSeconds){
				return diffSeconds + "秒前";
			}else{
				return "1秒前";
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}


	}


	 private static  boolean isSameDate(Date date1, Date date2) {
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(date1);

	        Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(date2);

	        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
	                .get(Calendar.YEAR);
	        boolean isSameMonth = isSameYear
	                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	        boolean isSameDate = isSameMonth
	                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
	                        .get(Calendar.DAY_OF_MONTH);

	        return isSameDate;
	    }

	public static boolean isNotBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}
	/**
	 *
	 * @Title: GenerateImage
	 * @Description: TODO(base64转图片)
	 * @param imgStr
	 * @param type
	 * @return
	 * @author jack
	 * @date 2015年11月23日 下午3:16:46
	 * @throws
	 */
	public static String GenerateImage(String imgStr,String type,String userid){
		if (imgStr == null) //图像数据为空
            return "";
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int date = c.get(Calendar.DATE);
            month++;
            long timeStamp = System.currentTimeMillis();
			 Random random = new Random();
			//生成jpeg图片
		     String url = timeStamp + "_" + random.nextInt(100000) + ".jpg";
		     //服务器地址
            String imgFilePath = "/usr/local/resin_server/webapps/eventimg/"+year+"/"+month+"/"+date+"/"+userid+"/"+type;//新生成的图片

            return ImgeUtil.CreateImage(imgStr, imgFilePath, url);
	}

	/**
	 *
	 * @Title: ICONImage
	 * @Description: TODO(头像地址)
	 * @param imgStr
	 * @param userid
	 * @return
	 * @author jack
	 * @date 2016年1月25日 下午6:36:26
	 * @throws
	 */

	public static String ICONImage(String imgStr,String userid){
		if (imgStr == null) //图像数据为空
            return "";

		long timeStamp = System.currentTimeMillis();
		 Random random = new Random();

		     String url = timeStamp + "_" + random.nextInt(100000) + ".jpg";
		     //服务器地址
            String imgFilePath = "/usr/local/resin_server/webapps/iconimg/"+userid;//新生成的图片
            return ImgeUtil.CreateImage(imgStr, imgFilePath, url);
	}


	 public static String GetImageStr()
	    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	        String imgFile = "d://1.jpg";//待处理的图片
	        InputStream in = null;
	        byte[] data = null;
	        //读取图片字节数组
	        try
	        {
	            in = new FileInputStream(imgFile);
	            data = new byte[in.available()];
	            in.read(data);
	            in.close();
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        //对字节数组Base64编码
	        BASE64Encoder encoder = new BASE64Encoder();
	        return encoder.encode(data);//返回Base64编码过的字节数组字符串
	    }
	/**
	 *
	 * @Title: GetAuthCode
	 * @Description: TODO(随机生成验证码)
	 * @param num
	 * @return
	 * @author jack
	 * @date 2015年12月25日 下午5:26:49
	 * @throws
	 */
	 public static String GetAuthCode(int num)
	 {
		  String str = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		  String str2[] = str.split(",");//将字符串以,分割
		  String res = "";
		     for(int i = 0; i < num ; i ++) {
		    	 int id = (int) Math.floor(Math.random()*61);
		         res += str2[id];
		     }
		     return res.toLowerCase();
	 }

	 public static String getStringByStrLength(String all, char sign, int begin)
	 {
		 String back = "";
		 int number = 0;
		 char arr[] = all.toCharArray();
		 for (int i = 0; i < arr.length; i++) {
			 if (arr[i] == sign) {
			 number++;
			 }
			 if (number == begin) {
				 number = i;
			     break;
			 }
		 }
		 back = all.substring(number+1, all.length());
		 return back;
	 }


	 /**
		 * 一次性判断多个或单个对象为空。
		 * @param objects
		 * @author zhou-baicheng
		 * @return 只要有一个元素为Blank，则返回true
		 */
		public static boolean isBlank(Object...objects){
			Boolean result = false ;
			for (Object object : objects) {
				if(null == object || "".equals(object.toString().trim())
						|| "null".equals(object.toString().trim())){
					result = true ;
					break ;
				}
			}
			return result ;
		}

		public static String getRandom(int length) {
			String val = "";
			Random random = new Random();
			for (int i = 0; i < length; i++) {
				// 输出字母还是数字
				String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
				// 字符串
				if ("char".equalsIgnoreCase(charOrNum)) {
					// 取得大写字母还是小写字母
					int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
					val += (char) (choice + random.nextInt(26));
				} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
					val += String.valueOf(random.nextInt(10));
				}
			}
			return val.toLowerCase();
		}
		/**
		 * 一次性判断多个或单个对象不为空。
		 * @param objects
	     * @author zhou-baicheng
		 * @return 只要有一个元素不为Blank，则返回true
		 */
		public static boolean isNotBlank(Object...objects){
			return !isBlank(objects);
		}
		public static boolean isBlank(String...objects){
			Object[] object = objects ;
			return isBlank(object);
		}
		public static boolean isNotBlank(String...objects){
			Object[] object = objects ;
			return !isBlank(object);
		}
		/**
		 * 判断一个字符串在数组中存在几个
		 * @param baseStr
		 * @param strings
		 * @return
		 */
		public static int indexOf(String baseStr,String[] strings){

			if(null == baseStr || baseStr.length() == 0 || null == strings)
				return 0;

			int i = 0;
			for (String string : strings) {
				boolean result = baseStr.equals(string);
				i = result ? ++i : i;
			}
			return i ;
		}
		public static String trimToEmpty(Object str){
		  return (isBlank(str) ? "" : str.toString().trim());
		}

		/**
		 * 将 Strig  进行 BASE64 编码
		 * @param str [要编码的字符串]
		 * @param bf  [true|false,true:去掉结尾补充的'=',false:不做处理]
		 * @return
		 */
	    public static String getBASE64(String str,boolean...bf) {
	       if (StringUtil.isBlank(str)) return null;
	       String base64 = new sun.misc.BASE64Encoder().encode(str.getBytes()) ;
	       //去掉 '='
	       if(isBlank(bf) && bf[0]){
	    	   base64 = base64.replaceAll("=", "");
	       }
	       return base64;
	    }

	    /** 将 BASE64 编码的字符串 s 进行解码**/
	    public static String getStrByBASE64(String s) {
	       if (isBlank(s)) return "";
	       BASE64Decoder decoder = new BASE64Decoder();
	       try {
	          byte[] b = decoder.decodeBuffer(s);
	          return new String(b);
	       } catch (Exception e) {
	          return "";
	       }
	    }
	    /**
	     * 把Map转换成get请求参数类型,如 {"name"=20,"age"=30} 转换后变成 name=20&age=30
	     * @param map
	     * @return
	     */
	    public static String mapToGet(Map<? extends Object,? extends Object> map){
	    	String result = "" ;
	    	if(map == null || map.size() ==0){
	    		return result ;
	    	}
	    	Set<? extends Object> keys = map.keySet();
	    	for (Object key : keys ) {
	    		result += ((String)key + "=" + (String)map.get(key) + "&");
			}

	    	return isBlank(result) ? result : result.substring(0,result.length() - 1);
	    }
	    /**
	     * 把一串参数字符串,转换成Map 如"?a=3&b=4" 转换为Map{a=3,b=4}
	     * @param args
	     * @return
	     */
	    public static Map<String, ? extends Object> getToMap(String args){
	    	if(isBlank(args)){
	    		return null ;
	    	}
	    	args = args.trim();
	    	//如果是?开头,把?去掉
	    	if(args.startsWith("?")){
	    		args = args.substring(1,args.length());
	    	}
	    	String[] argsArray = args.split("&");

	    	Map<String,Object> result = new HashMap<String,Object>();
	    	for (String ag : argsArray) {
				if(!isBlank(ag) && ag.indexOf("=")>0){

					String[] keyValue = ag.split("=");
					//如果value或者key值里包含 "="号,以第一个"="号为主 ,如  name=0=3  转换后,{"name":"0=3"}, 如果不满足需求,请勿修改,自行解决.

					String key = keyValue[0];
					String value = "" ;
					for (int i = 1; i < keyValue.length; i++) {
						value += keyValue[i]  + "=";
					}
					value = value.length() > 0 ? value.substring(0,value.length()-1) : value ;
					result.put(key,value);

				}
			}

	    	return result ;
	    }

	    /**
		 * 转换成Unicode
		 * @param str
		 * @return
		 */
	    public static String toUnicode(String str) {
	        String as[] = new String[str.length()];
	        String s1 = "";
	        for (int i = 0; i < str.length(); i++) {
	        	int v = str.charAt(i);
	        	if(v >=19968 && v <= 171941){
		            as[i] = Integer.toHexString(str.charAt(i) & 0xffff);
		            s1 = s1 + "\\u" + as[i];
	        	}else{
	        		 s1 = s1 + str.charAt(i);
	        	}
	        }
	        return s1;
	     }
	    /**
	     * 合并数据
	     * @param v
	     * @return
	     */
	    public static String merge(Object...v){
	    	StringBuffer sb = new StringBuffer();
	    	for (int i = 0; i < v.length; i++) {
	    		sb.append(v[i]);
			}
	    	return sb.toString() ;
	    }
	    /**
	     * 判断字符串是否包含汉字
	     * @param txt
	     * @return
	     */
	    public static Boolean containsCN(String txt){
	    	if(isBlank(txt)){
	    		return false;
	    	}
	    	for (int i = 0; i < txt.length(); i++) {

	    		String bb = txt.substring(i, i + 1);

	    		boolean cc = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", bb);
	    		if(cc)
	    		return cc ;
	    	}
			return false;
	    }
	    /**
	     * 去掉HTML代码
	     * @param news
	     * @return
	     */
	    public static String removeHtml(String news) {
	      String s = news.replaceAll("amp;", "").replaceAll("<","<").replaceAll(">", ">");

	      Pattern pattern = Pattern.compile("<(span)?\\sstyle.*?style>|(span)?\\sstyle=.*?>", Pattern.DOTALL);
	      Matcher matcher = pattern.matcher(s);
	      String str = matcher.replaceAll("");

	      Pattern pattern2 = Pattern.compile("(<[^>]+>)",Pattern.DOTALL);
	      Matcher matcher2 = pattern2.matcher(str);
	      String strhttp = matcher2.replaceAll(" ");


	      String regEx = "(((http|https|ftp)(\\s)*((\\:)|：))(\\s)*(//|//)(\\s)*)?"
	         + "([\\sa-zA-Z0-9(\\.|．)(\\s)*\\-]+((\\:)|(:)[\\sa-zA-Z0-9(\\.|．)&%\\$\\-]+)*@(\\s)*)?"
	         + "("
	         + "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])"
	         + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
	         + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
	         + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])"
	         + "|([\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*)*[\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*[\\sa-zA-Z]*"
	         + ")"
	         + "((\\s)*(\\:)|(：)(\\s)*[0-9]+)?"
	         + "(/(\\s)*[^/][\\sa-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*";
	      Pattern p1 = Pattern.compile(regEx,Pattern.DOTALL);
	      Matcher matchhttp = p1.matcher(strhttp);
	      String strnew = matchhttp.replaceAll("").replaceAll("(if[\\s]*\\(|else|elseif[\\s]*\\().*?;", " ");


	      Pattern patterncomma = Pattern.compile("(&[^;]+;)",Pattern.DOTALL);
	      Matcher matchercomma = patterncomma.matcher(strnew);
	      String strout = matchercomma.replaceAll(" ");
	      String answer = strout.replaceAll("[\\pP‘’“”]", " ")
	        .replaceAll("\r", " ").replaceAll("\n", " ")
	        .replaceAll("\\s", " ").replaceAll("　", "");


	      return answer;
	    }
	    /**
		 * 把数组的空数据去掉
		 * @param array
		 * @return
		 */
		public static List<String> array2Empty(String[] array){
			List<String> list = new ArrayList<String>();
			for (String string : array) {
				if(StringUtil.isNotBlank(string)){
					list.add(string);
				}
			}
			return list;
		}
		/**
		 * 把数组转换成set
		 * @param array
		 * @return
		 */
		public static Set<?> array2Set(Object[] array) {
			Set<Object> set = new TreeSet<Object>();
			for (Object id : array) {
				if(null != id){
					set.add(id);
				}
			}
			return set;
		}

	public static void main(String[] args) {
		String all = "32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,16,16,16,16,16,16,32,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,32,32,32,32,32,32,32,32,32,32,32,16,16,16,16,16,16,16,32,32,32,32,32,32,32,32,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,32,32,32,32,32,32,16,16,16,16,16,16,16,16,16,32,16,16,16,16,16,16,16,16,16,16,16,32,32,32,32,32,32,32,32,32,32,32,32,32,48,48,48,48,48,48,48,48,32,32,32,32,32,32,32,32,48,48,48,48,48,48,48,48,32,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,32,32,32,32,16,16,16,16,16,16,32,32,32,32,32,128,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
		System.out.print(all.split(",").length);
	}




}

