package com.hnii.scweb.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;

import com.hnii.scweb.system.session.bean.SessionBean;
import com.hnii.scweb.system.session.service.ISessionService;

public enum SystemUtil {
	INSTANCE;
	
	/**
	 * 1.创建表系统编号
	 * @return：50位字符串【UUID(32)+18位随机数】
	 */
	public String createPk(){
		StringBuffer sb = new StringBuffer();
		//UUID(32)
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		sb.append(uuid);
		//18位随机字符串
		for(int i = 0;i < 18;i++){
			int index =(int) (Math.random() * chartstr.length);
			sb.append(chartstr[index]);
		}
		return sb.toString();
	}
	
	/**
	 * 2.加密字符串
	 * @param str:需要加密的字符串
	 * @return：60位字符串
	 */
	public String encryptStr(String str){
		return BCrypt.hashpw(str, BCrypt.gensalt());
	}
	
	/**
	 * 3.核实加密字符串
	 * @param str:需要核实的字符串
	 * @param encryptStr:加密之后的字符串
	 * @return:true:匹配
	 *         false:不匹配
	 */
	public boolean checkEncryptStr(String str,String encryptStr){
		return BCrypt.checkpw(str, encryptStr);
	}
	
	/**
     * 4.加载系统属性文件
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
			propertiyMap.put(key, pvalue);
			return pvalue;
		}
	}
	/**
     * 4.1.直接读取加载系统属性文件
     * @param filename:加载的属性文件名称
     * @param filekey:加载的属性key
     * @return
     */
    public String getDirPropertiy(String filename,String filekey){
      String value = "";
      Properties pps = new Properties();
      InputStream in = null;
      FileInputStream fin = null;
      try{
    	fin = new FileInputStream(getFilePath() + "classes/"+filename+".properties");
        in = new BufferedInputStream(fin);
        pps.load(in);
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
    private String getFilePath(){
      String path=getClass().getProtectionDomain().getCodeSource().getLocation().toString(); 
      path = path.split("WEB-INF")[0]+"WEB-INF\\";
      path = path.replace('/', '\\');
      path = path.replace("file:", "");
      path = path.replace("classes\\", "");
      path = path.substring(1);
      return path;
    }
	
    /**
     * 5.获取客户端真实IP地址
     * @param request
     * @return
     * @throws IOException
     */
    public final String getIpAddress(HttpServletRequest request) throws IOException {  
 		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址  
 		String ip = request.getHeader("X-Forwarded-For"); 
 		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
 			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
 				ip = request.getHeader("Proxy-Client-IP");  
 			} 
 			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
 				ip = request.getHeader("WL-Proxy-Client-IP");   
 			}  
 			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
 				ip = request.getHeader("HTTP_CLIENT_IP");  
 			}  
 			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
 				ip = request.getHeader("HTTP_X_FORWARDED_FOR");   
 			} 
 			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
 				ip = request.getRemoteAddr();  
 			}  
 		} else if (ip.length() > 15) {  
 			String[] ips = ip.split(",");  
 			for (String strIp:ips) {    
 				if (!("unknown".equalsIgnoreCase(strIp))) {  
 					ip = strIp;  
 					break;  
 				}  
 			} 
 		}  
 		return ip;  
 	} 
	
    /** 
     * 6.把对象转换成Json格式，支持JavaBean、Map、List 
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
     * 7.把Json字符串读取成对象格式 
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
     * 8.插入会话=======================================【未对外开放】
     * @param systemid
     * @param userid
     * @param sessiontype
     * @param ucip
     * @param sessionvalue
     * @return
     *        0:失败
     *        1:成功
     */
    public Map<String,Object> insertsession(String systemid,String userid,String sessiontype,String ucip,String sessionvalue){
    	Map<String,Object> rmap = new HashMap<String,Object>();
    	if(null==userid||null==sessiontype||userid.equals("")||sessiontype.equals("")){
    		rmap.put("insertflag", 0);
    		rmap.put("info", "会话必须参数不全");
    		return rmap;
    	}
    	if(!(sessiontype.equals("1")||sessiontype.equals("2"))){
    		rmap.put("insertflag", 0);
    		rmap.put("info", "会话类型不支持,sessiontype="+sessiontype);
    		return rmap;
    	}
    	String sessionmodal = getPropertiy("public","sessionmodal");//会话模式[1:本地|2:公共会话组件]
    	if(sessionmodal.equals("1")){//1:本地
    		ISessionService service = (ISessionService) SpringTool.getBean("sessionService");
    	   	SessionBean sessionbean = new SessionBean();//sessionid,userid,sessiontype,ucip,sessionvalue
    	   	sessionbean.setSystemid(systemid);
    	   	sessionbean.setUserid(userid);
    	   	sessionbean.setSessiontype(sessiontype);
    	   	if(null==ucip){
    	   		sessionbean.setUcip("");
    	   	}else{
    	   		sessionbean.setUcip(ucip);
    	   	}
    	   	if(null==sessionvalue){
    	   		sessionbean.setSessionvalue("");
    	   	}else{
    	   		sessionbean.setSessionvalue(sessionvalue);
    	   	}
    	   	int cznum = service.insertSession(sessionbean);
    	   	if(cznum==1){
    	   		rmap.put("insertflag", 1);
    	   		String yyzbh = getPropertiy("public","localyyzbh");
        		rmap.put("yyzbh",yyzbh);
    	   	}else{
    	   		rmap.put("insertflag",0);
    	   		rmap.put("info","存储本地会话出错");
    	   	}
    	   	return rmap;
    	}
    	
    	//2:公共会话组件-------未实现
    	return rmap;
    }
    /**
	 * 9.根据会话所属应用组编号、会话编号、用户ID、会话类型、客户端IP、是否签到，查询是否有有效的会话
	 * @param sessionyybh:会话所属的应用组编号
	 * @param systemid:会话编号
	 * @param userid:所属用户USERID
	 * @param sessiontype:会话方式[1:URL|2:COOKIE]
	 * @param ip:客户端IP地址
	 * @param isqd:是否签到[1:签到|2:不签到]
	 * @return
	 *        0:不存在
	 *        1:存在
	 */
    public int selectIsExist(String sessionyybh,String systemid,String userid,String sessiontype,String ip,String isqd){
    	String yyzbh = getPropertiy("public","localyyzbh");
    	if(sessionyybh.equals(yyzbh)){//本地应用的会话，本地处理
    		ISessionService service = (ISessionService) SpringTool.getBean("sessionService");
        	return service.selectIsExist(systemid, userid, sessiontype,ip,isqd);
    	}
    	//外部接口调用的  -----------未实现
    	return 1;
    }
   
    /**
	 * 10.根据会话编号、会话方式与用户编号，查询会话存储值
	 * @param sessionyybh:会话所属的应用组编号
	 * @param systemid:会话编号
	 * @param userid:所属用户USERID
	 * @param sessiontype:会话方式[1:URL|2:COOKIE]
	 * @param key:查询的key
	 * @return
	 *       null:失败
	 *       有值:获取成功
	 */
	public String selectSessionValue(String sessionyybh,String systemid,String userid,String sessiontype,String key){
		String yyzbh = getPropertiy("public","localyyzbh");
		if(sessionyybh.equals(yyzbh)){//本地应用会话
			ISessionService service = (ISessionService) SpringTool.getBean("sessionService");
	    	return service.selectSessionValue(systemid,userid,sessiontype,key);
		}
		
		//外部接口调用的  -----------未实现
    	return "";
	}
	
	/**
	 * 11.根据会话编号、会话方式与用户编号，存储会话值
	 * @param sessionyybh:会话所属的应用组编号
	 * @param systemid:会话编号
	 * @param userid:所属用户USERID
	 * @param sessiontype:会话方式[1:URL|2:COOKIE]
	 * @param key:存储的key
	 * @param value:存储的value
	 * @return
	 *        0:存储失败
	 *        1:存储成功
	 * 注释:
	 *    支持JS调用
	 *    支持工具类调用      
	 */
	public int updateSessionValue(String sessionyybh,String systemid,String userid,String sessiontype,String key,String value){
		String yyzbh = getPropertiy("public","localyyzbh");
		if(sessionyybh.equals(yyzbh)){//本地应用会话
			ISessionService service = (ISessionService) SpringTool.getBean("sessionService");
	    	return service.updateSessionValue(systemid,userid,sessiontype,key,value);
		}
		
		//外部接口调用的  -----------未实现
    	return 1;
	}
	
	/**
	 * 12.创建会话校验码
	 * @param sessionid:会话编号
	 * @param userid:所属用户USERID
	 * @param sessiontype:会话方式
	 * @param ip:客户端IP地址
	 * @return
	 */
	public String createsessionjym(String sessionid,String userid,String sessiontype,String ip){	
		return this.encryptStr(sessionid+"_"+userid+"_"+sessiontype+"_"+ip);
	}
	
	/**
	 * 13.获取当前时间的格式化字符串
	 * @return
	 */
	public String getdqtime(){
		return sm.format(new Date());
	}
	
	//时间格式化工具
	private SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //对象与json字符串转换器
  	private ObjectMapper objmapper = new ObjectMapper(); 
	//属性文件
	private ConcurrentHashMap<String,String> propertiyMap = new ConcurrentHashMap<String,String>();
	//随机数生成参考字符串
	private String[] chartstr = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p",
			"q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q",
			"R","S","T","U","V","W","X","Y","Z"};
	
}
