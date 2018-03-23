package com.hnii.jws.bean;
/**
 * 用户业务对应对象
 * @author dengwei
 *
 */
public class UserYWBean {
	/**
	 * 1.业务用户ID 
	 */
	private String ywuserid;
	/**
	 * 2.业务编号 
	 */
	private String ywbh;
	/**
	 * 3.数据权限标识JSON 限制数据权限字段  
	 */
	private String sjqxjson;
	
	
	public String getYwuserid() {
		return ywuserid;
	}
	public void setYwuserid(String ywuserid) {
		this.ywuserid = ywuserid;
	}
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
	public String getSjqxjson() {
		return sjqxjson;
	}
	public void setSjqxjson(String sjqxjson) {
		this.sjqxjson = sjqxjson;
	}
}
