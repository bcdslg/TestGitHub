package com.hnii.scweb.system.session.dao;

import org.springframework.stereotype.Repository;

import com.hnii.scweb.system.session.bean.SessionBean;
import com.hnii.scweb.util.BaseDao;

@Repository("sessionDao")
public class SessionDaoImpl extends BaseDao{
	/**
	 * 1.插入session
	 * @param sessionbean:插入的session对象
	 * @return
	 */
	public int insertSession(SessionBean sessionbean){
		return this.insert("sessionmapper.insertsession",sessionbean);
	} 
	
	/**
	 * 2.根据会话编号、会话方式与用户编号，查询是否有有效的会话
	 * @param sessionbean:查询的参数
	 * @return
	 *        0:不存在
	 *        1:存在
	 */
	public int selectIsExist(SessionBean sessionbean){
		String sessiontype = sessionbean.getSessiontype();
		if(sessiontype.equals("1")){//URL方式
			return this.selectObject("sessionmapper.selectExistSessionip",sessionbean,Integer.class);
		}
		return this.selectObject("sessionmapper.selectExistSession",sessionbean,Integer.class);
	}
	
	/**
	 * 3.根据会话编号、会话方式与用户编号，查询会话存储值
	 * @param sessionbean:查询的参数
	 * @return
	 */
	public String selectSessionValue(SessionBean sessionbean){
		return this.selectObject("sessionmapper.selectsessionvalue",sessionbean,String.class);
	}
	
	/**
	 * 4.根据会话编号、会话方式与用户编号，存储会话值
	 * @param sessionbean:修改的参数
	 * @return
	 */
	public int updateSessionValue(SessionBean sessionbean){
		return this.update("sessionmapper.updatesessionvalue", sessionbean);
	}
	
	/**
	 * 5.根据会话编号签到
	 * @param sessionbean:修改的参数
	 * @return
	 */
	public int updateqd(String systemid){
		return this.update("sessionmapper.updateqd", systemid);
	}
}
