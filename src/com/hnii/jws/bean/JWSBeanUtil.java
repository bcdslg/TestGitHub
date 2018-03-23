package com.hnii.jws.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hnii.jws.client.ClientReqUtil;
import com.hnii.jws.dto.BaseDTOBean;
import com.hnii.jws.dto.JWSDTOBaseBean;
import com.hnii.jws.dto.WBUserDTOBean;
import com.hnii.jws.type.ReType;
import com.hnii.jws.util.JWSUtil;

/**
 * 接口对象管理工具类
 * @author dengwei
 *
 */
public enum JWSBeanUtil{
	INSTANCE;
	/**
	 * 0.上次加载配置文件时间（内部）
	 */
	private long lastloadtime;
	
	/**
	 * 1.本地属性文件对象
	 */
	private LocalBean localbean;
	/**
	 * 2.业务对象集合
	 */
	private List<YWBean> ywlist;
	/**
	 * 3.服务组列表集合
	 */
	private List<FWZBean> fwzlist;
	/**
	 * 4.应用对象集合
	 */
	private List<YYBean> yylist;
	/**
	 * 5.本地应用对象
	 */
	private YYBean localyybean;
	
	/**
	 * 1.应用启动
	 * @return:
	 *        0:启动成功
	 *        1:public属性配置文件，属性不完整
	 *        2:请求接口资源失败
	 *        3:解析JSON字符串出错或返回值为空
	 *        4:返回集合结果不全
	 */
	public String qd(){
		String localyyzbh = JWSUtil.INSTANCE.getPropertiy("public", "localyyzbh");
		String localyybh = JWSUtil.INSTANCE.getPropertiy("public", "localyybh");
		String qdpwd = JWSUtil.INSTANCE.getPropertiy("public", "qdpwd");
		String rloadywid = JWSUtil.INSTANCE.getPropertiy("public", "rloadywid");
		String fwzlx = JWSUtil.INSTANCE.getPropertiy("public", "fwzlx");
		if(localyyzbh.isEmpty()||localyybh.isEmpty()||qdpwd.isEmpty()||fwzlx.isEmpty()){
			return "1";
		}
		
		String qdywid = JWSUtil.INSTANCE.getPropertiy("public", "qdywid");
		String pzglwsdl = JWSUtil.INSTANCE.getPropertiy("public", "pzglwsdl");
		String pzgluserid = JWSUtil.INSTANCE.getPropertiy("public", "pzgluserid");
		String pzglpwd = JWSUtil.INSTANCE.getPropertiy("public", "pzglpwd");
		String pzglfwzbh = JWSUtil.INSTANCE.getPropertiy("public", "pzglfwzbh");
		String reloadpropminstr = JWSUtil.INSTANCE.getPropertiy("public", "reloadpropmin");
		int reloadpropmin = Integer.parseInt(reloadpropminstr);
		String yyclspringbean = JWSUtil.INSTANCE.getPropertiy("public", "yyclspringbean");
		String qdservicename = JWSUtil.INSTANCE.getPropertiy("public", "qdservicename");
		String isloaduserinfo = JWSUtil.INSTANCE.getPropertiy("public", "isloaduserinfo");
		String loaduserinfoywid = JWSUtil.INSTANCE.getPropertiy("public", "loaduserinfoywid");
		String utokenywid = JWSUtil.INSTANCE.getPropertiy("public", "utokenywid");
		String hhywbh = null==JWSUtil.INSTANCE.getPropertiy("public", "hhywbh")?"":JWSUtil.INSTANCE.getPropertiy("public", "hhywbh");
		
		if(qdywid.isEmpty()||pzglwsdl.isEmpty()||pzglfwzbh.isEmpty()){
			return "1";
		}
		
		this.localbean = new LocalBean();
		this.localbean.setLocalyyzbh(localyyzbh);
		this.localbean.setLocalyybh(localyybh);
		this.localbean.setQdpwd(qdpwd);
		this.localbean.setFwzlx(fwzlx);
		this.localbean.setQdywbh(qdywid);
		this.localbean.setPzglwsdl(pzglwsdl);
		this.localbean.setPzgluserid(pzgluserid);
		this.localbean.setPzglpwd(pzglpwd);
		this.localbean.setPzglfwzbh(pzglfwzbh);
		this.localbean.setReloadpropmin(reloadpropmin);
		this.localbean.setRloadywid(rloadywid);
		this.localbean.setYyclspringbean(yyclspringbean);
		this.localbean.setQdservicename(qdservicename);
		this.localbean.setIsloaduserinfo(isloaduserinfo);
		this.localbean.setLoaduserinfoywid(loaduserinfoywid);
		this.localbean.setUtokenywid(utokenywid);
		this.localbean.setHhywbh(hhywbh);
		
		//调用启动接口，获取数据
		String rjsonstr = ClientReqUtil.INSTANCE.qd();
		//System.out.println("=======>>>>启动 rjsonstr = "+rjsonstr);
		if(null==rjsonstr||rjsonstr.isEmpty()){
			return "3";
		}
		JWSDTOBaseBean databean = JWSUtil.INSTANCE.toObject(rjsonstr,JWSDTOBaseBean.class);
		if(null==databean){
			return "3";
		}
		if(databean.getReflag().equals(ReType.SUCCESS)){
			//提取业务数据
			List<YWBean> rywlist = databean.getYwlist();
			List<FWZBean> rfwzlist = databean.getFwzlist();
			List<YYBean> ryylist = databean.getYylist();
			if(null==rywlist||null==rfwzlist||null==ryylist){
				return "4";
			}
			this.ywlist = rywlist;
			this.fwzlist = rfwzlist;
			this.yylist = ryylist;
			this.localyybean = this.getyybean(this.localbean.getLocalyybh());
			//提取本地的动态启动码
			this.localbean.setRunid(databean.getRunid());
			//System.out.println("========runid = "+databean.getRunid());
			this.lastloadtime = System.currentTimeMillis();
		}else{
			System.out.println("==========>>>>启动——加载接口交互配置文件错误: "+databean.getFalseinfo());
			return "2";
		}
		this.loadwbuserinfo();
		return "0";
	}
	
