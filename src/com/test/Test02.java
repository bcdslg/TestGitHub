package com.test;

import java.util.ArrayList;
import java.util.List;

import com.hnii.jws.dto.BaseDTOBean;
import com.hnii.jws.type.ReType;
import com.hnii.jws.util.JWSUtil;

public class Test02 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		DTOTestBean dtbean = new DTOTestBean();
//		dtbean.setReflag(ReType.SUCCESS);
//		
//		List<UserBean> list = new ArrayList<UserBean>();
//		list.add(new UserBean("s1","张三"));
//		list.add(new UserBean("s2","李四"));
//		list.add(new UserBean("s3","王五"));
//		dtbean.setList(list);
		
		BaseDTOBean dtbean = new BaseDTOBean();
		dtbean.setReflag(ReType.SUCCESS);
		
		String rjsonstr = JWSUtil.INSTANCE.toJsonString(dtbean);
		System.out.println(rjsonstr);
		System.out.println("=================================");
		DTOTestBean rbean = JWSUtil.INSTANCE.toObject(rjsonstr, DTOTestBean.class);
		System.out.println(rbean.getReflag());
//		for(UserBean userbean:rbean.getList()){
//			System.out.println("userid = "+userbean.getUserid()+",username = "+userbean.getUsername());
//		}
	}

}
