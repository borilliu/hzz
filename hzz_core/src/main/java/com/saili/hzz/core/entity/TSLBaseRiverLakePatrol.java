package com.saili.hzz.core.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import com.saili.hzz.core.common.entity.IdEntity;

/**
 * 巡河记录
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_base_river_lake_patrol")
public class TSLBaseRiverLakePatrol extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private TSLBaseRiverLake tslBaseRiverLake;
	/** 河道 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_river_lake_id")
	public TSLBaseRiverLake getTslBaseRiverLake() {
		return tslBaseRiverLake;
	}
	public void setTslBaseRiverLake(TSLBaseRiverLake tslBaseRiverLake) {
		this.tslBaseRiverLake = tslBaseRiverLake;
	}
	
	/** 巡河人 */
	private RiverLakeChiefEntity riverLakeChief;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	public RiverLakeChiefEntity getRiverLakeChief() {
		return riverLakeChief;
	}
	public void setRiverLakeChief(RiverLakeChiefEntity riverLakeChief) {
		this.riverLakeChief = riverLakeChief;
	}
	
	/**巡河开始日期*/
	private java.util.Date patrolStartDate;
	/**巡河结束日期*/
	private java.util.Date patrolEndDate;
	/**巡河时长(分钟)*/
	private BigDecimal patrolDuration;
	/**巡河里程(公里)*/
	private BigDecimal patrolMileage;
	/**巡河手机型号*/
	private String patrolMobileModel;
	/**巡河App版本号*/
	private String patrolAppVer;
	/**
	 * 行政区划
	 */
	private TSLDivisionEntity division;
	/**
	 * 巡查河流属性
	 */
	private String category;

	@Column(name ="patrol_start_date",nullable=true)
	public java.util.Date getPatrolStartDate() {
		return patrolStartDate;
	}
	public void setPatrolStartDate(java.util.Date patrolStartDate) {
		this.patrolStartDate = patrolStartDate;
	}
	@Column(name ="patrol_end_date",nullable=true)
	public java.util.Date getPatrolEndDate() {
		return patrolEndDate;
	}
	public void setPatrolEndDate(java.util.Date patrolEndDate) {
		this.patrolEndDate = patrolEndDate;
	}
	@Column(name ="patrol_duration",nullable=true)
	public BigDecimal getPatrolDuration() {
		return patrolDuration;
	}
	public void setPatrolDuration(BigDecimal patrolDuration) {
		this.patrolDuration = patrolDuration;
	}
	@Column(name ="patrol_mileage",nullable=true)
	public BigDecimal getPatrolMileage() {
		return patrolMileage;
	}
	public void setPatrolMileage(BigDecimal patrolMileage) {
		this.patrolMileage = patrolMileage;
	}
	
	@Column(name ="patrol_mobile_model",nullable=true)
	public String getPatrolMobileModel() {
		return patrolMobileModel;
	}
	public void setPatrolMobileModel(String patrolMobileModel) {
		this.patrolMobileModel = patrolMobileModel;
	}
	@Column(name ="patrol_app_ver",nullable=true)
	public String getPatrolAppVer() {
		return patrolAppVer;
	}
	public void setPatrolAppVer(String patrolAppVer) {
		this.patrolAppVer = patrolAppVer;
	}
	/**
	 * gps记录
	 */
	private List<TSLBaseRiverLakePatrolGPS> listGPS= new ArrayList<TSLBaseRiverLakePatrolGPS>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "tslBaseRiverLakePatrol")
	public List<TSLBaseRiverLakePatrolGPS> getListGPS() {
		return listGPS;
	}

	public void setListGPS(List<TSLBaseRiverLakePatrolGPS> listGPS) {
		this.listGPS = listGPS;
	}
	
	/**
	 * 照片记录
	 */
	private List<TSLBaseRiverLakePatrolPhoto> listPhote= new ArrayList<TSLBaseRiverLakePatrolPhoto>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "tslBaseRiverLakePatrol")
	public List<TSLBaseRiverLakePatrolPhoto> getListGPSPhote() {
		return listPhote;
	}

	public void setListGPSPhote(List<TSLBaseRiverLakePatrolPhoto> listGPSPhote) {
		this.listPhote = listGPSPhote;
	}
	
	/**
	 * 事件记录
	 */
	private List<TSLBaseRiverLakePatrolEvent> listEvent= new ArrayList<TSLBaseRiverLakePatrolEvent>();
	@JsonIgnore
	@OneToMany(mappedBy = "tslBaseRiverLakePatrol")
	public List<TSLBaseRiverLakePatrolEvent> getListGPSEvent() {
		return listEvent;
	}
	public void setListGPSEvent(List<TSLBaseRiverLakePatrolEvent> listGPSEvent) {
		this.listEvent = listGPSEvent;
	}

	@ManyToOne
	@JoinColumn(name = "division_code", referencedColumnName = "code")
	public TSLDivisionEntity getDivision() {
		return division;
	}

	public void setDivision(TSLDivisionEntity division) {
		this.division = division;
	}

	@Column(name = "category")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