	/**
	 * 2.请求接口，加载属性文件
	 * @return:
	 * 	      0:加载成功
	 * 		  2:请求接口资源失败
	 *        3:解析JSON字符串出错或返回值为空
	 *        4:返回集合结果不全
	 */
	public synchronized String loadsystemprop(){
		if(System.currentTimeMillis() - this.lastloadtime < 60000){//上次更新未满一分钟
			return "0";
		}
		Map<String,Object> reqparammap = new HashMap<String,Object>();
		reqparammap.put("yybh",this.localbean.getLocalyybh());
		String rjsonstr = ClientReqUtil.INSTANCE.proxyFWZwsRequst(this.localbean.getRloadywid(), reqparammap);
		this.lastloadtime = System.currentTimeMillis();
		//System.out.println("=======>>>>定时器 rjsonstr = "+rjsonstr);
		if(null==rjsonstr||rjsonstr.isEmpty()){
			return "3";
		}
		JWSDTOBaseBean databean = JWSUtil.INSTANCE.toObject(rjsonstr,JWSDTOBaseBean.class);
		if(null==databean){
			return "3";
		}
		if(databean.getReflag().equals(ReType.SUCCESS)){
			//提取业务数据
			List<YWBean> rywlist = databean.getYwlist();
			List<FWZBean> rfwzlist = databean.getFwzlist();
			List<YYBean> ryylist = databean.getYylist();
			if(null==rywlist||null==rfwzlist||null==ryylist){
				return "4";
			}
			this.ywlist = rywlist;
			this.fwzlist = rfwzlist;
			this.yylist = ryylist;
			this.localyybean = this.getyybean(this.localbean.getLocalyybh());
		}else{
			System.out.println("==========>>>>定时器——加载接口交互配置文件错误: "+databean.getFalseinfo());
			return "2";
		}
		
		this.loadwbuserinfo();
		return "0";
	}
	
