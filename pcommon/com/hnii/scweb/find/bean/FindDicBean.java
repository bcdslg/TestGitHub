package com.hnii.scweb.find.bean;
/**
 * SELECT查询字典BEAN对象
 * @author dengwei
 *
 */
public class FindDicBean {
	public FindDicBean(){}
	public FindDicBean(String code,String value){
		this.code = code;
		this.value = value;
	}
	private String code;
	private String value;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
