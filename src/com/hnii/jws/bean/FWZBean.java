package com.hnii.jws.bean;
/**
 * 服务组对象
 * @author dengwei
 *
 */
public class FWZBean {
	/**
	 * 1.服务组编号
	 */
	private String systemid;
	/**
	 * 2.应用服务组名称
	 */
	private String yyfwzmc;
	/**
	 * 3.应用服务组类型
	 */
	private String fwzlx;
	/**
	 * 4.接口访问用户名 
	 */
	private String jwsuserid;
	/**
	 * 5.接口访问密码 
	 */
	private String jwspwd;
	/**
	 * 6.访问方式 1:IP访问|2:域名访问 
	 */
	private String jwsvfs;
	/**
	 * 7.IP访问前缀 
	 */
	private String jwsipqz;
	/**
	 * 8.IP系统编号 
	 */
	private String jwsipsystemid;
	/**
	 * 9.IP地址 
	 */
	private String jwsip;
	/**
	 * 10.IP后缀 
	 */
	private String jwsiphz;
	/**
	 * 11.域名地址 
	 */
	private String jwsymdz;
	/**
	 * 12.外网访问地址 附件与消息推送的应用服务组需要填写 
	 */
	private String wwurl;
	/**
	 * 13.附件静态化地址 附件服务组需要填写 
	 */
	private String filedz;
	/**
	 * 14.附件下载地址 
	 */
	private String filedowndz;
	
	
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public String getYyfwzmc() {
		return yyfwzmc;
	}
	public void setYyfwzmc(String yyfwzmc) {
		this.yyfwzmc = yyfwzmc;
	}
	public String getFwzlx() {
		return fwzlx;
	}
	public void setFwzlx(String fwzlx) {
		this.fwzlx = fwzlx;
	}
	public String getJwsuserid() {
		return jwsuserid;
	}
	public void setJwsuserid(String jwsuserid) {
		this.jwsuserid = jwsuserid;
	}
	public String getJwspwd() {
		return jwspwd;
	}
	public void setJwspwd(String jwspwd) {
		this.jwspwd = jwspwd;
	}
	public String getJwsvfs() {
		return jwsvfs;
	}
	public void setJwsvfs(String jwsvfs) {
		this.jwsvfs = jwsvfs;
	}
	public String getJwsipqz() {
		return jwsipqz;
	}
	public void setJwsipqz(String jwsipqz) {
		this.jwsipqz = jwsipqz;
	}
	public String getJwsip() {
		return jwsip;
	}
	public void setJwsip(String jwsip) {
		this.jwsip = jwsip;
	}
	public String getJwsiphz() {
		return jwsiphz;
	}
	public void setJwsiphz(String jwsiphz) {
		this.jwsiphz = jwsiphz;
	}
	public String getJwsymdz() {
		return jwsymdz;
	}
	public void setJwsymdz(String jwsymdz) {
		this.jwsymdz = jwsymdz;
	}
	public String getWwurl() {
		return wwurl;
	}
	public void setWwurl(String wwurl) {
		this.wwurl = wwurl;
	}
	public String getFiledz() {
		return filedz;
	}
	public void setFiledz(String filedz) {
		this.filedz = filedz;
	}
	public String getFiledowndz() {
		return filedowndz;
	}
	public void setFiledowndz(String filedowndz) {
		this.filedowndz = filedowndz;
	}
	public String getJwsipsystemid() {
		return jwsipsystemid;
	}
	public void setJwsipsystemid(String jwsipsystemid) {
		this.jwsipsystemid = jwsipsystemid;
	}
}
