package com.saili.hzz.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.saili.hzz.core.enums.DivisionLevelEnum;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import com.saili.hzz.core.common.controller.CustomJsonDateDeserializer;
import com.saili.hzz.core.common.entity.IdEntity;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 行政区划
 * @author  liuby
 */
@Entity
@Table(name = "t_sl_division")
public class TSLDivisionEntity extends IdEntity implements IStatsType, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/***
	 * 区划名称
	 */
	@Excel(name = "区划名称" ,width = 20)
	private String name;
	/***
	 * 英文区划名称
	 */
	@Excel(name = "英文区划名称" ,width = 20)
	private String enName;
	/***
	 * 区划编码
	 */
	@Excel(name = "区划编码",width = 20)
	private String code;
	/***
	 * 区划排序
	 */
	@Excel(name = "区划排序",width = 20)
	private String sort;
	/***
	 * 区划备注
	 */
	@Excel(name = "区划备注",width = 20)
	private String remarks;
	/***
	 * 上级区划编码
	 */
	private String parentCodes;
	/**
	 * 上级行政区划
	 */
	private TSLDivisionEntity parent;
	/**
	 * 下属行政区划
	 */
	private List<TSLDivisionEntity> childrenList = new ArrayList<TSLDivisionEntity>();

	/**
	 * 所属部门
	 */
	private java.lang.String sysOrgCode;
	/**
	 * 所属公司
	 */
	private java.lang.String sysCompanyCode;

	/**
	 * 创建人名称
	 */
	private java.lang.String createName;
	/**
	 * 创建人登录名称
	 */
	private java.lang.String createBy;
	/**
	 * 创建日期
	 */
	private java.util.Date createDate;
	/**
	 * 更新人名称
	 */
	private java.lang.String updateName;
	/**
	 * 更新人登录名称
	 */
	private java.lang.String updateBy;
	/**
	 * 更新日期
	 */
	private java.util.Date updateDate;

	public TSLDivisionEntity() {
	}

	public TSLDivisionEntity(String code) {
		this.code = code;
	}

	@Transient
	public String getDivisionLevelName() {
		if (StringUtils.isBlank(parentCodes)) {
			return null;
		}
		int parentCout = parentCodes.split(",").length;
		return DivisionLevelEnum.getNameByParentCount(parentCout);
	}

	@Transient
	public String getDivisionLevelCode() {
		if (StringUtils.isBlank(parentCodes)) {
			return null;
		}
		int parentCout = parentCodes.split(",").length;
		return DivisionLevelEnum.getCodeByParentCount(parentCout);
	}

	@Transient
	public String getParentCode() {
		if (null == this.parent || StringUtils.isEmpty(this.parent.getCode())) {
			return null;
		}

		return this.parent.getCode();
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "parentdivisionid")
	@JoinColumn(name = "parent_code",referencedColumnName = "code")
	public TSLDivisionEntity getParent() {
		return this.parent;
	}

	public void setParent(TSLDivisionEntity parent) {
		this.parent = parent;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
	public List<TSLDivisionEntity> getChildrenList() {
		return this.childrenList;
	}

	public void setChildrenList(List<TSLDivisionEntity> childrenList) {
		this.childrenList = childrenList;
	}

	@Override
	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "en_name", nullable = false, length = 100)
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Override
	@Column(name = "code", length = 64)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="sort")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	@Column(name = "remarks", length = 64)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Transient
	public String getParentCodes() {
		return parentCodes;
	}

	@Transient
	public void setParentCodes(String parentCodes) {
		this.parentCodes = parentCodes;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属部门
	 */
	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public java.lang.String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属部门
	 */
	public void setSysOrgCode(java.lang.String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属公司
	 */
	@Column(name ="SYS_COMPANY_CODE",nullable=true,length=50)
	public java.lang.String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属公司
	 */
	public void setSysCompanyCode(java.lang.String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
}
