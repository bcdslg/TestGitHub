package com.hnii.scweb.find.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 分页对象
 * @author dengwei
 *
 */
public class PageBean implements Serializable{
  private static final long serialVersionUID = 1L;
  private int page = 1;
  private int begin;
  private int end;
  private int total = 0;
  private int rownum = 20;
  private List<?> rows = new ArrayList();
  private String flag;

  public PageBean(){
  }


  public String getFlag() {
    return this.flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public int getTotal() {
    return this.total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public void setRows(List<?> rows) {
    this.rows = rows;
  }

  public int getPage() {
    return this.page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getBegin() {
    return this.begin;
  }

  public void setBegin(int begin) {
    this.begin = begin;
  }

  public int getEnd() {
    return this.end;
  }

  public List<?> getRows() {
    return this.rows;
  }

  public void setPagePara(Integer rows) {
    if (rows != null) {
      this.begin = ((this.page - 1) * rows.intValue());
      this.end = (this.page * rows.intValue());
      this.rownum = rows.intValue();
    } else {
      this.begin = ((this.page - 1) * this.rownum);
      this.end = (this.page * this.rownum);
    }
  }

  public int getRownum() {
    return this.rownum;
  }

  public void setRownum(int rownum) {
    this.rownum = rownum;
  }
}
