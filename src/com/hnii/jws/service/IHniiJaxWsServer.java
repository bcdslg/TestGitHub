package com.hnii.jws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IHniiJaxWsServer {
	/**
	 * 1.普通业务请求
	 * @param yybh:发送请求的应用编号
	 * @param runid:发送请求的应用启动编号
	 * @param fwzlx:发送请求的应用所属服务组类型
	 * @param targetType:目标类型[1:应用服务组|2:应用]
	 * @param targetId:目标标识符
	 * @param ywbh:请求的业务编号
	 * @param reqparamjsonstr:请求参数JSON字符串
	 * @return:返回JSON字符串
	 */
	@WebMethod
	public String wsRequst(@WebParam(name = "yybh") String yybh,
						   @WebParam(name = "runid") String runid,
						   @WebParam(name = "fwzlx") String fwzlx,
						   @WebParam(name = "targetType") String targetType,
						   @WebParam(name = "targetId") String targetId,
						   @WebParam(name = "ywbh") String ywbh,
						   @WebParam(name = "reqparamjsonstr") String reqparamjsonstr
			);
	/**
	 * 2.附带二进制文件的业务请求
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
	@WebMethod
	public String wsFile(@WebParam(name = "yybh") String yybh,
					     @WebParam(name = "runid") String runid,
					     @WebParam(name = "fwzlx") String fwzlx,
					     @WebParam(name = "targetType") String targetType,
					     @WebParam(name = "targetId") String targetId,
					     @WebParam(name = "ywbh") String ywbh,
					     @WebParam(name = "reqparamjsonstr") String reqparamjsonstr,
						 @WebParam(name = "filecontent") byte[] filecontent
			);
}
