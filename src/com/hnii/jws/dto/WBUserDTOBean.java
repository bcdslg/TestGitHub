package com.hnii.jws.dto;

import java.util.List;

import com.hnii.jws.bean.UserBean;
import com.hnii.jws.bean.UserIPBean;
import com.hnii.jws.bean.UserYWBean;

/**
 * 外部用户DTO对象
 * @author dengwei
 *
 */
public class WBUserDTOBean extends BaseDTOBean{
	/**
	 * 1.外部用户列表
	 */
	private List<UserBean> userlist;
	/**
	 * 2.外部用户所属IP对象
	 */
	private List<UserIPBean> useriplist;
	/**
	 * 3.用户业务对应对象
	 */
	private List<UserYWBean> userywlist;
	
	public List<UserBean> getUserlist() {
		return userlist;
	}
	public void setUserlist(List<UserBean> userlist) {
		this.userlist = userlist;
	}
	public List<UserIPBean> getUseriplist() {
		return useriplist;
	}
	public void setUseriplist(List<UserIPBean> useriplist) {
		this.useriplist = useriplist;
	}
	public List<UserYWBean> getUserywlist() {
		return userywlist;
	}
	public void setUserywlist(List<UserYWBean> userywlist) {
		this.userywlist = userywlist;
	}
}
