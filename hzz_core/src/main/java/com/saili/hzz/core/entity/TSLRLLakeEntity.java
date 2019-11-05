package com.saili.hzz.core.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * 湖泊
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_rl_lake")
@PrimaryKeyJoinColumn(name = "id")
public class TSLRLLakeEntity extends TSLBaseRiverLake implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** 咸淡属性*/
	private String saltyAttr;// 咸淡属性  10河流11水渠 	字典saltyAttr
	/** 平均水深(米)*/
	private Float meanDepth;
	/** 湖泊容积(m³)*/
	private Float lakeVolume;
	/** 最大水深(米)*/
	private Float maxWaterDepth;
	
	public String getSaltyAttr() {
		return saltyAttr;
	}
	public void setSaltyAttr(String saltyAttr) {
		this.saltyAttr = saltyAttr;
	}
	public Float getMeanDepth() {
		return meanDepth;
	}
	public void setMeanDepth(Float meanDepth) {
		this.meanDepth = meanDepth;
	}
	public Float getLakeVolume() {
		return lakeVolume;
	}
	public void setLakeVolume(Float lakeVolume) {
		this.lakeVolume = lakeVolume;
	}
	public Float getMaxWaterDepth() {
		return maxWaterDepth;
	}
	public void setMaxWaterDepth(Float maxWaterDepth) {
		this.maxWaterDepth = maxWaterDepth;
	}
	
	
	
	
}
