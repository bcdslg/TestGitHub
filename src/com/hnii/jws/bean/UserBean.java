package com.hnii.jws.bean;
/**
 * 外部用户对象
 * @author dengwei
 *
 */
public class UserBean {
	/**
	 * 1.用户ID
	 */
	private String ywuserid;
	/**
	 * 2.用户密码(加密) YWPWD
	 */
	private String ywpwd;
	/**
	 * 3.联系电话 
	 */
	private String lxdh;
	/**
	 * 4.用户名称 
	 */
	private String yhmc;
	
	
	public String getYwuserid() {
		return ywuserid;
	}
	public void setYwuserid(String ywuserid) {
		this.ywuserid = ywuserid;
	}
	public String getYwpwd() {
		return ywpwd;
	}
	public void setYwpwd(String ywpwd) {
		this.ywpwd = ywpwd;
	}
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getYhmc() {
		return yhmc;
	}
	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}
}
