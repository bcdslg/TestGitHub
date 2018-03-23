package com.hnii.jws.iservice;
/**
 * 接口统一定义类
 * @author dengwei
 *
 */
public interface IHniiService{
	/**
	 * 1.接口统一调用方法
	 * @param ywbh:请求的业务编号
	 * @param reqparamjson:请求参数
	 * @return:
	 * 		返回JSON字符串
	 */
	public String jwsopt(String ywbh,String reqparamjson);
	/**
	 * 2.接口统一调用方法，携带二进制文件
	 * @param ywbh:请求的业务编号
	 * @param reqparamjson:请求参数
	 * @param filebyte:二进制文件
	 * @return:
	 * 		返回JSON字符串
	 */
	public String jwsopt(String ywbh,String reqparamjson,byte[] filebyte);
}