	/**
	 * 3.获取应用请求信息
	 * @param targetId:目标应用ID
	 * @return:
	 *       本地为目标应用，直接处理 [0,描述语言]
	 *    	  目标应用或所属服务组不存在[1,描述语言]
	 *       请求地址消息[请求地址,请求用户名，请求密码]
	 */
	public List<String> getyyreqinfo(String targetId){
		List<String> rlist = new ArrayList<String>();
		//0.本地为目标应用，可以直接处理
		if(this.localbean.getLocalyybh().equals(targetId)){
			rlist.add("0");
			rlist.add("本地为目标应用，直接处理");
			return rlist;
		}
		//1.目标应用不存在
		YYBean targetyybean = null;
		for(YYBean yybean:this.yylist){
			if(yybean.getSystemid().equals(targetId)){
				targetyybean = yybean;
				break;
			}
		}
		if(null==targetyybean){
			loadsystemprop();//重新加载接口配置属性文件
			for(YYBean yybean:this.yylist){
				if(yybean.getSystemid().equals(targetId)){
					targetyybean = yybean;
					break;
				}
			}
			if(null==targetyybean){
				rlist.add("1");
				rlist.add("请求的目标应用不存在,请求的应用编号为:["+targetId+"]");
				return rlist;
			}
		}
		
		//2.返回请求信息
		String rwsdlurl = "";
		if(targetyybean.getJwswwipsystemid().equals(this.localyybean.getJwswwipsystemid())){//2.1.外网IP相同，使用内网配置信息
			String vfs = targetyybean.getJwsnwvfs();
			if(vfs.equals("1")){//1:IP访问
				String nwip = targetyybean.getJwsnwip();
				if(null==nwip||nwip.isEmpty()){//内网IP地址为空，则使用外网的IP信息
					rwsdlurl = targetyybean.getJwswwipqz()+targetyybean.getJwswwip()+targetyybean.getJwswwiphz()+"?wsdl";
				}else{
					rwsdlurl = targetyybean.getJwsnwipqz()+targetyybean.getJwsnwip()+targetyybean.getJwsnwiphz()+"?wsdl";
				}
			}else{//2:域名访问 
				String nwym = targetyybean.getJwsnwymdz();
				if(null==nwym||nwym.isEmpty()){//内网域名地址为空，则使用外网域名地址
					rwsdlurl = targetyybean.getJwswwymdz()+"?wsdl";
				}else{
					rwsdlurl = targetyybean.getJwsnwymdz()+"?wsdl";
				}
			}
		}else{//2.2.外网IP不相同，使用外网配置信息
			String vfs = targetyybean.getJwswwvfs();
			if(vfs.equals("1")){//1:IP访问
				rwsdlurl = targetyybean.getJwswwipqz()+targetyybean.getJwswwip()+targetyybean.getJwswwiphz()+"?wsdl";
			}else{//2:域名访问 
				rwsdlurl = targetyybean.getJwswwymdz()+"?wsdl";
			}
		}
		FWZBean fwzbean = this.getfwzbean(targetyybean.getFwzsystemid());
		if(null==fwzbean){
			rlist.add("1");
			rlist.add("请求的目标应用关联的服务组不存在，服务组编号:["+targetyybean.getFwzsystemid()+"]");
			return rlist;
		}
		
		rlist.add(rwsdlurl);
		rlist.add(fwzbean.getJwsuserid());
		rlist.add(fwzbean.getJwspwd());
		return rlist;
	}
	
