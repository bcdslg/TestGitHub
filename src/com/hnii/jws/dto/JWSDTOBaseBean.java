package com.hnii.jws.dto;

import java.util.List;

import com.hnii.jws.bean.FWZBean;
import com.hnii.jws.bean.YWBean;
import com.hnii.jws.bean.YYBean;

/**
 * 接口交互依据数据对象
 * @author dengwei
 *
 */
public class JWSDTOBaseBean extends BaseDTOBean{
	/**
	 * 1.业务对象集合
	 */
	private List<YWBean> ywlist;
	/**
	 * 2.服务组列表集合
	 */
	private List<FWZBean> fwzlist;
	/**
	 * 3.应用对象集合
	 */
	private List<YYBean> yylist;
	/**
	 * 4.启动运行密码[只有启动的时候，才设置值]
	 */
	private String runid;
	
	public List<YWBean> getYwlist() {
		return ywlist;
	}
	public void setYwlist(List<YWBean> ywlist) {
		this.ywlist = ywlist;
	}
	public List<FWZBean> getFwzlist(){
		return fwzlist;
	}
	public void setFwzlist(List<FWZBean> fwzlist) {
		this.fwzlist = fwzlist;
	}
	public List<YYBean> getYylist() {
		return yylist;
	}
	public void setYylist(List<YYBean> yylist) {
		this.yylist = yylist;
	}
	public String getRunid() {
		return runid;
	}
	public void setRunid(String runid) {
		this.runid = runid;
	}
}
