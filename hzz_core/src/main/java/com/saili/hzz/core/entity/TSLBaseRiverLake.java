package com.saili.hzz.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSONObject;
import com.saili.hzz.core.enums.DivisionLevelEnum;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.saili.hzz.core.common.entity.IdEntity;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 河湖
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_base_river_lake")
@Inheritance(strategy = InheritanceType.JOINED)
public class TSLBaseRiverLake extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 名称 */
	private java.lang.String name;
	/** 编码 */
	private java.lang.String code;
	/** 河长公示牌经纬度 */
	public String publicSignsLngLat;
	
	/** 一河一档  档案 */
	private java.lang.String archivesDoc;
	/** 一河一策  政策 */
	private java.lang.String policyDoc;
	/** 河长公示牌 */
	private java.lang.String publicSignsDoc;
	/** 河流图片*/
	private java.lang.String photo;
	
	@Excel(name = "流域面积(平方公里)" ,width = 20)
	/** 流域面积 水面面积(平方公里)*/
	private Float waterArea;
	/** 上级 */
	private TSLBaseRiverLake parent;
	/**
	 * 类型
	 * 10：河流、11：湖泊、12：河段、13：水库、21：取水口、22：水功能区、23：测站、24：排污口
	 */
	private java.lang.String riverLakeType;

	/**
	 * 河流经纬度
	 */
	@Excel(name = "河流经纬度" ,width = 20)
	private String riverRangeData;

	/**
	 * 河流地图中心经纬度
	 */
	@Excel(name = "河流地图中心经纬度" ,width = 20)
	private String mapCenterLngLat;


	/**
	 * 河流地图缩放级
	 */
	@Excel(name = "河流地图缩放级" ,width = 20)
	private String mapZoom;

	/**
	 * 河湖标识经度
	 */
	@Excel(name = "河湖标识经度" ,width = 20)
	private Double publicSignsLng;
	/**
	 * 河湖标识经度
	 */
	@Excel(name = "河湖标识纬度" ,width = 20)
	private Double publicSignsLat;

	/** 行政区划 */
	private TSLDivisionEntity TSLDivision;

	/** 组织 */
	private java.lang.String sysOrgCode;
	/** 公司 */
	private java.lang.String sysCompanyCode;


	/** 创建人名称 */
	private java.lang.String createName;
	/** 创建人登录名称 */
	private java.lang.String createBy;
	/** 创建日期 */
	private java.util.Date createDate;
	/** 更新人名称 */
	private java.lang.String updateName;
	/** 更新人登录名称 */
	private java.lang.String updateBy;
	/** 更新日期 */
	private java.util.Date updateDate;


	/**
	 * 计算用
	 */
	private String tempName;


	@Transient
	public String getDivisionLevelCode() {
		if (null == TSLDivision || StringUtils.isBlank(TSLDivision.getParentCodes())) {
			return null;
		}
		int parentCout = TSLDivision.getParentCodes().split(",").length;
		return DivisionLevelEnum.getCodeByParentCount(parentCout);
	}

	@Transient
	public String getDivisionCode() {
		if (null == TSLDivision || StringUtils.isBlank(TSLDivision.getCode())) {
			return null;
		}
		return TSLDivision.getCode();
	}

	@Transient
	public String getTempName() {
		return tempName;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	@Column(name = "photo", length = 100)
	public java.lang.String getPhoto() {
		return photo;
	}

	public void setPhoto(java.lang.String photo) {
		this.photo = photo;
	}

	@Column(name = "archives_doc", length = 100)
	public java.lang.String getArchivesDoc() {
		return archivesDoc;
	}

	public void setArchivesDoc(java.lang.String archivesDoc) {
		this.archivesDoc = archivesDoc;
	}

	@Column(name = "policy_doc", length = 100)
	public java.lang.String getPolicyDoc() {
		return policyDoc;
	}

	public void setPolicyDoc(java.lang.String policyDoc) {
		this.policyDoc = policyDoc;
	}

	@Column(name = "public_signs_doc", length = 100)
	public java.lang.String getPublicSignsDoc() {
		return publicSignsDoc;
	}

	public void setPublicSignsDoc(java.lang.String publicSignsDoc) {
		this.publicSignsDoc = publicSignsDoc;
	}

	@Column(name = "public_signs_lnglat", length = 100)
	public String getPublicSignsLngLat() {
		return publicSignsLngLat;
	}

	public void setPublicSignsLngLat(String publicSignsLngLat) {
		this.publicSignsLngLat = publicSignsLngLat;
	}

	@NotFound(action=NotFoundAction.IGNORE)
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "division_divisionCode",referencedColumnName = "code")
	public TSLDivisionEntity getTSLDivision() {
		return this.TSLDivision;
	}

	public void setTSLDivision(TSLDivisionEntity TSLDivision) {
		this.TSLDivision = TSLDivision;
	}

	/**
	 * 方法: 取得TSLBaseRiverLake
	 * 
	 * @return: TSLBaseRiverLake 上级code
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_CODE",referencedColumnName = "code")
	public TSLBaseRiverLake getParent() {
		return this.parent;
	}

	@Column(name = "river_lake_type", nullable = true, length = 10)
	public java.lang.String getRiverLakeType() {
		return riverLakeType;
	}

	public void setRiverLakeType(java.lang.String riverLakeType) {
		this.riverLakeType = riverLakeType;
	}

	/**
	 * 方法: 设置TSLBaseRiverLake
	 * 
	 * @param: TSLBaseRiverLake 上级
	 */
	public void setParent(TSLBaseRiverLake parent) {
		this.parent = parent;
	}
	
	private List<TSLBaseRiverLake> list;
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "parent")
	public List<TSLBaseRiverLake> getList() {
		return list;
	}

	public void setList(List<TSLBaseRiverLake> list) {
		this.list = list;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 创建人名称
	 */
	@Column(name = "CREATE_NAME", nullable = true, length = 50)
	public java.lang.String getCreateName() {
		return this.createName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 创建人名称
	 */
	public void setCreateName(java.lang.String createName) {
		this.createName = createName;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 创建人登录名称
	 */
	@Column(name = "CREATE_BY", nullable = true, length = 50)
	public java.lang.String getCreateBy() {
		return this.createBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
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

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 更新人名称
	 */
	@Column(name = "UPDATE_NAME", nullable = true, length = 50)
	public java.lang.String getUpdateName() {
		return this.updateName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 更新人名称
	 */
	public void setUpdateName(java.lang.String updateName) {
		this.updateName = updateName;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 更新人登录名称
	 */
	@Column(name = "UPDATE_BY", nullable = true, length = 50)
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 更新日期
	 */
	@Column(name = "UPDATE_DATE", nullable = true)
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 类型名称
	 */
	@Column(name = "NAME", nullable = true, length = 32)
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 类型名称
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 类型编码
	 */
	@Column(name = "CODE", nullable = true, length = 32)
	public java.lang.String getCode() {
		return this.code;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 类型编码
	 */
	public void setCode(java.lang.String code) {
		this.code = code;
	}
	
	@Column(name = "SYS_ORG_CODE", nullable = true, length = 15)
	public java.lang.String getSysOrgCode() {
		return sysOrgCode;
	}

	public void setSysOrgCode(java.lang.String sysOrgCode) {
		this.sysOrgCode = sysOrgCode;
	}

	@Column(name = "SYS_COMPANY_CODE", nullable = true, length = 15)
	public java.lang.String getSysCompanyCode() {
		return sysCompanyCode;
	}

	public void setSysCompanyCode(java.lang.String sysCompanyCode) {
		this.sysCompanyCode = sysCompanyCode;
	}
	
	private List<TSLBaseRiverLakeUser> baseRiverLakeUserList = new ArrayList<TSLBaseRiverLakeUser>();

	@JsonIgnore
    @OneToMany(mappedBy = "tslBaseRiverLake")
    public List<TSLBaseRiverLakeUser> getBaseRiverLakeUserList() {
        return baseRiverLakeUserList;
    }

    public void setBaseRiverLakeUserList(List<TSLBaseRiverLakeUser> baseRiverLakeUserList) {
        this.baseRiverLakeUserList = baseRiverLakeUserList;
    }

	public String getRiverRangeData() {
		return riverRangeData;
	}

	public void setRiverRangeData(String riverRangeData) {
		this.riverRangeData = riverRangeData;
	}

	public String getMapCenterLngLat() {
		return mapCenterLngLat;
	}

	public void setMapCenterLngLat(String mapCenterLngLat) {
		this.mapCenterLngLat = mapCenterLngLat;
	}

	public String getMapZoom() {
		return mapZoom;
	}

	public void setMapZoom(String mapZoom) {
		this.mapZoom = mapZoom;
	}
	
	public Float getWaterArea() {
		return waterArea;
	}

	public void setWaterArea(Float waterArea) {
		this.waterArea = waterArea;
	}

	@Column(name = "public_signs_lng", nullable = true, length = 15)
	public Double getPublicSignsLng() {
		if ((null == publicSignsLng || publicSignsLng <= 0) && StringUtils.isNotBlank(publicSignsLngLat)) {
			JSONObject publicSigns = JSONObject.parseObject(publicSignsLngLat);
			return publicSigns.getDouble("lng");
		}
		return publicSignsLng;
	}

	public void setPublicSignsLng(Double publicSignsLng) {
		this.publicSignsLng = publicSignsLng;
	}

	@Column(name = "public_signs_lat", nullable = true, length = 15)
	public Double getPublicSignsLat() {
		if ((null == publicSignsLat || publicSignsLat <= 0) && StringUtils.isNotBlank(publicSignsLngLat)) {
			JSONObject publicSigns = JSONObject.parseObject(publicSignsLngLat);
			return publicSigns.getDouble("lat");
		}
		return publicSignsLat;
	}

	public void setPublicSignsLat(Double publicSignsLat) {
		this.publicSignsLat = publicSignsLat;
	}
}
