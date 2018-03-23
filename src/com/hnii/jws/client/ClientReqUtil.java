package com.hnii.jws.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import com.hnii.jws.bean.JWSBeanUtil;
import com.hnii.jws.bean.LocalBean;
import com.hnii.jws.dto.BaseDTOBean;
import com.hnii.jws.iservice.IHniiService;
import com.hnii.jws.type.ReType;
import com.hnii.jws.type.TargetType;
import com.hnii.jws.util.JWSUtil;
import com.hnii.scweb.util.SpringTool;

/**
 * 客户端发送请求工具类
 * 依赖 springtool类
 * @author dengwei
 *
 */
public enum ClientReqUtil {
	INSTANCE;
	
	private String targetNamespace="http://service.jws.hnii.com/";
	private String targetService = "HniiJaxWsServerImplService";
	private String filepath = "";//文件路径(缓存)
	
	/**
	 * 1.0.普通业务请求
	 * @param rwsdlurl:请求的wsdl文件地址，类似http://localhost:8080/JWSS/jaxws?wsdl
	 * @param ruserid:请求的用户名  【容器配置】
	 * @param rpwd:请求的密码  【容器配置】
	 * @param yybh:发送请求的应用编号
	 * @param runid:发送请求的应用启动编号
	 * @param fwzlx:发送请求的应用所属服务组类型
	 * @param targetType:目标类型[1:应用服务组|2:应用]
	 * @param targetId:目标标识符
	 * @param ywbh:请求的业务编号
	 * @param reqparamjsonstr:请求参数JSON字符串
	 * @return:返回JSON字符串
	 */
	private String wsRequst(String rwsdlurl,String ruserid,String rpwd,
			String yybh, String runid, String fwzlx,String targetType,
			String targetId,String ywbh, String reqparamjsonstr){
		String rfilepath = (null==filepath||filepath.equals(""))?getFilePath():filepath;
		filepath = rfilepath;
		URL url = null;
		try {
			String path = "file:"+rfilepath;
			path = path.replaceAll("\\\\", "//");
			url = new URL(path+"hniiwsdl//hniijaxws.wsdl");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		QName qname = new QName(targetNamespace,targetService);
		Service service = Service.create(url, qname);
		
		IHniiJaxWsServer se = service.getPort(IHniiJaxWsServer.class);
		BindingProvider bp = (BindingProvider) se;
		if(!(null==ruserid||null==rpwd||ruserid.isEmpty()||rpwd.isEmpty())){
			bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,ruserid);
	        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, rpwd);
		}
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,rwsdlurl);
		
        String trunid = null==runid?"":runid;
        String repjsonstr = se.wsRequst(yybh, trunid, fwzlx, targetType, targetId, ywbh, reqparamjsonstr);
		return repjsonstr;
	}
	/**
	 * 1.1.目标类型为应用
	 * @param ywbh:请求的业务编号
	 * @param targetId:应用编号
	 * @param reqparammap:请求参数MAP对象
	 * @return:
	 * 		  本地为目标应用，直接处理 [0,描述语言]
	 *    	  目标应用或所属服务组不存在[1,描述语言]
	 *       请求地址消息[请求地址,请求用户名，请求密码]
	 */
	public String proxyYYwsRequst(String ywbh,String targetId,Map<String,Object> reqparammap){
		List<String> list = JWSBeanUtil.INSTANCE.getyyreqinfo(targetId);
		if(null!=list&&list.size()>0){
			int length = list.size();
			if(length==2){
				if(list.get(0).equals("0")){//本地为目标应用，直接处理
					IHniiService hniiservice = (IHniiService)SpringTool.getBean(JWSBeanUtil.INSTANCE.getLocalbean().getYyclspringbean());
					return hniiservice.jwsopt(ywbh,JWSUtil.INSTANCE.toJsonString(reqparammap));
				}else if(list.get(0).equals("1")){//目标应用或所属服务组不存在
					BaseDTOBean bean = new BaseDTOBean();
					bean.setReflag(ReType.ERROR);
					bean.setFalseinfo(list.get(1)+",执行应用编号["+JWSBeanUtil.INSTANCE.getLocalbean().getLocalyybh()+"]");
					return JWSUtil.INSTANCE.toJsonString(bean);
				}
			}
			if(length==3){
				LocalBean localbean = JWSBeanUtil.INSTANCE.getLocalbean();
				return wsRequst(list.get(0),list.get(1),list.get(2),localbean.getLocalyybh(),localbean.getRunid(),localbean.getFwzlx(),TargetType.YY,
					   targetId,ywbh,JWSUtil.INSTANCE.toJsonString(reqparammap));
			}
		}
		BaseDTOBean bean = new BaseDTOBean();
		bean.setReflag(ReType.ERROR);
		bean.setFalseinfo("请求应用，参数无法正确获取，出错应用编号["+JWSBeanUtil.INSTANCE.getLocalbean().getLocalyybh()+"]");
		return JWSUtil.INSTANCE.toJsonString(bean);
	}
	/**
	 * 1.2.1.目标类型为服务组
	 * @param ywbh:业务编号
	 * @param reqparammap:请求参数MAP对象
	 * @return
	 * 		  业务编号不存在 [1,描述语言]
	 * 		  属于本应用服务组业务，直接处理[0,业务配置bean名称]
	 *       请求地址信息[请求地址,请求用户名,请求密码,目标应用编号]
	 */
	public String proxyFWZwsRequst(String ywbh,Map<String,Object> reqparammap){
		List<String> list = JWSBeanUtil.INSTANCE.getfwzreqinfo(ywbh);
		//System.out.println("========>>>>> "+list);
		if(null!=list&&list.size()>0){
			int length = list.size();
			if(length==2){
				if(list.get(0).equals("0")){//属于本应用服务组业务，直接处理
					IHniiService hniiservice = (IHniiService)SpringTool.getBean(list.get(1));
					return hniiservice.jwsopt(ywbh,JWSUtil.INSTANCE.toJsonString(reqparammap));
				}else if(list.get(0).equals("1")){//业务编号不存在
					BaseDTOBean bean = new BaseDTOBean();
					bean.setReflag(ReType.ERROR);
					bean.setFalseinfo(list.get(1)+",执行应用编号["+JWSBeanUtil.INSTANCE.getLocalbean().getLocalyybh()+"]");
					return JWSUtil.INSTANCE.toJsonString(bean);
				}
			}
			if(length==4){
				LocalBean localbean = JWSBeanUtil.INSTANCE.getLocalbean();
//				System.out.println("==============");
//				System.out.println("yybh = "+localbean.getLocalyybh());
//				System.out.println("runid = "+localbean.getRunid());
//				System.out.println("==============");
				return wsRequst(list.get(0),list.get(1),list.get(2),localbean.getLocalyybh(),localbean.getRunid(),localbean.getFwzlx(),TargetType.FWZ,
						list.get(3),ywbh,JWSUtil.INSTANCE.toJsonString(reqparammap));
			}
		}
		BaseDTOBean bean = new BaseDTOBean();
		bean.setReflag(ReType.ERROR);
		bean.setFalseinfo("请求服务组，业务编号["+ywbh+"]，参数无法正确获取，出错应用编号["+JWSBeanUtil.INSTANCE.getLocalbean().getLocalyybh()+"]");
		return JWSUtil.INSTANCE.toJsonString(bean);
	}
	/**
	 * 1.2.2.按服务组编号，请求服务
	 * @param fwzbh:服务组编号
	 * @param reqparammap:请求参数
	 * @return
	 * 		请求地址信息[请求地址,请求用户名,请求密码]
	 */
	public String proxyFWZHHwsRequst(String fwzbh,String ywbh,Map<String,Object> reqparammap){
		List<String> list = JWSBeanUtil.INSTANCE.getfwzbyfwzbhreqinfo(fwzbh);
		if(null!=list&&list.size()>0){
			int length = list.size();
			if(length==2){
				BaseDTOBean bean = new BaseDTOBean();
				bean.setReflag(ReType.ERROR);
				bean.setFalseinfo(list.get(1)+",执行应用编号["+JWSBeanUtil.INSTANCE.getLocalbean().getLocalyybh()+"]");
				return JWSUtil.INSTANCE.toJsonString(bean);
			}
			if(length==4){
				LocalBean localbean = JWSBeanUtil.INSTANCE.getLocalbean();
				return wsRequst(list.get(0),list.get(1),list.get(2),localbean.getLocalyybh(),localbean.getRunid(),localbean.getFwzlx(),TargetType.FWZ,
						fwzbh,ywbh,JWSUtil.INSTANCE.toJsonString(reqparammap));
			}
		}
		BaseDTOBean bean = new BaseDTOBean();
		bean.setReflag(ReType.ERROR);
		bean.setFalseinfo("请求服务组编号["+fwzbh+"],异常");
		return JWSUtil.INSTANCE.toJsonString(bean);
	}
	
	/**
	 * 1.3.请求接口获取平台配置信息
	 * @return:
	 * 		 返回结果JSON文件
	 */
	public String qd(){
		LocalBean localbean = JWSBeanUtil.INSTANCE.getLocalbean();
		if(localbean.getLocalyyzbh().equals(localbean.getPzglfwzbh())){
			IHniiService hniiservice = (IHniiService)SpringTool.getBean(localbean.getQdservicename());
			Map<String,String> selfmap = new HashMap<String,String>();
			selfmap.put("yybh", localbean.getLocalyybh());
			selfmap.put("fwzlx", localbean.getFwzlx());
			selfmap.put("ywbh", localbean.getQdywbh());
			selfmap.put("qdpwd", localbean.getQdpwd());
			return hniiservice.jwsopt(localbean.getQdywbh(),JWSUtil.INSTANCE.toJsonString(selfmap));
		}
		
		String rwsdlurl = localbean.getPzglwsdl();
		String ruserid = localbean.getPzgluserid();
		String rpwd = localbean.getPzglpwd();
		String yybh = localbean.getLocalyybh();
		String runid = "";
		String fwzlx = localbean.getFwzlx();
		String targetType = TargetType.FWZ;
		String targetId = localbean.getPzglfwzbh();
		String ywbh = localbean.getQdywbh();
		
		Map<String,String> rmap = new HashMap<String,String>();
		rmap.put("yybh", yybh);
		rmap.put("fwzlx", fwzlx);
		rmap.put("ywbh", ywbh);
		rmap.put("qdpwd", localbean.getQdpwd());
		String reqparamjsonstr = JWSUtil.INSTANCE.toJsonString(rmap);
		return this.wsRequst(rwsdlurl, ruserid, rpwd, yybh, runid,fwzlx, targetType, targetId, ywbh, reqparamjsonstr); 
	}
	
	/**
	 * 2.0.附带二进制文件的业务请求
	 * @param rwsdlurl:请求的wsdl文件地址，类似http://localhost:8080/JWSS/jaxws?wsdl
	 * @param ruserid:请求的用户名  【容器配置】
	 * @param rpwd:请求的密码  【容器配置】
	 * @param yybh:发送请求的应用编号
	 * @param runid:发送请求的应用启动编号
	 * @param fwzlx:发送请求的应用所属服务组类型
	 * @param targetType:目标类型[1:应用服务组|2:应用]
	 * @param targetId:目标标识符
	 * @param ywbh:请求的业务编号
	 * @param reqparamjsonstr:请求参数JSON字符串
	 * @param filecontent:二进制文件内容
	 * @return:返回JSON字符串
	 */
	private String wsFile(String rwsdlurl,String ruserid,String rpwd,
			String yybh, String runid,String fwzlx,String targetType,
			String targetId,String ywbh,String reqparamjsonstr,byte[] filecontent){
		String rfilepath = (null==filepath||filepath.equals(""))?getFilePath():filepath;
		filepath = rfilepath;
		URL url = null;
		try {
			String path = "file:"+rfilepath;
			path = path.replaceAll("\\\\", "//");
			url = new URL(path+"hniiwsdl//hniijaxws.wsdl");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		QName qname = new QName(targetNamespace,targetService);
		Service service = Service.create(url, qname);
		
		IHniiJaxWsServer se = service.getPort(IHniiJaxWsServer.class);

		BindingProvider bp = (BindingProvider) se;
		if(!(null==ruserid||null==rpwd||ruserid.isEmpty()||rpwd.isEmpty())){
			bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,ruserid);
	        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, rpwd);
		}
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,rwsdlurl);
        String repjsonstr = se.wsFile(yybh, runid, fwzlx, targetType, targetId, ywbh, reqparamjsonstr, filecontent);
		return repjsonstr;
	}
	
	/**
	 * 2.1.目标类型为应用
	 * @param ywbh:请求的业务编号
	 * @param targetId:应用编号
	 * @param reqparammap:请求参数MAP对象
	 * @param filecontent:二进制文件内容
	 * @return
	 * 		   本地为目标应用，直接处理 [0,描述语言]
	 *    	   目标应用或所属服务组不存在[1,描述语言]
	 *       请求地址消息[请求地址,请求用户名，请求密码]
	 */
	public String proxyYYwsFile(String ywbh,String targetId,Map<String,Object> reqparammap,byte[] filecontent){
		List<String> list = JWSBeanUtil.INSTANCE.getyyreqinfo(targetId);
		if(null!=list&&list.size()>0){
			int length = list.size();
			if(length==2){
				if(list.get(0).equals("0")){//本地为目标应用，直接处理
					IHniiService hniiservice = (IHniiService)SpringTool.getBean(JWSBeanUtil.INSTANCE.getLocalbean().getYyclspringbean());
					return hniiservice.jwsopt(ywbh,JWSUtil.INSTANCE.toJsonString(reqparammap),filecontent);
				}else if(list.get(0).equals("1")){//目标应用或所属服务组不存在
					BaseDTOBean bean = new BaseDTOBean();
					bean.setReflag(ReType.ERROR);
					bean.setFalseinfo(list.get(1)+",携带附件,执行应用编号["+JWSBeanUtil.INSTANCE.getLocalbean().getLocalyybh()+"]");
					return JWSUtil.INSTANCE.toJsonString(bean);
				}
			}
			if(length==3){
				LocalBean localbean = JWSBeanUtil.INSTANCE.getLocalbean();
				return wsFile(list.get(0),list.get(1),list.get(2),localbean.getLocalyybh(),localbean.getRunid(),localbean.getFwzlx(),TargetType.YY,
							  targetId,ywbh,JWSUtil.INSTANCE.toJsonString(reqparammap),filecontent);
			}
		}
		BaseDTOBean bean = new BaseDTOBean();
		bean.setReflag(ReType.ERROR);
		bean.setFalseinfo("请求应用，携带附件，参数无法正确获取，出错应用编号["+JWSBeanUtil.INSTANCE.getLocalbean().getLocalyybh()+"]");
		return JWSUtil.INSTANCE.toJsonString(bean);
	}
	/**
	 * 2.2.目标类型为服务组
	 * @param ywbh:业务编号
	 * @param reqparammap:请求参数MAP对象
	 * @param filecontent:二进制文件内容
	 * @return
	 * 		   业务编号不存在 [1,描述语言]
	 * 		   属于本应用服务组业务，直接处理[0,业务配置bean名称]
	 *       请求地址信息[请求地址,请求用户名,请求密码,目标应用编号]
	 */
	public String proxyFWZwsFile(String ywbh,Map<String,Object> reqparammap,byte[] filecontent){
		List<String> list = JWSBeanUtil.INSTANCE.getfwzreqinfo(ywbh);
		if(null!=list&&list.size()>0){
			int length = list.size();
			if(length==2){
				if(list.get(0).equals("0")){//属于本应用服务组业务，直接处理
					IHniiService hniiservice = (IHniiService)SpringTool.getBean(list.get(1));
					return hniiservice.jwsopt(ywbh,JWSUtil.INSTANCE.toJsonString(reqparammap),filecontent);
				}else if(list.get(0).equals("1")){//业务编号不存在
					BaseDTOBean bean = new BaseDTOBean();
					bean.setReflag(ReType.ERROR);
					bean.setFalseinfo(list.get(1)+",携带附件,执行应用编号["+JWSBeanUtil.INSTANCE.getLocalbean().getLocalyybh()+"]");
					return JWSUtil.INSTANCE.toJsonString(bean);
				}
			}
			if(length==4){
				LocalBean localbean = JWSBeanUtil.INSTANCE.getLocalbean();
				return wsFile(list.get(0),list.get(1),list.get(2),localbean.getLocalyybh(),localbean.getRunid(),localbean.getFwzlx(),TargetType.FWZ,
						list.get(3),ywbh,JWSUtil.INSTANCE.toJsonString(reqparammap),filecontent);
			}
		}
		BaseDTOBean bean = new BaseDTOBean();
		bean.setReflag(ReType.ERROR);
		bean.setFalseinfo("请求服务组,携带附件，业务编号["+ywbh+"]，参数无法正确获取，出错应用编号["+JWSBeanUtil.INSTANCE.getLocalbean().getLocalyybh()+"]");
		return JWSUtil.INSTANCE.toJsonString(bean);
	}
	
	/**
	 * 3.获取wsdl文件本地路径，分开写是方便以后兼容weblogic做准备
	 * @return
	 */
	private synchronized String getFilePath(){
		return JWSUtil.INSTANCE.getFilePath();
    }
}