	/**
	 * 4.获取业务对应服务组请求信息
	 * @param ywbh:业务编号
	 * @return:
	 * 		  业务编号不存在 [1,描述语言]
	 * 		  属于本应用服务组业务，直接处理[0,业务配置bean名称]
	 *       请求地址信息[请求地址,请求用户名,请求密码,请求目标服务组编号]
	 */
	public List<String> getfwzreqinfo(String ywbh){
		List<String> rlist = new ArrayList<String>();
		//1.业务编号是否存在
		YWBean ywbean = null;
		for(YWBean lywbean:this.ywlist){
			if(lywbean.getYwbh().equals(ywbh)){
				ywbean = lywbean;
				break;
			}
		}
		if(null==ywbean){
			loadsystemprop();//重新加载接口配置属性文件
			for(YWBean lywbean:this.ywlist){
				if(lywbean.getYwbh().equals(ywbh)){
					ywbean = lywbean;
					break;
				}
			}
			if(null==ywbean){
				rlist.add("1");
				rlist.add("请求的业务编号["+ywbh+"]不存在");
				return rlist;
			}
		}
		//2.属于本应用服务组业务，直接处理[0,描述语言]
		if(ywbean.getYyfwzsystemid().equals(this.localbean.getLocalyyzbh())){
			rlist.add("0");
			rlist.add(ywbean.getServicename());
			return rlist;
		}
		
		//3.请求地址信息
		FWZBean fwzbean = this.getfwzbean(ywbean.getYyfwzsystemid());
		if(null==fwzbean){
			rlist.add("1");
			rlist.add("请求的业务编号["+ywbh+"]对应的服务组编号["+ywbean.getYyfwzsystemid()+"]不存在");
			return rlist;
		}
		String rwsdlurl = "";
		String vfs = fwzbean.getJwsvfs();
		if(vfs.equals("1")){//1:IP访问
			rwsdlurl = fwzbean.getJwsipqz()+fwzbean.getJwsip()+fwzbean.getJwsiphz()+"?wsdl";
		}else{//2:域名访问 
			rwsdlurl = fwzbean.getJwsymdz()+"?wsdl";
		}
		
		rlist.add(rwsdlurl);
		rlist.add(fwzbean.getJwsuserid());
		rlist.add(fwzbean.getJwspwd());
		rlist.add(fwzbean.getSystemid());
		return rlist;
	}
	/**
	 * 4.1.根据服务组编号，获取请求地址
	 * @param fwzbh:服务组编号
	 * @return:
	 * 		     服务组编号不存在 [1,描述语言]
	 *        请求地址信息[请求地址,请求用户名,请求密码]
	 */
	public List<String> getfwzbyfwzbhreqinfo(String fwzbh){
		List<String> rlist = new ArrayList<String>();
		//1.服务组编号不存在
		FWZBean fwzbean = this.getfwzbean(fwzbh);
		if(null==fwzbean){
			rlist.add("1");
			rlist.add("请求的服务组编号["+fwzbh+"]不存在");
			return rlist;
		}
		String rwsdlurl = "";
		String vfs = fwzbean.getJwsvfs();
		if(vfs.equals("1")){//1:IP访问
			rwsdlurl = fwzbean.getJwsipqz()+fwzbean.getJwsip()+fwzbean.getJwsiphz()+"?wsdl";
		}else{//2:域名访问 
			rwsdlurl = fwzbean.getJwsymdz()+"?wsdl";
		}
		
		rlist.add(rwsdlurl);
		rlist.add(fwzbean.getJwsuserid());
		rlist.add(fwzbean.getJwspwd());
		return rlist;
	}
	
