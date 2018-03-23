package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hnii.jws.client.ClientReqUtil;
import com.hnii.jws.dto.BaseDTOBean;
import com.hnii.jws.type.ReType;
import com.hnii.jws.util.JWSUtil;
import com.hnii.scweb.util.SystemUtil;


public class Test01 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String rwsdlurl = "http://localhost:7001/FWGL/hnii/jaxws?wsdl";
//		String ruserid = "";
//		String rpwd = "";
//		String yybh = "";
//		String runid = "";
//		String fwzlx = "";
//		String targetType = "";
//		String targetId = "";
//		String ywbh = "";
//		String reqparamjsonstr = "";
//		byte[] filecontent = "abc".getBytes();
//		//String rjson = ClientReqUtil.INSTANCE.wsFile(rwsdlurl, ruserid, rpwd, yybh, runid, fwzlx, targetType, targetId, ywbh, reqparamjsonstr, filecontent);
//		String rjson = ClientReqUtil.INSTANCE.wsRequst(rwsdlurl, ruserid, rpwd, yybh, runid, fwzlx, targetType, targetId, ywbh, reqparamjsonstr);
//		System.out.println("rjson = "+rjson);
		
		//System.out.println(SystemUtil.INSTANCE.encryptStr("123456"));
		
//		System.out.println(null==JWSUtil.INSTANCE.getPropertiy("public", "hhywbh1"));
		
		boolean flag = JWSUtil.INSTANCE.checkEncryptStr("031759d8e1f744ca994dc7e790e1be79v2346XeBqjUgHO2MI6", "$2a$10$hw.EyydMtoizGrgCn.TXZerVig8MtuBdNtYX/9G92V6gVeD.SFFjK");
		System.out.println(flag);
	}

}

