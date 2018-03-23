package com.hnii.jws.bean;
/**
 * 外部用户所属IP对象
 * @author dengwei
 *
 */
public class UserIPBean {
	/**
	 * 0.系统编号
	 */
	private String systemid;
	/**
	 * 1.业务用户ID 
	 */
	private String ywuserid;
	/**
	 * 2.用户密码  IP
	 */
	private String ip;
	/**
	 * 3.Token值 
	 */
	private String token;
	
	public String getYwuserid() {
		return ywuserid;
	}
	public void setYwuserid(String ywuserid) {
		this.ywuserid = ywuserid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
}
