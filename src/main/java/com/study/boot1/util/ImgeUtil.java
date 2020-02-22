package com.study.boot1.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImgeUtil {

	
	
	public static String CreateImage(String imgStr, String url, String imgname)
	{
		if (imgStr == null) //图像数据为空  
            return "";  
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
		     //服务器地址
            String imgFilePath = url+"/"+imgname;//新生成的图片 
            File file2 =new File(url+"/");    
            //如果文件夹不存在则创建    
	          if  (!file2 .exists()  && !file2 .isDirectory())      
	          {       
	        	  file2 .mkdirs();    
	          } 
            File file=new File(imgFilePath);
		            if(!file.exists())    
		            {    
		                try {    
		                    file.createNewFile();    
		                } catch (IOException e) {    
		                    // TODO Auto-generated catch block    
		                    e.printStackTrace();    
		                }    
		            }  
		          
            OutputStream out = new FileOutputStream(new File(imgFilePath));      
            out.write(b);  
            out.flush();  
            out.close(); 
            String path = imgFilePath.substring(imgFilePath.indexOf("webapps/"), imgFilePath.length());
            return "http://www.59medi.com"+path.substring(path.indexOf("/"),path.length());  
        }   
        catch (Exception e)   
        {  
            return "";  
        }  
	}
	
	public static String getBsae64Img(String imgstr) throws IOException
	{
		 String[] url = imgstr.split(",");
         String u = url[1];
		 BASE64Encoder encoder = new BASE64Encoder();
		  BASE64Decoder decoder = new BASE64Decoder(); 
		  byte[] b = decoder.decodeBuffer(u);
		  String a = encoder.encode(b);
		  return a;
	}
	
	public static void createFile(String sPath){
		File file2 = new File(sPath);  
		if  (!file2 .exists()  && !file2 .isDirectory())      
		{       
			  file2 .mkdirs();  
		} 
	}
	
	public static boolean deleteFile(String sPath) {  
		boolean flag = false;  
		File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
}
