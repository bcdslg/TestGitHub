package com.hnii.jws.dto;
/**
 * 接口返回对象
 * @author dengwei
 *
 */
public class BaseDTOBean {
	/**
	 * 1.返回标识
	 */
	private String reflag;
	/**
	 * 2.错误信息
	 */
	private String falseinfo="";
	/**
	 * 3.返回字符串内容
	 */
	private String rstr;
	/**
	 * 4.返回数字类型
	 */
	private int rint;
	/**
	 * 5.返回boolean类型
	 */
	private boolean rboolean;
	
	public String getReflag() {
		return reflag;
	}
	public void setReflag(String reflag){
		this.reflag = reflag;
	}
	public String getFalseinfo() {
		return falseinfo;
	}
	public void setFalseinfo(String falseinfo) {
		this.falseinfo = falseinfo;
	}
	public String getRstr() {
		return rstr;
	}
	public void setRstr(String rstr) {
		this.rstr = rstr;
	}
	public int getRint() {
		return rint;
	}
	public void setRint(int rint) {
		this.rint = rint;
	}
	public boolean isRboolean() {
		return rboolean;
	}
	public void setRboolean(boolean rboolean) {
		this.rboolean = rboolean;
	}
}
