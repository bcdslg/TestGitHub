package com.test;

public class UserBean {
	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserBean(String userid, String username) {
		super();
		this.userid = userid;
		this.username = username;
	}
	private String userid;
	private String username;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
