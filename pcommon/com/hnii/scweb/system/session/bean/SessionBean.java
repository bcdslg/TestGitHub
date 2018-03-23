package com.hnii.scweb.system.session.bean;

/**
 * 会话对象BEAN
 * @author dengwei
 *
 */
public class SessionBean {
	public SessionBean(){};
	
	public SessionBean(String systemid,String userid,String sessiontype){
		this.systemid = systemid;
		this.userid = userid;
		this.sessiontype = sessiontype;
	}
	
	
	/**
	 * 1.会话编号
	 */
	private String systemid;
	/**
	 * 2.会话关联用户ID
	 */
	private String userid;
	/**
	 * 3.会话类型[1:URL|2:COOKIE]
	 */
	private String sessiontype;
	/**
	 * 4.URL会话接入客户端IP地址
	 */
	private String ucip;
	/**
	 * 5.最后签到时间
	 */
	private String zhqdsj;
	/**
	 * 6.会话存储业务值[JSON字符串]
	 */
	private String sessionvalue;
	
	
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSessiontype() {
		return sessiontype;
	}
	public void setSessiontype(String sessiontype) {
		this.sessiontype = sessiontype;
	}
	public String getUcip() {
		return ucip;
	}
	public void setUcip(String ucip) {
		this.ucip = ucip;
	}
	public String getZhqdsj() {
		return zhqdsj;
	}
	public void setZhqdsj(String zhqdsj) {
		this.zhqdsj = zhqdsj;
	}
	public String getSessionvalue() {
		return sessionvalue;
	}
	public void setSessionvalue(String sessionvalue) {
		this.sessionvalue = sessionvalue;
	}
}
