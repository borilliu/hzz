package com.saili.hzz.core.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 排污口
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_rl_project_sewage_outlet")
@PrimaryKeyJoinColumn(name = "id")
public class TSLRLProjectSewageOutlet extends TSLBaseRiverLake implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	@Excel(name = "排水口位置" ,width = 20)
	private String location;// 排水口位置
	@Excel(name = "排污口类型" ,width = 20)
	private String sewageOutType;// 排污口类型 字典sewageOutType
	@Excel(name = "入河方式" ,width = 20)
	private String riverEntryMode;// 入河方式 字典riverEntryMode
	@Excel(name = "排放方式" ,width = 20)
	private String emissionMode;// 排放方式 字典emissionMode
	@Excel(name = "排入一级水功能区域" ,width = 20)
	private String intoPrimary;// 排入一级水功能区域
	@Excel(name = "排入二级水功能区域" ,width = 20)
	private String intoSecondary;// 排入二级水功能区域
	@Excel(name = "污水主要来源" ,width = 20)
	private String sourcesSewageType;// 污水主要来源 字典sourcesSewageType
	@Excel(name = "排污量(万吨)" ,width = 20)
	private Float pollutantDischarge;// 排污量(万吨)
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSewageOutType() {
		return sewageOutType;
	}
	public void setSewageOutType(String sewageOutType) {
		this.sewageOutType = sewageOutType;
	}
	public String getRiverEntryMode() {
		return riverEntryMode;
	}
	public void setRiverEntryMode(String riverEntryMode) {
		this.riverEntryMode = riverEntryMode;
	}
	public String getEmissionMode() {
		return emissionMode;
	}
	public void setEmissionMode(String emissionMode) {
		this.emissionMode = emissionMode;
	}
	public String getIntoPrimary() {
		return intoPrimary;
	}
	public void setIntoPrimary(String intoPrimary) {
		this.intoPrimary = intoPrimary;
	}
	public String getIntoSecondary() {
		return intoSecondary;
	}
	public void setIntoSecondary(String intoSecondary) {
		this.intoSecondary = intoSecondary;
	}
	public String getSourcesSewageType() {
		return sourcesSewageType;
	}
	public void setSourcesSewageType(String sourcesSewageType) {
		this.sourcesSewageType = sourcesSewageType;
	}
	public Float getPollutantDischarge() {
		return pollutantDischarge;
	}
	public void setPollutantDischarge(Float pollutantDischarge) {
		this.pollutantDischarge = pollutantDischarge;
	}
	
}
