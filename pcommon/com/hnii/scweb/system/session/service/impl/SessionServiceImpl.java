package com.hnii.scweb.system.session.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnii.scweb.system.session.bean.SessionBean;
import com.hnii.scweb.system.session.dao.SessionDaoImpl;
import com.hnii.scweb.system.session.service.ISessionService;
import com.hnii.scweb.util.SystemUtil;


@Service("sessionService")
public class SessionServiceImpl implements ISessionService{
	@Autowired
	private SessionDaoImpl sessionDao;

	public void setSessionDao(SessionDaoImpl sessionDao) {
		this.sessionDao = sessionDao;
	}
	
	
	/**
	 * 1.插入session
	 * @param sessionbean:插入的session对象[userid,sessiontype,ucip,sessionvalue]
	 * @return
	 * 		0:失败
	 *      1:成功
	 * 注释:
	 *    登录调用:登录验证成功之后，接着存储会话信息;
	 *    工具类对接:展示对外未开放
	 */
	@Override
	public int insertSession(SessionBean sessionbean){
		return sessionDao.insertSession(sessionbean);
	}

	/**
	 * 2.根据会话编号、会话方式与用户编号，查询是否有有效的会话
	 * @param systemid:会话编号
	 * @param userid:所属用户USERID
	 * @param sessiontype:会话方式[1:URL|2:COOKIE]
	 * @param isqd:是否签到[1:签到|2:不签到]
	 * @return
	 *        0:不存在
	 *        1:存在
	 * 注释:
	 *    拦截器调用:用于核实会话是否过期，调用工具类
	 *    工具类对接：用于拦截器调用
	 */
	@Override
	public int selectIsExist(String systemid,String userid,String sessiontype,String ip,String isqd) {
		if(null==systemid||null==userid||null==sessiontype||systemid.equals("")||userid.equals("")||sessiontype.equals("")){
			return 0;
		}
		
		SessionBean sessionbean = new SessionBean(systemid,userid,sessiontype);
		if(sessiontype.equals("1")){
			sessionbean.setUcip(ip);
		}
		boolean flag = true;
		if(sessionDao.selectIsExist(sessionbean)==1){
			//签到会话
			if(isqd.equals("1")){
				sessionDao.updateqd(systemid);
			}
		}else{
			flag = false;
		}
		
		return flag?1:0;
	}

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
	@Override
	public String selectSessionValue(String systemid,String userid,String sessiontype, String key){
		if(null==systemid||null==userid||null==sessiontype||null==key||systemid.equals("")||userid.equals("")||sessiontype.equals("")||key.equals("")){
			return null;
		}
		SessionBean sessionbean = new SessionBean(systemid,userid,sessiontype);
		String mapjson = sessionDao.selectSessionValue(sessionbean);
		if(null==mapjson||mapjson.equals("")){
			return null;
		}
		Map<String,String> map = SystemUtil.INSTANCE.toObject(mapjson,Map.class); 
		if(null!= map&&map.containsKey(key)){
			return map.get(key)+"";
		}
		return null;
	}

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
	@Override
	public int updateSessionValue(String systemid,String userid,String sessiontype,String key,String value){
		if(null==key||null==value||null==systemid||null==userid||null==sessiontype||
		   key.equals("")||value.equals("")||systemid.equals("")||userid.equals("")||sessiontype.equals("")){
			return 0;
		}
		SessionBean sessionbean = new SessionBean(systemid,userid,sessiontype);
		
		String mapjson = sessionDao.selectSessionValue(sessionbean);
		Map<String,String> map = null;
		if(null==mapjson||mapjson.equals("")){
			map = new HashMap<String,String>();
		}else{
			map = SystemUtil.INSTANCE.toObject(mapjson,Map.class);
		}
		map.put(key,value);
		String keepmapjson = SystemUtil.INSTANCE.toJsonString(map);
		sessionbean.setSessionvalue(keepmapjson);
		return sessionDao.updateSessionValue(sessionbean);
	}

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
	@Override
	public int updateqd(String systemid) {
		return sessionDao.updateqd(systemid);
	}
	
}
