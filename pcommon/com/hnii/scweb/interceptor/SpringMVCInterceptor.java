package com.hnii.scweb.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hnii.scweb.util.SystemUtil;


/**
 * 拦截器
 * @author dengwei
 *
 */
public class SpringMVCInterceptor extends HandlerInterceptorAdapter {  
	/**
	 * 线程里绑定变量容器
	 * 绑定变量：startTimeThreadLocal.set("123");
	 * 获取变量:startTimeThreadLocal.get();
	 */
	private NamedThreadLocal<Map<String,String>>  startTimeThreadLocal =  new NamedThreadLocal<Map<String,String>>("StopWatch-StartTime");
	
	/**
     * 在Controller方法前进行拦截
     */
   public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
    	System.out.println("======================preHandle==============================");
    	//校验会话
    	//sessionid:会话编号,sessionjym:会话校验码[会话编号+USERID+会话方式+接入端IP的加密字段]
    	String sessionid = request.getParameter("sessionid");
    	sessionid=null==sessionid?"":sessionid;
    	String sessionjym = request.getParameter("sessionjym");
    	sessionjym=null==sessionjym?"":sessionjym;
    	String userid = request.getParameter("userid");
    	userid=null==userid?"":userid;
    	String sessiontype = request.getParameter("sessiontype");
    	sessiontype=null==sessiontype?"":sessiontype;
    	String sessionyybh = request.getParameter("sessionyybh");
    	sessionyybh=null==sessionyybh?"":sessionyybh;
    	String ip = SystemUtil.INSTANCE.getIpAddress(request);
    	
    	//1.规则校验 
    	if(!SystemUtil.INSTANCE.checkEncryptStr(sessionid+"_"+userid+"_"+sessiontype+"_"+ip,sessionjym)){
    		response.sendRedirect(request.getContextPath()+"/indexview?sessionstatu=1");
    		return false;
    	}
    	//1.2.会话应用编号是否存在
    	if(sessionyybh.isEmpty()){
    		System.out.println("=======>>>>>> 未指定会话服务编号");
    		response.sendRedirect(request.getContextPath()+"/indexview?sessionstatu=1");
    		return false;
    	}
    	
    	//2.核实数据库会话是否存在，并且根据签到标识，来操作是否更新
    	String tip = sessiontype.equals("1")?ip:"";//1:URL|2:COOKIE
    	String isqd = request.getParameter("sfqd");
    	isqd = null==isqd?"1":isqd;
    	int num = SystemUtil.INSTANCE.selectIsExist(sessionyybh,sessionid,userid,sessiontype,tip,isqd);
    	if(num==0){
    		response.sendRedirect(request.getContextPath()+"/indexview?sessionstatu=1");
    		return false;
    	}
    	
    	String uri = request.getRequestURI();
    	if(uri.indexOf("view")>-1){//表示有视图
    		Map<String,String> bdmap = new HashMap<String,String>();
        	bdmap.put("sessionid", sessionid);
        	bdmap.put("sessionjym", sessionjym);
        	bdmap.put("userid", userid);
        	bdmap.put("sessiontype", sessiontype);
        	bdmap.put("sessionyybh", sessionyybh);
        	startTimeThreadLocal.set(bdmap);
    	}
    	
    	request.setAttribute("IP", ip);
        return true;
   }


   public void postHandle(HttpServletRequest request,HttpServletResponse response,Object handler,ModelAndView modelAndView) throws Exception {
	   System.out.println("======================postHandle==============================");
	   String uri = request.getRequestURI();
	   if(uri.indexOf("view")>-1){//表示有视图
	   	   Map<String,String> rmap = startTimeThreadLocal.get();
	 	   request.setAttribute("sessionid", rmap.get("sessionid"));
	 	   request.setAttribute("sessionjym", rmap.get("sessionjym"));
	 	   request.setAttribute("userid", rmap.get("userid"));
	 	   request.setAttribute("sessionyybh", rmap.get("sessionyybh"));
	 	   request.setAttribute("sessiontype", rmap.get("sessiontype"));
	   }
   }

   /**
    * 在Controller方法后进行拦截
    */
   public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
	   System.out.println("======================afterCompletion==============================");
	   
   }
   
}  
