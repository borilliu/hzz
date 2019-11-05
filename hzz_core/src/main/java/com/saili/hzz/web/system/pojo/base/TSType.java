package com.saili.hzz.web.system.pojo.base;
// default package

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.saili.hzz.core.entity.IStatsType;
import com.saili.hzz.core.common.entity.IdEntity;

/**
 * 通用类型字典表
 *  @author  liuby
 */
@Entity
@Table(name = "t_s_type")
public class TSType extends IdEntity implements IStatsType, java.io.Serializable {

	private TSTypegroup TSTypegroup;//类型分组
	private TSType TSType;//父类型
	private String typename;//类型名称
	private String typecode;//类型编码

	private Date createDate;//创建时间
	private String createName;//创建用户

//	private List<TPProcess> TSProcesses = new ArrayList();
	private List<TSType> TSTypes =new ArrayList();

	private Integer orderNum;//序号

	@Override
	@Transient
	public String getName() {
		return typename;
	}

	@Override
	@Transient
	public String getCode() {
		return typecode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typegroupid")
	public TSTypegroup getTSTypegroup() {
		return this.TSTypegroup;
	}

	public void setTSTypegroup(TSTypegroup TSTypegroup) {
		this.TSTypegroup = TSTypegroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typepid")
	public TSType getTSType() {
		return this.TSType;
	}

	public void setTSType(TSType TSType) {
		this.TSType = TSType;
	}

	@Column(name = "typename", length = 50)
	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	@Column(name = "typecode", length = 50)
	public String getTypecode() {
		return this.typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name="create_name",length=36)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSType")
//	public List<TPProcess> getTSProcesses() {
//		return this.TSProcesses;
//	}
//
//	public void setTSProcesses(List<TPProcess> TSProcesses) {
//		this.TSProcesses = TSProcesses;
//	}
	
	@Column(name="order_num",length=3)
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSType")
	public List<TSType> getTSTypes() {
		return this.TSTypes;
	}

	public void setTSTypes(List<TSType> TSTypes) {
		this.TSTypes = TSTypes;
	}
}