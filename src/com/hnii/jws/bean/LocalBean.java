package com.hnii.jws.bean;
/**
 * 本地属性文件对象
 * @author dengwei
 *
 */
public class LocalBean {
	/**
	 * 1.本地服务组编号
	 */
	private String localyyzbh;
	/**
	 * 2.本地应用编号
	 */
	private String localyybh;
	/**
	 * 3.启动密码
	 */
	private String qdpwd;
	/**
	 * 4.所属服务组类型
	 */
	private String fwzlx;
	/**
	 * 5.启动业务编号
	 */
	private String qdywbh;
	
	/**
	 * 6.配置管理的wsdl文件访问地址
	 */
	private String pzglwsdl;
	/**
	 * 7.配置管理访问用户名
	 */
	private String pzgluserid;
	/**
	 * 8.配置管理访问密码
	 */
	private String pzglpwd;
	/**
	 * 9.配置管理服务组编号
	 */
	private String pzglfwzbh;
	/**
	 * 10.重新加载配置属性时间
	 */
	private int reloadpropmin;
	/**
	 * 11.重新加载配置信息的业务编号
	 */
	private String rloadywid;
	/**
	 * 12.本地应用处理springbean对象名称【到应用的未有配置，此处为处理所有到应用的配置名称，比如:消息】
	 */
	private String yyclspringbean;
	/**
	 * 13.启动业务对应的服务类名称
	 */
	private String qdservicename;
	/**
	 * 14.是否加载用户信息[1:是|2:否]
	 */
	private String isloaduserinfo;
	/**
	 * 15.加载用户信息业务编号
	 */
	private String loaduserinfoywid;
	/**
	 * 16.修改用户IP的token值
	 */
	private String utokenywid;
	/**
	 * 17.本地应用运行编号
	 */
	private String runid;
	/**
	 * 18.JWS接收到请求，直接处理的会话业务编号
	 */
	private String hhywbh;
	
	public String getLocalyyzbh() {
		return localyyzbh;
	}
	public void setLocalyyzbh(String localyyzbh) {
		this.localyyzbh = localyyzbh;
	}
	public String getLocalyybh() {
		return localyybh;
	}
	public void setLocalyybh(String localyybh) {
		this.localyybh = localyybh;
	}
	public String getQdpwd() {
		return qdpwd;
	}
	public void setQdpwd(String qdpwd) {
		this.qdpwd = qdpwd;
	}
	public String getFwzlx() {
		return fwzlx;
	}
	public void setFwzlx(String fwzlx) {
		this.fwzlx = fwzlx;
	}
	public String getQdywbh() {
		return qdywbh;
	}
	public void setQdywbh(String qdywbh) {
		this.qdywbh = qdywbh;
	}
	public String getPzglwsdl() {
		return pzglwsdl;
	}
	public void setPzglwsdl(String pzglwsdl) {
		this.pzglwsdl = pzglwsdl;
	}
	public String getPzgluserid() {
		return pzgluserid;
	}
	public void setPzgluserid(String pzgluserid) {
		this.pzgluserid = pzgluserid;
	}
	public String getPzglpwd() {
		return pzglpwd;
	}
	public void setPzglpwd(String pzglpwd) {
		this.pzglpwd = pzglpwd;
	}
	public String getPzglfwzbh() {
		return pzglfwzbh;
	}
	public void setPzglfwzbh(String pzglfwzbh) {
		this.pzglfwzbh = pzglfwzbh;
	}
	public int getReloadpropmin() {
		return reloadpropmin;
	}
	public void setReloadpropmin(int reloadpropmin) {
		this.reloadpropmin = reloadpropmin;
	}
	public String getRloadywid() {
		return rloadywid;
	}
	public void setRloadywid(String rloadywid) {
		this.rloadywid = rloadywid;
	}
	public String getYyclspringbean() {
		return yyclspringbean;
	}
	public void setYyclspringbean(String yyclspringbean) {
		this.yyclspringbean = yyclspringbean;
	}
	public String getQdservicename() {
		return qdservicename;
	}
	public void setQdservicename(String qdservicename) {
		this.qdservicename = qdservicename;
	}
	public String getIsloaduserinfo() {
		return isloaduserinfo;
	}
	public void setIsloaduserinfo(String isloaduserinfo) {
		this.isloaduserinfo = isloaduserinfo;
	}
	public String getLoaduserinfoywid() {
		return loaduserinfoywid;
	}
	public void setLoaduserinfoywid(String loaduserinfoywid) {
		this.loaduserinfoywid = loaduserinfoywid;
	}
	public String getUtokenywid() {
		return utokenywid;
	}
	public void setUtokenywid(String utokenywid) {
		this.utokenywid = utokenywid;
	}
	public String getRunid() {
		return runid;
	}
	public void setRunid(String runid) {
		this.runid = runid;
	}
	public String getHhywbh() {
		return hhywbh;
	}
	public void setHhywbh(String hhywbh) {
		this.hhywbh = hhywbh;
	}
}
