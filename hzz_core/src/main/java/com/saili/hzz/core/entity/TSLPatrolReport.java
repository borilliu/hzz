package com.saili.hzz.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.saili.hzz.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 巡河报表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_patrol_report")
public class TSLPatrolReport extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 行政区划 */
	private TSLDivisionEntity tslDivision;//行政区划
	
	@Excel(name = "年月" ,width = 20)
	private String ym;
	
	/** 创建日期 */
	private java.util.Date createDate;
	
	/** 应巡人数 */
	private Integer shouldNumber;

	/** 已巡人数 */
	private Integer visitedNumber;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "division_divisionCode",referencedColumnName = "code")
	public TSLDivisionEntity getTslDivision() {
		return tslDivision;
	}
	public void setTslDivision(TSLDivisionEntity tslDivision) {
		this.tslDivision = tslDivision;
	}
	
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
	
	@Column(name ="ym",nullable=true)
	public String getYm() {
		return ym;
	}

	public void setYm(String ym) {
		this.ym = ym;
	}
	
	@Column(name ="should_number",nullable=true)
	public Integer getShouldNumber() {
		return shouldNumber;
	}
	public void setShouldNumber(Integer shouldNumber) {
		this.shouldNumber = shouldNumber;
	}
	
	@Column(name ="visited_number",nullable=true)
	public Integer getVisitedNumber() {
		return visitedNumber;
	}
	public void setVisitedNumber(Integer visitedNumber) {
		this.visitedNumber = visitedNumber;
	}
	
	
	

}