	/**
	 * 5.验证客户端来源
	 * @param type:启动(qd)|业务(yw)
	 * @param yybh:应用编号
	 * @param fwzlx:发起应用所属服务组类型
	 * @param pwd:校验码
	 * @return
	 * 		  1:成功
	 *        2:失败，应用编号不能存在
	 *        3:失败，应用编号与启动密码或运行编号不匹配
	 *        4:失败，应用编号对应的服务组类型不匹配
	 *        5:失败，应用启动时，应用编号与登录IP不匹配 (启动类型才核实)
	 */
	public String vcomefrom(String type,String yybh,String fwzlx,String pwd,String ip){
		boolean reloadflag = false;
		//2.应用编号是否存在
		YYBean yybean = this.getyybean(yybh);
		if(null==yybean){
			if(!reloadflag){
				this.loadsystemprop();
				reloadflag = true;
				yybean = this.getyybean(yybh);
				if(null==yybean){
					return "2";
				}
			}else{
				return "2";
			}
		}
		//3.应用启动密码或运行编号是否匹配
		if(type.equals("qd")){//启动，核实启动密码
			boolean hsflag = JWSUtil.INSTANCE.checkEncryptStr(pwd, yybean.getYyqdpwd());
			if(!hsflag){
				if(!reloadflag){
					this.loadsystemprop();
					reloadflag = true;
					yybean = this.getyybean(yybh);
					hsflag = JWSUtil.INSTANCE.checkEncryptStr(pwd, yybean.getYyqdpwd());
					if(!hsflag){
						return "3";
					}
				}else{
					return "3";
				}
			}
		}else{//业务，核实运行编号
			if(!(JWSUtil.INSTANCE.checkEncryptStr(pwd, yybean.getYyrunid()))){
				if(!reloadflag){
					this.loadsystemprop();
					reloadflag = true;
					yybean = this.getyybean(yybh);
					if(!(JWSUtil.INSTANCE.checkEncryptStr(pwd, yybean.getYyrunid()))){
						return "3";
					}
				}else{
					return "3";
				}
			}
		}
		//4.应用与登记的服务组类型是否匹配
		if(!(this.getfwzbean(yybean.getFwzsystemid()).getFwzlx().equals(fwzlx))){
			if(!reloadflag){
				this.loadsystemprop();
				reloadflag = true;
				yybean = this.getyybean(yybh);
				if(!(this.getfwzbean(yybean.getFwzsystemid()).getFwzlx().equals(fwzlx))){
					return "4";
				}
			}else{
				return "4";
			}
		}
		//5.应用启动时，应用编号与登录IP不匹配
		if(type.equals("qd")){
			String wwip = null==yybean.getJwswwip()?"":yybean.getJwswwip();
			String nwip = null==yybean.getJwsnwip()?"":yybean.getJwsnwip();
			if(!(null!=ip&&(wwip.indexOf(ip)>-1||nwip.indexOf(ip)>-1)&&ip.length()>0)){
				if(!reloadflag){
					this.loadsystemprop();
					reloadflag = true;
					yybean = this.getyybean(yybh);
					wwip = null==yybean.getJwswwip()?"":yybean.getJwswwip();
					nwip = null==yybean.getJwsnwip()?"":yybean.getJwsnwip();
					if(!(null!=ip&&(wwip.indexOf(ip)>-1||nwip.indexOf(ip)>-1)&&ip.length()>0)){
						return "5";
					}
				}else{
					return "5";
				}
			}
		}
		return "1";
	}
	
