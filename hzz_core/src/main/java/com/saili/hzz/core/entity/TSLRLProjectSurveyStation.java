package com.saili.hzz.core.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 测站
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_rl_project_survey_station")
@PrimaryKeyJoinColumn(name = "id")
public class TSLRLProjectSurveyStation extends TSLBaseRiverLake implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	@Excel(name = "测站所在地" ,width = 20)
	private String location;// 测站所在地
	@Excel(name = "测站年月" ,width = 20)
	private String stationYear;// 测站年月
	@Excel(name = "测站类别" ,width = 20)
	private String stationType;// 测站类别   字典stationType
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStationYear() {
		return stationYear;
	}
	public void setStationYear(String stationYear) {
		this.stationYear = stationYear;
	}
	public String getStationType() {
		return stationType;
	}
	public void setStationType(String stationType) {
		this.stationType = stationType;
	}
}
