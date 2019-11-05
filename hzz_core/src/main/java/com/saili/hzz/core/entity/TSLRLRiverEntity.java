package com.saili.hzz.core.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 河流
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_rl_river")
@PrimaryKeyJoinColumn(name = "id")
public class TSLRLRiverEntity extends TSLBaseRiverLake implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	@Excel(name = "河流类型" ,width = 20)
	private String riverType;// 河流类型  10河流11水渠 	字典riverType
	
	@Excel(name = "河流长度(公里)" ,width = 20)
	private Float riverLength;// 河流长度(公里)
	
	@Excel(name = "河源所在位置" ,width = 20)
	private String startPositionName;// 河源所在位置
	
	@Excel(name = "河口所在位置" ,width = 20)
	private String endPositiomName;// 河口所在位置

	@Excel(name = "河流流经地" ,width = 20)
	private String riverDesc;// 河流流经地

	public String getRiverType() {
		return riverType;
	}

	public void setRiverType(String riverType) {
		this.riverType = riverType;
	}

	public Float getRiverLength() {
		return riverLength;
	}

	public void setRiverLength(Float riverLength) {
		this.riverLength = riverLength;
	}

	public String getStartPositionName() {
		return startPositionName;
	}

	public void setStartPositionName(String startPositionName) {
		this.startPositionName = startPositionName;
	}

	public String getEndPositiomName() {
		return endPositiomName;
	}

	public void setEndPositiomName(String endPositiomName) {
		this.endPositiomName = endPositiomName;
	}

	public String getRiverDesc() {
		return riverDesc;
	}

	public void setRiverDesc(String riverDesc) {
		this.riverDesc = riverDesc;
	}
	
	
	
}