	/**
	 * 0.1.获取本地属性对象
	 * @return
	 */
	public LocalBean getLocalbean() {
		return localbean;
	}
	/**
	 * 0.2.根据业务编号获取业务对象
	 * @param ywbh:业务编号
	 * @return
	 */
	public YWBean getywbean(String ywbh){
		if(null==this.ywlist){
			return null;
		}
		for(YWBean ywbean:this.ywlist){
			if(ywbean.getYwbh().equals(ywbh)){
				return ywbean;
			}
		}
		return null;
	}
	/**
	 * 0.3.根据服务组编号获取服务组对象
	 * @param systemid:服务组编号
	 * @return
	 */
	public FWZBean getfwzbean(String systemid){
		if(null==this.fwzlist){
			return null;
		}
		for(FWZBean fwzbean:this.fwzlist){
			if(fwzbean.getSystemid().equals(systemid)){
				return fwzbean;
			}
		}
		return null;
	}
	/**
	 * 0.4.根据应用编号获取应用对象
	 * @param systemid:应用编号
	 * @return
	 */
	public YYBean getyybean(String systemid){
		if(null==this.yylist){
			return null;
		}
		for(YYBean yybean:this.yylist){
			if(yybean.getSystemid().equals(systemid)){
				return yybean;
			}
		}
		return null;
	}

/////////////////////////////////////外部用户 BEGIN/////////////////////////////////////////////////////////////////////////
	/**
	 * 1.外部用户列表
	 */
	private List<UserBean> userlist;
	/**
	 * 2.外部用户所属IP对象
	 */
	private List<UserIPBean> useriplist;
	/**
	 * 3.用户业务对应对象
	 */
	private List<UserYWBean> userywlist;
	
	/**
	 * 1.加载外部用户相关信息
	 * @return:
	 *        0:加载成功
	 *        1:加载失败
	 */
	public synchronized String loadwbuserinfo(){
		if(this.localbean.getIsloaduserinfo().equals("1")){
			Map<String,Object> reqparammap = new HashMap<String,Object>();
			reqparammap.put("yybh",this.localbean.getLocalyybh());
			reqparammap.put("fwzbh",this.localbean.getLocalyyzbh());
			reqparammap.put("fwzlx",this.localbean.getFwzlx());
			String rjsonstr = ClientReqUtil.INSTANCE.proxyFWZwsRequst(this.localbean.getLoaduserinfoywid(), reqparammap);
			WBUserDTOBean wbuserbean = JWSUtil.INSTANCE.toObject(rjsonstr,WBUserDTOBean.class);
			if(wbuserbean.getReflag().equals(ReType.ERROR)){
				System.out.println("====================>>>>> 加载外部用户信息出错："+wbuserbean.getFalseinfo());
				return "1";
			}
			this.userlist = wbuserbean.getUserlist();
			this.useriplist = wbuserbean.getUseriplist();
			this.userywlist = wbuserbean.getUserywlist();
		}
		return "0";
	}
	/**
	 * 2.外部用户登录
	 * @param userid:用户ID
	 * @param pwd:用户密码
	 * @param userip:用户机子IP地址
	 * @return:
	 *       loginflag:0(登录失败)|1(登录成功)
	 *       falseinfo:登录失败信息    
	 *       token:返回token值(60长度字符串),服务器端加密存储
	 */
	public Map<String,String> wbuserlogin(String userid,String pwd,String userip){
		Map<String,String> rmap = new HashMap<String,String>();
		//1.核实用户ID与密码
		boolean checkuserflag = false;
		for(UserBean userbean:this.userlist){
			if(userbean.getYwuserid().equals(userid)&&JWSUtil.INSTANCE.checkEncryptStr(pwd,userbean.getYwpwd())){
				checkuserflag = true;
				break;
			}
		}
		if(!checkuserflag){
			rmap.put("loginflag", "0");
			rmap.put("falseinfo", "用户ID与密码不匹配，请核实");
			return rmap;
		}
		//2.核实用户ID登记IP地址
		UserIPBean luseripbean = null;
		for(UserIPBean useripbean:this.useriplist){
			if(useripbean.getYwuserid().equals(userid)&&useripbean.getIp().equals(userip)){
				luseripbean = useripbean;
			}
		}
		if(null==luseripbean){
			rmap.put("loginflag", "0");
			rmap.put("falseinfo", "用户ID下属无该IP信息，IP值:"+userip);
			return rmap;
		}
		
		String token = JWSUtil.INSTANCE.getToken();
		//3.存储token到平台
		Map<String,Object> reqparammap = new HashMap<String,Object>();
		reqparammap.put("token",token);
		reqparammap.put("systemid",luseripbean.getSystemid());
		String rjsonstr = ClientReqUtil.INSTANCE.proxyFWZwsRequst(this.localbean.getUtokenywid(), reqparammap);
		BaseDTOBean rbean = JWSUtil.INSTANCE.toObject(rjsonstr,BaseDTOBean.class);
		if(rbean.getReflag().equals(ReType.ERROR)){
			rmap.put("loginflag", "0");
			rmap.put("falseinfo", rbean.getFalseinfo());
			return rmap;
		}
		//4.返回token值
		rmap.put("loginflag", "1");
		rmap.put("token",token);
		luseripbean.setToken(JWSUtil.INSTANCE.encryptStr(token));
		return rmap;
	}
	
