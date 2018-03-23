package com.test;

import java.util.List;

import com.hnii.jws.dto.BaseDTOBean;

public class DTOTestBean extends BaseDTOBean{
	private List<UserBean> list;
	
	public List<UserBean> getList() {
		return list;
	}

	public void setList(List<UserBean> list) {
		this.list = list;
	}
}
