package com.saili.hzz.core.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 水功能区
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_rl_project_water_function")
@PrimaryKeyJoinColumn(name = "id")
public class TSLRLProjectWaterFunction extends TSLBaseRiverLake implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Excel(name = "一级水功能区名称" ,width = 20)
	private String primaryName;// 一级水功能区名称
	@Excel(name = "二级水功能区名称" ,width = 20)
	private String secondaryName;// 二级水功能区名称
	@Excel(name = "用水类型" ,width = 20)
	private String useWaterType;// 用水类型   字典useWaterType
	@Excel(name = "水功能区长度(千米)" ,width = 20)
	private Float waterFunctionLength;// 水功能区长度(千米)
	@Excel(name = "水功能区水质目标" ,width = 20)
	private String waterQuality;// 水功能区水质目标   字典waterQuality
	@Excel(name = "水功能区起始断面" ,width = 20)
	private String startSection;// 水功能区起始断面
	@Excel(name = "水功能区终止断面" ,width = 20)
	private String endSection;// 水功能区终止断面
	@Excel(name = "监测断面" ,width = 20)
	private String monitoringSection;// 监测断面
	public String getPrimaryName() {
		return primaryName;
	}
	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}
	public String getSecondaryName() {
		return secondaryName;
	}
	public void setSecondaryName(String secondaryName) {
		this.secondaryName = secondaryName;
	}
	public String getUseWaterType() {
		return useWaterType;
	}
	public void setUseWaterType(String useWaterType) {
		this.useWaterType = useWaterType;
	}
	public Float getWaterFunctionLength() {
		return waterFunctionLength;
	}
	public void setWaterFunctionLength(Float waterFunctionLength) {
		this.waterFunctionLength = waterFunctionLength;
	}
	public String getWaterQuality() {
		return waterQuality;
	}
	public void setWaterQuality(String waterQuality) {
		this.waterQuality = waterQuality;
	}
	public String getStartSection() {
		return startSection;
	}
	public void setStartSection(String startSection) {
		this.startSection = startSection;
	}
	public String getEndSection() {
		return endSection;
	}
	public void setEndSection(String endSection) {
		this.endSection = endSection;
	}
	public String getMonitoringSection() {
		return monitoringSection;
	}
	public void setMonitoringSection(String monitoringSection) {
		this.monitoringSection = monitoringSection;
	}
	
	
}