	/**
	 * 3.核实用户身份与操作权限
	 * @param userid
	 * @param token
	 * @param userip
	 * @param ywbh
	 * @return:
	 *        vflag:0(验证失败)|1(验证成功)
	 *        falseinfo:失败原因描述
	 *        sjqxjson:返回用户的数据操作权限JSON数据（有可能为空）
	 */
	public Map<String,String> checkuserpower(String userid,String token,String userip,String ywbh){
		Map<String,String> rmap = new HashMap<String,String>();
		//1.是否有该用户IP
		UserIPBean luseripbean = null;
		for(UserIPBean useripbean:this.useriplist){
			if(userid.equals(useripbean.getYwuserid())&&userip.equals(useripbean.getIp())){
				luseripbean = useripbean;
			}
		}
		if(null==luseripbean){
			rmap.put("vflag","0");
			rmap.put("falseinfo","用户ID下属无该IP信息，IP值:"+userip);
			return rmap;
		}
		//2.核实token信息,核实失败，则需要重新加载外部用户信息，在次核实信息
		if((null==luseripbean.getToken()||luseripbean.getToken().isEmpty())||(!JWSUtil.INSTANCE.checkEncryptStr(token,luseripbean.getToken()))){
			this.loadwbuserinfo();
			//2.1.是否有该用户IP
			for(UserIPBean useripbean:this.useriplist){
				if(userid.equals(useripbean.getYwuserid())&&userip.equals(useripbean.getIp())){
					luseripbean = useripbean;
				}
			}
			if(null==luseripbean){
				rmap.put("vflag","0");
				rmap.put("falseinfo","用户ID下属无该IP信息，IP值:"+userip);
				return rmap;
			}
			//2.2.核实token信息,核实失败，无需在加载信息
			if(!JWSUtil.INSTANCE.checkEncryptStr(token, luseripbean.getToken())){
				rmap.put("vflag","0");
				rmap.put("falseinfo","token校验失败，请重新登录获取token值");
				return rmap;
			}
		}
		//3.核实业务操作权限
		UserYWBean luserywbean = null;
		for(UserYWBean userywbean:this.userywlist){
			if(userid.equals(userywbean.getYwuserid())&&ywbh.equals(userywbean.getYwbh())){
				luserywbean = userywbean;
				break;
			}
		}
		if(null==luserywbean){
			rmap.put("vflag","0");
			rmap.put("falseinfo","该用户["+userid+"]与业务编号["+ywbh+"],无对应操作权限绑定，请核实");
			return rmap;
		}
		//4.返回操作权限数据
		rmap.put("vflag","1");
		rmap.put("sjqxjson", luserywbean.getSjqxjson());
		return rmap;
	}
	/**
	 * 4.是否直接处理业务
	 * @param ywbh
	 * @return
	 */
	public boolean sfzjclyw(String ywbh){
		if(this.localbean.getHhywbh().isEmpty()){
			return false;
		}
		String[] ywlist = this.localbean.getHhywbh().split(",");
		boolean flag = false;
		for(int i = 0;i < ywlist.length;i++){
			if(ywlist[i].equals(ywbh)){
				return true;
			}
		}
		return flag;
	}
/////////////////////////////////////外部用户 END/////////////////////////////////////////////////////////////////////////
}
