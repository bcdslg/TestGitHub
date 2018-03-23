package com.hnii.jws.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.hnii.jws.bean.JWSBeanUtil;
import com.hnii.jws.bean.YWBean;
import com.hnii.jws.client.ClientReqUtil;
import com.hnii.jws.dto.BaseDTOBean;
import com.hnii.jws.iservice.IHniiService;
import com.hnii.jws.type.ReType;
import com.hnii.jws.type.TargetType;
import com.hnii.jws.util.JWSUtil;
import com.hnii.scweb.util.SpringTool;


@WebService(endpointInterface = "com.hnii.jws.service.IHniiJaxWsServer")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class HniiJaxWsServerImpl implements IHniiJaxWsServer{
	@Resource
	private WebServiceContext wsContext;
	
	/**
	 * 1.普通业务请求(服务器端)
	 * @param yybh:发送请求的应用编号
	 * @param runid:发送请求的应用启动编号
	 * @param fwzlx:发送请求的应用所属服务组类型
	 * @param targetType:目标类型[1:应用服务组|2:应用]
	 * @param targetId:目标标识符
	 * @param ywbh:请求的业务编号
	 * @param reqparamjsonstr:请求参数JSON字符串
	 * @return:返回JSON字符串
	 */
	@Override
	public String wsRequst(String yybh, String runid, String fwzlx,String targetType, String targetId, String ywbh,String reqparamjsonstr){
		//来源校验  BEGIN
		boolean yzflag = true;
		String rflaseinfo = "";
		if(ywbh.equals(JWSBeanUtil.INSTANCE.getLocalbean().getQdywbh())){//如果是启动业务，则验证应用编号+启动密码
			Map<String,String> map = JWSUtil.INSTANCE.toObject(reqparamjsonstr, Map.class);
			String qdpwd = map.get("qdpwd");
			String ip = getClientInfo();
			String reflag = JWSBeanUtil.INSTANCE.vcomefrom("qd",yybh,fwzlx,qdpwd,ip);
			if(reflag.equals("2")){//失败,应用编号不存在
				yzflag = false;
				rflaseinfo = "应用编号不存在";
			}else if(reflag.equals("3")){//失败，应用编号与启动密码或运行编号不匹配
				yzflag = false;
				rflaseinfo = "应用编号与启动密码不匹配";
			}else if(reflag.equals("4")){//失败，应用编号对应的服务组类型不匹配
				yzflag = false;
				rflaseinfo = "应用编号对应的服务组类型不匹配";
			}else if(reflag.equals("5")){//失败，应用启动时，应用编号与登录IP不匹配
				yzflag = false;
				rflaseinfo = "应用启动时，应用编号与登录IP不匹配,IP地址["+ip+"]";
			}
		}else{//不是启动业务，则验证应用编号+运行编号
			String reflag = JWSBeanUtil.INSTANCE.vcomefrom("yw",yybh,fwzlx,runid,"");
			if(reflag.equals("2")){//失败,应用编号不存在
				yzflag = false;
				rflaseinfo = "应用编号不存在";
			}else if(reflag.equals("3")){//失败，应用编号与启动密码或运行编号不匹配
				yzflag = false;
				rflaseinfo = "应用编号与应用运行编号不匹配";
			}else if(reflag.equals("4")){//失败，应用编号对应的服务组类型不匹配
				yzflag = false;
				rflaseinfo = "应用编号对应的服务组类型不匹配";
			}
		}
		
		if(!yzflag){
			BaseDTOBean rdtobean = new BaseDTOBean();
			rdtobean.setReflag(ReType.ERROR);
			rdtobean.setFalseinfo(rflaseinfo);
			return JWSUtil.INSTANCE.toJsonString(rdtobean);
		}
		//来源校验  END
		
		//接收到的业务编号，允许自己直接处理，处理名称为同名的springbean对象
		if(JWSBeanUtil.INSTANCE.sfzjclyw(ywbh)){//直接处理的业务编号
			YWBean ywbean = JWSBeanUtil.INSTANCE.getywbean(ywbh);
			IHniiService hniiservice = (IHniiService)SpringTool.getBean(ywbean.getServicename());
			return hniiservice.jwsopt(ywbh,reqparamjsonstr);
		}
		
		//处理业务 BEGIN
		Map<String,Object> reqparammap = JWSUtil.INSTANCE.toObject(reqparamjsonstr, Map.class);
		if(targetType.equals(TargetType.FWZ)){//目标为服务组
			return ClientReqUtil.INSTANCE.proxyFWZwsRequst(ywbh, reqparammap);
		}else if(targetType.equals(TargetType.YY)){//目标为应用
			return ClientReqUtil.INSTANCE.proxyYYwsRequst(ywbh, targetId, reqparammap);
		}
		//处理业务 END
		
		BaseDTOBean rdtobean = new BaseDTOBean();
		rdtobean.setReflag(ReType.ERROR);
		rdtobean.setFalseinfo("缺失目标请求类型部分请求");
		return JWSUtil.INSTANCE.toJsonString(rdtobean);
	}
	/**
	 * 2.附带二进制文件的业务请求(服务器端)
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
	@Override
	public String wsFile(String yybh, String runid, String fwzlx,String targetType, String targetId, String ywbh,String reqparamjsonstr, byte[] filecontent) {
		//来源校验  BEGIN
		boolean yzflag = true;
		String rflaseinfo = "";
		if(ywbh.equals(JWSBeanUtil.INSTANCE.getLocalbean().getQdywbh())){//如果是启动业务，则验证应用编号+启动密码
			Map<String,String> map = JWSUtil.INSTANCE.toObject(reqparamjsonstr, Map.class);
			String qdpwd = map.get("qdpwd");
			String ip = getClientInfo();
			String reflag = JWSBeanUtil.INSTANCE.vcomefrom("qd",yybh,fwzlx,qdpwd,ip);
			if(reflag.equals("2")){//失败,应用编号不存在
				yzflag = false;
				rflaseinfo = "应用编号不存在";
			}else if(reflag.equals("3")){//失败，应用编号与启动密码或运行编号不匹配
				yzflag = false;
				rflaseinfo = "应用编号与启动密码不匹配";
			}else if(reflag.equals("4")){//失败，应用编号对应的服务组类型不匹配
				yzflag = false;
				rflaseinfo = "应用编号对应的服务组类型不匹配";
			}else if(reflag.equals("5")){//失败，应用启动时，应用编号与登录IP不匹配
				yzflag = false;
				rflaseinfo = "应用启动时，应用编号与登录IP不匹配,IP地址["+ip+"]";
			}
		}else{//不是启动业务，则验证应用编号+运行编号
			String reflag = JWSBeanUtil.INSTANCE.vcomefrom("yw",yybh,fwzlx,runid,"");
			if(reflag.equals("2")){//失败,应用编号不存在
				yzflag = false;
				rflaseinfo = "应用编号不存在";
			}else if(reflag.equals("3")){//失败，应用编号与启动密码或运行编号不匹配
				yzflag = false;
				rflaseinfo = "应用编号与应用运行编号不匹配";
			}else if(reflag.equals("4")){//失败，应用编号对应的服务组类型不匹配
				yzflag = false;
				rflaseinfo = "应用编号对应的服务组类型不匹配";
			}
		}
		
		if(!yzflag){
			BaseDTOBean rdtobean = new BaseDTOBean();
			rdtobean.setReflag(ReType.ERROR);
			rdtobean.setFalseinfo(rflaseinfo);
			return JWSUtil.INSTANCE.toJsonString(rdtobean);
		}
		//来源校验  END
		
		//处理业务 BEGIN
		Map<String,Object> reqparammap = JWSUtil.INSTANCE.toObject(reqparamjsonstr, Map.class);
		if(targetType.equals(TargetType.FWZ)){//目标为服务组
			return ClientReqUtil.INSTANCE.proxyFWZwsFile(ywbh, reqparammap, filecontent);
		}else if(targetType.equals(TargetType.YY)){//目标为应用
			return ClientReqUtil.INSTANCE.proxyYYwsFile(ywbh, targetId, reqparammap, filecontent);
		}
		//处理业务 END
		
		BaseDTOBean rdtobean = new BaseDTOBean();
		rdtobean.setReflag(ReType.ERROR);
		rdtobean.setFalseinfo("缺失目标请求类型部分请求");
		return JWSUtil.INSTANCE.toJsonString(rdtobean);
	}
	
	
	
	
	/**
	 * 1.获取客户端信息
	 * @return
	 */
	private final String getClientInfo() {
		String clientIP = null;
		try {
			MessageContext mc = wsContext.getMessageContext();
			HttpServletRequest request = (HttpServletRequest) (mc.get(MessageContext.SERVLET_REQUEST));
			clientIP = getIpAddress(request);
			//System.out.println("client IP : " + clientIP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientIP;
	}
	/**
	 * 2.获取真实IP
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private final static String getIpAddress(HttpServletRequest request) throws IOException {  
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址  
		String ip = request.getHeader("X-Forwarded-For");  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("Proxy-Client-IP");  
			}  
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("WL-Proxy-Client-IP");   
			}  
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("HTTP_CLIENT_IP");  
			}  
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");   
			}  
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getRemoteAddr();  
			}  
		} else if (ip.length() > 15) {  
			String[] ips = ip.split(",");  
			for (int index = 0; index < ips.length; index++) {  
				String strIp = (String) ips[index];  
				if (!("unknown".equalsIgnoreCase(strIp))) {  
					ip = strIp;  
					break;  
				}  
			}  
		}  
		return ip;  
	}
	
}
