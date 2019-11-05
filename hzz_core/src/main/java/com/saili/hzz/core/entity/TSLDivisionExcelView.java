package com.saili.hzz.core.entity;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 行政区划导入对象
 * @author  liuby
 */
public class TSLDivisionExcelView implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Excel(name="行政区划编码",width=50)
	private String id;
	@Excel(name="行政区划名称",width=50)
	private String divisionName;
	@Excel(name="行政区划排序",width=50)
	private String divisionOrder;
	@Excel(name="行政区划父编码",width=50)
	private String parentdivisionid;
	@Excel(name="行政区划备注",width=50)
	private String divisionMemo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getDivisionOrder() {
		return divisionOrder;
	}
	public void setDivisionOrder(String divisionOrder) {
		this.divisionOrder = divisionOrder;
	}
	public String getParentdivisionid() {
		return parentdivisionid;
	}
	public void setParentdivisionid(String parentdivisionid) {
		this.parentdivisionid = parentdivisionid;
	}
	public String getDivisionMemo() {
		return divisionMemo;
	}
	public void setDivisionMemo(String divisionMemo) {
		this.divisionMemo = divisionMemo;
	}
	
	
	
	
}
