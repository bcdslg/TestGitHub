package com.hnii.scweb.system.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hnii.jws.bean.FWZBean;
import com.hnii.jws.bean.JWSBeanUtil;
import com.hnii.jws.bean.LocalBean;
import com.hnii.jws.bean.YWBean;
import com.hnii.jws.bean.YYBean;
import com.hnii.jws.dto.JWSDTOBaseBean;
import com.hnii.jws.iservice.IHniiService;
import com.hnii.jws.type.ReType;
import com.hnii.jws.util.JWSUtil;

@Service("jkxxservice")
public class UserServiceImpl implements IHniiService{

	@Override
	public String jwsopt(String ywbh, String reqparamjson) {
		System.out.println("=================>>>>jwsopt ywbh = "+ywbh);
		String runid = "yy_run_0001";
		JWSDTOBaseBean dtbean = new JWSDTOBaseBean();
		dtbean.setReflag(ReType.SUCCESS);
		
		LocalBean localbean = JWSBeanUtil.INSTANCE.getLocalbean();	
		if(ywbh.equals(localbean.getQdywbh())){
			dtbean.setRunid(runid);
		}
		
		List<FWZBean> fwzlist = new ArrayList<FWZBean>();
		FWZBean fwzbean1 = new FWZBean();
		fwzbean1.setSystemid("de07ac78e55b49c78dfaba47da5b52deuzkvWrf5hQshItXsac");
		fwzbean1.setFwzlx("03");
		fwzbean1.setJwsvfs("2");
		fwzbean1.setJwsymdz("http://localhost:8080/FWGL/hnii/jaxws");
		fwzlist.add(fwzbean1);
		
		FWZBean fwzbean = new FWZBean();
		fwzbean.setSystemid("6e1392c3f8d245a6a427b79acc7dc140D5oF0pLuBs88fVOY7y");
		fwzbean.setFwzlx("05");
		fwzbean.setJwsvfs("2");
		fwzbean.setJwsymdz("http://localhost:7001/Test/hnii/jaxws");
		fwzlist.add(fwzbean);
		
		List<YWBean> ywlist = new ArrayList<YWBean>();
		YWBean ywbean = new YWBean();
		ywbean.setYwbh(localbean.getQdywbh());
		ywbean.setServicename(localbean.getQdservicename());
		ywbean.setYyfwzsystemid("6e1392c3f8d245a6a427b79acc7dc140D5oF0pLuBs88fVOY7y");
		ywlist.add(ywbean);
		YWBean ywbean1 = new YWBean();
		ywbean1.setYwbh(localbean.getRloadywid());
		ywbean1.setServicename(localbean.getQdservicename());
		ywbean1.setYyfwzsystemid("6e1392c3f8d245a6a427b79acc7dc140D5oF0pLuBs88fVOY7y");
		ywlist.add(ywbean1);
		
		List<YYBean> yylist = new ArrayList<YYBean>();
		YYBean yybean1 = new YYBean();
		yybean1.setSystemid("f2b5b56132384494813ac8b86320cc942NARZn4prJ3cNcKmPH");
		yybean1.setFwzsystemid("de07ac78e55b49c78dfaba47da5b52deuzkvWrf5hQshItXsac");
		yybean1.setYyrunid(runid);
		yylist.add(yybean1);
		
		YYBean yybean = new YYBean();
		yybean.setSystemid(localbean.getLocalyybh());
		yybean.setFwzsystemid("6e1392c3f8d245a6a427b79acc7dc140D5oF0pLuBs88fVOY7y");
		yybean.setYyrunid(runid);
		yylist.add(yybean);
		
		
		dtbean.setFwzlist(fwzlist);
		dtbean.setYwlist(ywlist);
		dtbean.setYylist(yylist);
		return JWSUtil.INSTANCE.toJsonString(dtbean);
	}

	@Override
	public String jwsopt(String ywbh, String reqparamjson, byte[] filebyte){
		return "{\"userid\":\"cat\",\"name\":\"wsFile——名称\"}";
	}
	
}
