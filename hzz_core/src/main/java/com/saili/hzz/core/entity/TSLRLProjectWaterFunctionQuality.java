package com.saili.hzz.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.saili.hzz.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 水质报表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_rl_project_water_function_quality")
public class TSLRLProjectWaterFunctionQuality extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** 水功能区*/
	private TSLRLProjectWaterFunction tslRLProjectWaterFunction;
	
	@Excel(name = "本月水质类别" ,width = 32)
	private String waterQuality;// 水功能区水质目标   字典waterQuality
	
	@Excel(name = "本月达标评价" ,width = 32)
	private String waterEvaluate;// 水功能区水质目标   字典waterEvaluate
	
	@Excel(name = "本月主要超标项目及超标倍数" ,width = 100)
	private String projectMultiples;
	
	@Excel(name = "年月" ,width = 20)
	private String ym;
	
	/** 创建日期 */
	private java.util.Date createDate;

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 创建日期
	 */
	@Column(name = "CREATE_DATE", nullable = true)
	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 创建日期
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_water_function_id")
	public TSLRLProjectWaterFunction getTslRLProjectWaterFunction() {
		return tslRLProjectWaterFunction;
	}

	public void setTslRLProjectWaterFunction(TSLRLProjectWaterFunction tslRLProjectWaterFunction) {
		this.tslRLProjectWaterFunction = tslRLProjectWaterFunction;
	}

	@Column(name ="water_quality",nullable=true)
	public String getWaterQuality() {
		return waterQuality;
	}

	public void setWaterQuality(String waterQuality) {
		this.waterQuality = waterQuality;
	}

	@Column(name ="water_evaluate",nullable=true)
	public String getWaterEvaluate() {
		return waterEvaluate;
	}

	public void setWaterEvaluate(String waterEvaluate) {
		this.waterEvaluate = waterEvaluate;
	}

	@Column(name ="project_multiples",nullable=true)
	public String getProjectMultiples() {
		return projectMultiples;
	}

	public void setProjectMultiples(String projectMultiples) {
		this.projectMultiples = projectMultiples;
	}

	@Column(name ="ym",nullable=true)
	public String getYm() {
		return ym;
	}

	public void setYm(String ym) {
		this.ym = ym;
	}
	

}
