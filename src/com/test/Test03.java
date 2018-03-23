package com.test;

import java.util.ArrayList;
import java.util.List;

public class Test03 {
	public static List<String> list = new ArrayList<String>();
	public static void main(String[] args){
		list.add("s1");
		list.add("s2");
		list.add("s3");
		new Thread(new Runnable(){
			@Override
			public void run() {
				for(int i = 0;i < list.size();i++){//与 for(String str:list){  输出是不一致的
					System.out.println("==========>>>>>> "+list.get(i));
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<String> rlist = new ArrayList<String>();
				rlist.add("t1");
				rlist.add("t2");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				list = rlist;
				System.out.println("======替换成功=====");
			}
		}).start();
	}
}
