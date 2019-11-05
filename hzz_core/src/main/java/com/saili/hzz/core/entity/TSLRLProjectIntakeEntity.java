package com.saili.hzz.core.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 取水口
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_rl_project_intake")
@PrimaryKeyJoinColumn(name = "id")
public class TSLRLProjectIntakeEntity extends TSLBaseRiverLake implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	@Excel(name = "取水口位置" ,width = 20)
	private String address;// 取水口位置
	
	@Excel(name = "取水方式" ,width = 20)
	private String takeType;// 取水方式  10自流11抽提  字典takeType
			
	@Excel(name = "水闸名称" ,width = 20)
	private String gateName;// 水闸名称
	
	@Excel(name = "引调水工程名称" ,width = 20)
	private String diversionProjectName;// 引调水工程名称
	
	@Excel(name = "地表水水源地名称" ,width = 20)
	private String waterSourceSName;// 地表水水源地名称

	@Excel(name = "水源类型" ,width = 20)
	private String waterSourceType;// 水源类型 计算用 不保存
	
	@Excel(name = "水源名称" ,width = 20)
	private String waterSourceName;// 水源地名称 计算用 不保存

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTakeType() {
		return takeType;
	}

	public void setTakeType(String takeType) {
		this.takeType = takeType;
	}

	public String getGateName() {
		return gateName;
	}

	public void setGateName(String gateName) {
		this.gateName = gateName;
	}

	public String getDiversionProjectName() {
		return diversionProjectName;
	}

	public void setDiversionProjectName(String diversionProjectName) {
		this.diversionProjectName = diversionProjectName;
	}

	public String getWaterSourceSName() {
		return waterSourceSName;
	}

	public void setWaterSourceSName(String waterSourceSName) {
		this.waterSourceSName = waterSourceSName;
	}

	@Transient
	public String getWaterSourceType() {
		return waterSourceType;
	}

	public void setWaterSourceType(String waterSourceType) {
		this.waterSourceType = waterSourceType;
	}
	
	@Transient
	public String getWaterSourceName() {
		return waterSourceName;
	}

	public void setWaterSourceName(String waterSourceName) {
		this.waterSourceName = waterSourceName;
	}
	
	
	
}
