package com.hnii.jws.servlet;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.hnii.jws.bean.JWSBeanUtil;
import com.hnii.jws.bean.LocalBean;

public class HniiServlet extends HttpServlet {
	@Override
	public void init() throws ServletException {
		String qdflag = JWSBeanUtil.INSTANCE.qd();
		boolean qdboolean = true;
		if(qdflag.equals("1")){
			System.out.println("==============>>>>>>>> public属性配置文件，属性不完整");
			qdboolean = false;
		}else if(qdflag.equals("2")){
			System.out.println("==============>>>>>>>> 请求接口资源失败");
			qdboolean = false;
		}else if(qdflag.equals("3")){
			System.out.println("==============>>>>>>>> 解析JSON字符串出错或返回值为空");
			qdboolean = false;
		}else if(qdflag.equals("4")){
			System.out.println("==============>>>>>>>> 返回集合结果不全");
			qdboolean = false;
		}
		if(qdboolean){
			LocalBean localbean = JWSBeanUtil.INSTANCE.getLocalbean();
			//定时器
			if(localbean.getReloadpropmin()>0){
				long timerun = localbean.getReloadpropmin()*60*1000;
				Timer timer = new Timer();   
		        //0表示立即执行一次，以后每隔一段时间执行一次  
		        timer.schedule(new TimerTask(){
					@Override
					public void run(){
						System.out.println("======================>>>>> 更新属性配置文件 BEGIN ");
						String flag = JWSBeanUtil.INSTANCE.loadsystemprop();
						System.out.println("======================>>>>> 更新属性配置文件 END ");
					}
		        },timerun,timerun);  
			}
			System.out.println("==========>>>>>> 接口数据读取完毕，开启本地接口服务成功 ================");
		}else{
			try {
				System.out.println("==========>>>>>> 10秒之后，关闭系统");
				Thread.sleep(1000*10);
				System.exit(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
