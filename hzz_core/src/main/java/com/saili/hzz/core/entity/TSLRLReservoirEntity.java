package com.saili.hzz.core.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 水库
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_rl_reservoir")
@PrimaryKeyJoinColumn(name = "id")
public class TSLRLReservoirEntity extends TSLBaseRiverLake implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** 水库类型*/
	private String reservoirType;// 水库类型 	字典reservoirType
	/** 工程等别*/
	private String engineeringGrade;  //工程等别 字典engineeringGrade
	/** 工程规模*/
	private String engineeringScale; //工程规模 字典engineeringScale
	/** 水库等级*/
	private String reservoirGrade;//水库等级
	/**防洪高水位(m) */
	private Float highFloodLevel;//防洪高水位
	/**正常蓄水位 (m)*/
	private Float normalWaterLevel;//正常蓄水位
	/** 主汛期防洪限制水位(m)*/
	private Float floodPeriodLevel;//主汛期防洪限制水位
	/** 总库容(m³)*/
	private Float totalCapacity;//总库容
	/** 调洪库容(m³)*/
	private Float floodCapacity;//调洪库容
	/** 防洪库容(m³)*/
	private Float floodStorage;//防洪库容
	
	public String getReservoirType() {
		return reservoirType;
	}
	public void setReservoirType(String reservoirType) {
		this.reservoirType = reservoirType;
	}
	public String getEngineeringGrade() {
		return engineeringGrade;
	}
	public void setEngineeringGrade(String engineeringGrade) {
		this.engineeringGrade = engineeringGrade;
	}
	public String getEngineeringScale() {
		return engineeringScale;
	}
	public void setEngineeringScale(String engineeringScale) {
		this.engineeringScale = engineeringScale;
	}
	public Float getHighFloodLevel() {
		return highFloodLevel;
	}
	public void setHighFloodLevel(Float highFloodLevel) {
		this.highFloodLevel = highFloodLevel;
	}
	public String getReservoirGrade() {
		return reservoirGrade;
	}
	public void setReservoirGrade(String reservoirGrade) {
		this.reservoirGrade = reservoirGrade;
	}
	public Float getNormalWaterLevel() {
		return normalWaterLevel;
	}
	public void setNormalWaterLevel(Float normalWaterLevel) {
		this.normalWaterLevel = normalWaterLevel;
	}
	public Float getFloodPeriodLevel() {
		return floodPeriodLevel;
	}
	public void setFloodPeriodLevel(Float floodPeriodLevel) {
		this.floodPeriodLevel = floodPeriodLevel;
	}
	public Float getTotalCapacity() {
		return totalCapacity;
	}
	public void setTotalCapacity(Float totalCapacity) {
		this.totalCapacity = totalCapacity;
	}
	public Float getFloodCapacity() {
		return floodCapacity;
	}
	public void setFloodCapacity(Float floodCapacity) {
		this.floodCapacity = floodCapacity;
	}
	public Float getFloodStorage() {
		return floodStorage;
	}
	public void setFloodStorage(Float floodStorage) {
		this.floodStorage = floodStorage;
	}
	
}
