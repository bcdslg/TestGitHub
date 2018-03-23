package com.hnii.scweb.util;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class DateUtil {
	/**
	 * 
	 * @Title: toDate
	 * @Description: TODO(将String类型的日期转换为java.sql.Date)
	 * @param @param dateStr
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date toDate(String dateStr) throws Exception {
		if (dateStr == null || dateStr.trim().equals("")) {
			return null;
		}
		Date date = null;
		try {
			date = Date.valueOf(dateStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		return date;
	}

	/**
	 * 
	 * @Title: DatetoStr
	 * @Description: TODO(将Date日期转换为String)
	 * @param @param date
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String toString(java.util.Date date) {
		String result = date.toString();

		return result;
	}

	/**
	 * 
	 * @Title: fmtDateToStr
	 * @Description: TODO(格式话日期，输出字符串)
	 * @param @param date
	 * @param @param flag 格式化日期类型：1:年月日;2:时分秒;3:年月日时分秒
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String fmtDateToStr(java.util.Date date, int flag) {
		if (date == null) {
			return "";
		}
		String result = "";
		SimpleDateFormat sdf = null;
		switch (flag) {
		case 1:
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case 2:
			sdf = new SimpleDateFormat("HH:mm:ss");// 获取时分秒
			break;
		case 3:
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		default:
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			break;
		}
		result = sdf.format(date);
		return result;
	}

	/**
	 * 
	 * @Title: addDay
	 * @Description: TODO(计算日期，天数加减)
	 * @param @param initDateL
	 * @param @param days
	 * @param @return 设定文件
	 * @return java.util.Date 返回类型
	 * @throws
	 */
	public static java.util.Date addDay(long initDateL, int days) {
		long resultL = 0;
		resultL = initDateL + 1000 * 60 * 60 * 24 * days;
		java.util.Date result = new java.util.Date(resultL);
		return result;
	}

}
