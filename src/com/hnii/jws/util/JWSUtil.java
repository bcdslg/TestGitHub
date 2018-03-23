package com.hnii.jws.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.codehaus.jackson.map.ObjectMapper;

import com.hnii.scweb.util.BCrypt;
/**
 * 外部依赖 加密工具类
 * @author dengwei
 *
 */
public enum JWSUtil {
	INSTANCE;
	
	/**
     * 1.加载系统属性文件
     * @param filename:加载的属性文件名称
     * @param filekey:加载的属性key
     * @return
     */
	public String getPropertiy(String filename,String filekey){
		String key = filename+"_"+filekey;
		if(propertiyMap.containsKey(key)){
			return propertiyMap.get(key);
		}else{
			String pvalue = getDirPropertiy(filename,filekey);
			return pvalue;
		}
	}
	/**
     * 1.1.直接读取加载系统属性文件
     * @param filename:加载的属性文件名称
     * @param filekey:加载的属性key
     * @return
     */
    private String getDirPropertiy(String filename,String filekey){
    	String value = "";
        Properties pps = new Properties();
        InputStream in = null;
        FileInputStream fin = null;
        try{
      	fin = new FileInputStream(getFilePath() + "classes/"+filename+".properties");
          in = new BufferedInputStream(fin);
          pps.load(in);
          for (String pkey : pps.stringPropertyNames()) { 
          	propertiyMap.put(filename+"_"+pkey,pps.getProperty(pkey));
          } 
          value = pps.getProperty(filekey);
        } catch (IOException e) {
          e.printStackTrace();
          return null;
        }finally{
      	  if(null!=in){
      		  try {
  				in.close();
  				fin.close();
  			} catch (IOException e){
  				e.printStackTrace();
  			}
      	  }
        }
        return value;
    }
	/**
	 * 1.2.获取项目在硬盘路径
	 * @return
	 */
	public String getFilePath(){
		String path=getClass().getProtectionDomain().getCodeSource().getLocation().toString(); 
		path = path.split("WEB-INF")[0]+"WEB-INF\\";
		path = path.replace('/', '\\');
		path = path.replace("file:", "");
		path = path.replace("classes\\", "");
		path = path.substring(1);
		return path;
    }
	/** 
     * 2.把对象转换成Json格式，支持JavaBean、Map、List 
     * @param obj 
     * @return 
     */ 
	public String toJsonString(Object obj){  
        try {  
            return objmapper.writeValueAsString(obj);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 
	/** 
     * 3.把Json字符串读取成对象格式 
     * @param <T> 
     * @param str 
     * @param cla 
     * @return 
     */  
    public <T> T toObject(String str, Class<T> cla){  
        if(str == null){  
            return null;      
        }  
        try {  
            return objmapper.readValue(str, cla);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
 
    /**
	 * 4.加密字符串
	 * @param str:需要加密的字符串
	 * @return：60位字符串
	 */
	public String encryptStr(String str){
		return BCrypt.hashpw(str, BCrypt.gensalt());
	}
	
	/**
	 * 5.核实加密字符串
	 * @param str:需要核实的字符串
	 * @param encryptStr:加密之后的字符串
	 * @return:true:匹配
	 *         false:不匹配
	 */
	public boolean checkEncryptStr(String str,String encryptStr){
		return BCrypt.checkpw(str, encryptStr);
	}
	
    /**
     * 6.获取token值
     * @return
     */
	public String getToken(){
		return getrandomstr(18)+getjavauuid();
	}
	
	
	/**
	 * 0.获取UUID
	 * @return
	 */
	private String getjavauuid(){
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
	/**
	 * 0.返回指定长度的随机字符串
	 * @param length:字符串长度
	 * @return
	 */
	private String getrandomstr(int length){
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < length;i++){
			int index =(int) (Math.random() * chartstr.length);
			sb.append(chartstr[index]);
		}
		return sb.toString();
	}
	//对象与json字符串转换器
  	private ObjectMapper objmapper = new ObjectMapper(); 
	//属性文件
	private ConcurrentHashMap<String,String> propertiyMap = new ConcurrentHashMap<String,String>();
	//随机数生成参考字符串
	private String[] chartstr = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p",
			"q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q",
			"R","S","T","U","V","W","X","Y","Z"};
}
