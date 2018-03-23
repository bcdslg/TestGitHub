package com.hnii.jws.bean;
/**
 * 业务对象
 * @author dengwei
 *
 */
public class YWBean{
	/**
	 * 1.业务编号
	 */
	private String ywbh;
	/**
	 * 2.业务名称 
	 */
	private String ywmc;
	/**
	 * 3.服务名称 服务配置名称 定义的类必须实现 IHNIISERVICE 接口
	 */
	private String servicename;
	/**
	 * 4.业务描述
	 */
	private String ywms;
	/**
	 * 5.所属应用服务组系统编号 
	 */
	private String yyfwzsystemid;
	
	
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
	public String getYwmc() {
		return ywmc;
	}
	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	public String getYwms() {
		return ywms;
	}
	public void setYwms(String ywms) {
		this.ywms = ywms;
	}
	public String getYyfwzsystemid() {
		return yyfwzsystemid;
	}
	public void setYyfwzsystemid(String yyfwzsystemid) {
		this.yyfwzsystemid = yyfwzsystemid;
	}
}
