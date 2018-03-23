package com.hnii.scweb.util;

import java.util.List;

public class StringUtil {
	/**
	 * 
	 * @Title: clearNull
	 * @Description: TODO(清除空字符串，选择替换)
	 * @param @param inputstr
	 * @param @param replacestr
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String clearNull(String inputstr, String... replacestr) {

		String outputstr = inputstr;
		if (inputstr == null || inputstr.isEmpty() || inputstr.equals("")
				|| inputstr.equals("null")) {
			if (replacestr.length > 0 && replacestr[0] != null) {
				outputstr = replacestr[0];
			} else {
				outputstr = "";
			}
		}
		return outputstr;

	}

	/**
	 * 
	 * @Title: subString
	 * @Description: TODO(字符串分割)
	 * @param @param 要分割的字符串
	 * @param @param 分割字符，如逗号
	 * @param @return 设定文件
	 * @return String[] 返回类型
	 * @throws
	 */
	public static String[] splitString(String inputStr, String splitStr) {
		String[] splitStrs = inputStr.split(splitStr);
		return splitStrs;

	}

	public static String unHttpAndPost(String str) {
		String http = "http://";
		if (str.contains(http)) {
			str = str.substring(7, str.length());
		}
		if (str.contains("/_1")) {
			str = str.substring(0, str.indexOf("/_1"));
		}
		if (str.contains(":")) {
			str = str.substring(0, str.length() - 5);
		}
		return str;
	}

	/**
	 * 将数组转换为字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String listToStr(List<String> str) {
		if (str == null) {
			return "";
		}
		return str.toString().replace("[", "").replace("]", "");
	}
	/**
	 * 字符中是否为null或empty
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean isNullOrEmpty(String str) {
		if (str == null || str.isEmpty())
			return true;
		return false;
	}
}
