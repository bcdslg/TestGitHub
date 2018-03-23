package com.hnii.scweb.system.session.service;

import com.hnii.scweb.system.session.bean.SessionBean;

public interface ISessionService {
	/**
	 * 1.插入session
	 * @param sessionbean:插入的session对象[userid,sessiontype,ucip,sessionvalue]
	 * @return
	 * 		0:失败
	 *      1:成功
	 * 注释:
	 *    登录调用:登录验证成功之后，接着存储会话信息;
	 *    工具类调用:展示对外未开放
	 */
	public int insertSession(SessionBean sessionbean);
	
	/**
	 * 2.根据会话编号、会话方式与用户编号，查询是否有有效的会话
	 * @param systemid:会话编号
	 * @param userid:所属用户USERID
	 * @param sessiontype:会话方式[1:URL|2:COOKIE]
	 * @param ip:客户端IP地址
	 * @return
	 *        0:不存在
	 *        1:存在
	 */
	public int selectIsExist(String systemid,String userid,String sessiontype,String ip,String isqd);
	
	/**
	 * 3.根据会话编号、会话方式与用户编号，查询会话存储值
	 * @param systemid:会话编号
	 * @param userid:所属用户USERID
	 * @param sessiontype:会话方式[1:URL|2:COOKIE]
	 * @param key:查询的key
	 * @return
	 *       null:失败
	 *       有值:获取成功
	 * 注释:
	 *    支持JS调用
	 *    支持工具类调用
	 */
	public String selectSessionValue(String systemid,String userid,String sessiontype,String key);
	
	/**
	 * 4.根据会话编号、会话方式与用户编号，存储会话值
	 * @param systemid:会话编号
	 * @param userid:所属用户USERID
	 * @param sessiontype:会话方式[1:URL|2:COOKIE]
	 * @param key:存储的key
	 * @param value:存储的value
	 * @return
	 *        0:存储失败
	 *        1:存储成功
	 * 注释:
	 *    支持JS调用
	 *    支持工具类调用      
	 */
	public int updateSessionValue(String systemid,String userid,String sessiontype,String key,String value);
	/**
	 * 5.根据会话编号签到
	 * @param systemid:签到的会话编号
	 * @return
	 *       0:失败
	 *       1:成功
	 * 注释：
	 *    拦截器调用:用于核实会话是否过期，调用工具类
	 *    工具类对接：用于拦截器调用
	 */
	public int updateqd(String systemid);
}
