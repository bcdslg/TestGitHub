package com.hnii.scweb.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageData implements Serializable {
	private static final long serialVersionUID = 1L;
	private int current = 1;// 当前页
	private int pagesize = 10;// 每页显示条数
	private int total = 0;// 总条数
	private int begin;
	private int end;
	private String sort;
	private String order;
	private String flag;
	private List<?> rows = new ArrayList();
	
	private List<?> spareRows = new ArrayList();
	private int spareCount = 0;// 总条数

	public int getSpareCount() {
		return spareCount;
	}

	public void setSpareCount(int spareCount) {
		this.spareCount = spareCount;
	}

	public List<?> getSpareRows() {
		return spareRows;
	}

	public void setSpareRows(List<?> spareRows) {
		this.spareRows = spareRows;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PageData() {
	}

	public void setPagePara(Integer rows) {
		if (rows != null) {
			this.begin = ((this.current - 1) * rows.intValue());
			this.end = (this.current * rows.intValue());
			this.pagesize = rows.intValue();
		} else {
			this.begin = ((this.current - 1) * this.pagesize);
			this.end = (this.current * this.pagesize);
		}
	}

}
