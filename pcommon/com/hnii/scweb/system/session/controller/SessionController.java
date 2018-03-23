package com.hnii.scweb.system.session.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hnii.scweb.system.session.service.ISessionService;
import com.hnii.scweb.util.SystemUtil;



@Controller
public class SessionController{
	/**
	 * 3.根据会话编号、会话方式与用户编号，查询会话存储值
	 * @param sessionid:会话编号
	 * @param userid:所属用户USERID
	 * @param sessiontype:会话方式[1:URL|2:COOKIE]
	 * @param key:查询的key
	 * @return
	 *       null:失败
	 *       有值:获取成功
	 * 注释:
	 *    支持JS调用
	 *    支持工具类调用
	 */
	@RequestMapping(value = "/getsessionjson", method = RequestMethod.POST)
	public void getvlauebykey(HttpServletRequest request,HttpServletResponse response,String sessionyybh,String sessionid,String userid,String sessiontype,String key){
		response.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
	    response.setHeader("Cache-Control", "no-cache");

	    String rstr = SystemUtil.INSTANCE.selectSessionValue(sessionyybh, sessionid, userid, sessiontype, key);
	    
	    PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
		    out.print(rstr);
		    out.flush();
		    out.close();
		}
	}
	/**
	 * 4.根据会话编号、会话方式与用户编号，存储会话值
	 * @param sessionid:会话编号
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
	@RequestMapping(value = "/setsessionjson", method = RequestMethod.POST)
	public @ResponseBody Integer setvalue(String sessionyybh,String sessionid,String userid,String sessiontype,String key,String value){		
		return SystemUtil.INSTANCE.updateSessionValue(sessionyybh, sessionid, userid, sessiontype, key, value);
	}
	
}
