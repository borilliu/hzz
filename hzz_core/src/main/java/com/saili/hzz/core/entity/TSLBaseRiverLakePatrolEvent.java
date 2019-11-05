package com.saili.hzz.core.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.saili.hzz.core.common.entity.IdEntity;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import com.saili.hzz.web.system.pojo.base.TSUser;

/**
 * 巡河事件记录
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_base_river_lake_patrol_event")
public class TSLBaseRiverLakePatrolEvent extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/** 巡河记录*/
	private TSLBaseRiverLakePatrol tslBaseRiverLakePatrol;
	/** 河道*/
	private TSLBaseRiverLake tslBaseRiverLake;
	/** 行政区划 */
	private TSLDivisionEntity tslDivision;
	/** 事件照片记录*/
	private List<TSLBaseRiverLakePatrolEventPhoto> listEventPhoto= new ArrayList<TSLBaseRiverLakePatrolEventPhoto>();
	/** 事件处理照片记录*/
	private List<TSLBaseRiverLakePatrolEventPhoto1> listEventPhoto1= new ArrayList<TSLBaseRiverLakePatrolEventPhoto1>();
	/** 巡河人事件人*/
	private TSUser tsSubmitUser;
	/** 事件处理过程记录*/
	private List<TSLBaseRiverLakePatrolEventProcess> listEventProcess= new ArrayList<TSLBaseRiverLakePatrolEventProcess>();
	/***
	 * 事件来源类型 放到字典表里
	 * code=sourceType=10巡河11微信12电话 字典
	 */
	private java.lang.String sourceType;
	/***
	 * 事件类型 放到字典表里
	 * code=questType=10漂浮物11污水偷排12违章建筑13非法捕捞14破坏岸线15河坝渗漏垮塌16河岸有垃圾17存在围垦湖泊18存在侵占水域19	存在非法采砂20存在违法养殖
	 */
	private java.lang.String questType;
	/** 事件时间*/
	private java.util.Date eventDate;
	/** 事件Lng*/
	private java.lang.String eventLng;
	/** 事件Lat*/
	private java.lang.String eventLat;
	/** 事件具体地址*/
	private java.lang.String eventAddress;
	/** 事件名称 */
	private java.lang.String name;
	/** 事件编号 */
	private java.lang.String code;
	/** 事件内容 */
	private java.lang.String memo;
	/***
	 * 事件处理状态放到字典表里
	 * code=eventStatus
	 */
	private java.lang.String eventStatus;
	/** 处理内容 处理结果*/
	private java.lang.String handleMemo;
	/** 处理时间*/
	private java.util.Date handleDate;
	/***
	 * 处理是否属实
	 * code=handleIsTF
	 */
	private java.lang.String handleIsTF;
	/***
	 * 处理类型
	 * code=handleType
	 */
	private java.lang.String handleType;
	/** 处理人姓名*/
	private java.lang.String handleName;
	/** 处理人电话*/
	private java.lang.String handlePhone;
	/** 处理人部门 输入真实的部门 不需要选择*/
	private java.lang.String handleDept;
	/** 回访时间*/
	private java.util.Date returnDate;
	/** 群众满意度*/
	private java.lang.String massSatisP;//code=massSatisP
	/** 办结用时以天为单位 ,以事件开始时间(投诉时间)到处理结束时间的天数*/
	private Integer finishedDay;
	/** 处理用时以小时为单位,从事件转派最后一位到手的那个人时间到处理结束时间的小时数，如果没有转派人就是0*/
	private Integer handleHour;
	/** 投拆人姓名*/
	private java.lang.String complainantName;
	/** 投拆人电话*/
	private java.lang.String complainantPhone;
	
	
	/**创建时间*/
	private java.util.Date createDate;
	/**创建人ID*/
	private java.lang.String createBy;
	/**创建人名称*/
	private java.lang.String createName;
	/***
	 * 流程id
	 */
	private java.lang.String procInsId;

	/***
	 * @deprecated
	 */
	private int lid;

	@Transient
	public String getAddParentCode() {
		if (null == this.tslDivision) {
			return null;
		} else if (StringUtils.isNotBlank(tslDivision.getParentCodes()) && StringUtils.isNotBlank(tslDivision.getCode())) {
			return tslDivision.getParentCodes() + tslDivision.getCode();
		} else if (StringUtils.isNotBlank(tslDivision.getParentCodes())) {
			return tslDivision.getParentCodes();
		}
		return this.getDivisionCode();
	}

	@Transient
	public String getDivisionCode() {
		if (null == this.tslDivision) {
			return null;
		}
		return this.tslDivision.getCode();
	}

	@Transient
	public String keyArea() {
		if (null == this.tslDivision) {
			return null;
		}
		return this.tslDivision.getCode();
	}

	@Column(name ="proc_ins_id",nullable=true)
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	/***
	 * @deprecated
	 */
	@Column(name ="lid",nullable=true, updatable = false)
	public int getLid() {
		return lid;
	}

	/***
	 * @deprecated
	 */
	public void setLid(int lid) {
		this.lid = lid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="create_date",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人ID
	 */
	@Column(name ="create_by",nullable=true,length=32)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人ID
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="create_name",nullable=true,length=32)
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
	private String tempName; //计算用
	@Transient
	public String getTempName() {
		return tempName;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	
	
	
	@Column(name ="handle_memo",nullable=true)
	public java.lang.String getHandleMemo() {
		return handleMemo;
	}
	public void setHandleMemo(java.lang.String handleMemo) {
		this.handleMemo = handleMemo;
	}
	@Column(name ="handle_date",nullable=true)
	public java.util.Date getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(java.util.Date handleDate) {
		this.handleDate = handleDate;
	}
	@Column(name ="handle_is_tf",nullable=true)
	public java.lang.String getHandleIsTF() {
		return handleIsTF;
	}
	public void setHandleIsTF(java.lang.String handleIsTF) {
		this.handleIsTF = handleIsTF;
	}
	@Column(name ="handle_name",nullable=true)
	public java.lang.String getHandleName() {
		return handleName;
	}
	public void setHandleName(java.lang.String handleName) {
		this.handleName = handleName;
	}
	@Column(name ="handle_type",nullable=true)
	public java.lang.String getHandleType() {
		return handleType;
	}
	public void setHandleType(java.lang.String handleType) {
		this.handleType = handleType;
	}
	@Column(name ="handle_phone",nullable=true)
	public java.lang.String getHandlePhone() {
		return handlePhone;
	}
	public void setHandlePhone(java.lang.String handlePhone) {
		this.handlePhone = handlePhone;
	}
	@Column(name ="handle_dept",nullable=true)
	public java.lang.String getHandleDept() {
		return handleDept;
	}
	public void setHandleDept(java.lang.String handleDept) {
		this.handleDept = handleDept;
	}
	@Column(name ="return_date",nullable=true)
	public java.util.Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(java.util.Date returnDate) {
		this.returnDate = returnDate;
	}
	@Column(name ="mass_satis_p",nullable=true)
	public java.lang.String getMassSatisP() {
		return massSatisP;
	}
	public void setMassSatisP(java.lang.String massSatisP) {
		this.massSatisP = massSatisP;
	}
	@Column(name ="finished_day",nullable=true)
	public Integer getFinishedDay() {
		return finishedDay;
	}
	public void setFinishedDay(Integer finishedDay) {
		this.finishedDay = finishedDay;
	}
	@Column(name ="handle_hour",nullable=true)
	public Integer getHandleHour() {
		return handleHour;
	}
	public void setHandleHour(Integer handleHour) {
		this.handleHour = handleHour;
	}
	@Column(name ="complainant_name",nullable=true)
	public java.lang.String getComplainantName() {
		return complainantName;
	}
	public void setComplainantName(java.lang.String complainantName) {
		this.complainantName = complainantName;
	}
	@Column(name ="complainant_phone",nullable=true)
	public java.lang.String getComplainantPhone() {
		return complainantPhone;
	}
	public void setComplainantPhone(java.lang.String complainantPhone) {
		this.complainantPhone = complainantPhone;
	}
	@Column(name ="event_address",nullable=true)
	public java.lang.String getEventAddress() {
		return eventAddress;
	}
	public void setEventAddress(java.lang.String eventAddress) {
		this.eventAddress = eventAddress;
	}
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
	@Column(name ="event_status",nullable=true)
	public java.lang.String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(java.lang.String status) {
		this.eventStatus = status;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_river_lake_patrol_id")
	public TSLBaseRiverLakePatrol getTslBaseRiverLakePatrol() {
		return tslBaseRiverLakePatrol;
	}
	public void setTslBaseRiverLakePatrol(TSLBaseRiverLakePatrol tslBaseRiverLakePatrol) {
		this.tslBaseRiverLakePatrol = tslBaseRiverLakePatrol;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "base_river_lake_id")
	@JoinColumn(name = "base_river_lake_code",referencedColumnName = "code")
	public TSLBaseRiverLake getTslBaseRiverLake() {
		return tslBaseRiverLake;
	}
	public void setTslBaseRiverLake(TSLBaseRiverLake tslBaseRiverLake) {
		this.tslBaseRiverLake = tslBaseRiverLake;
	}
	@Column(name ="quest_type",nullable=true)
	public java.lang.String getQuestType() {
		return questType;
	}
	public void setQuestType(java.lang.String questionType) {
		this.questType = questionType;
	}
	@Column(name ="source_type",nullable=true)
	public java.lang.String getSourceType() {
		return sourceType;
	}
	public void setSourceType(java.lang.String sourceType) {
		this.sourceType = sourceType;
	}
	@Column(name ="event_date",nullable=true)
	public java.util.Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(java.util.Date eventDate) {
		this.eventDate = eventDate;
	}
	@Column(name ="event_lng",nullable=true)
	public java.lang.String getEventLng() {
		return eventLng;
	}
	public void setEventLng(java.lang.String eventLng) {
		this.eventLng = eventLng;
	}
	@Column(name ="event_lat",nullable=true)
	public java.lang.String getEventLat() {
		return eventLat;
	}
	public void setEventLat(java.lang.String eventLat) {
		this.eventLat = eventLat;
	}
	@Column(name ="name",nullable=true)
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	@Column(name ="code",nullable=true)
	public java.lang.String getCode() {
		return code;
	}
	public void setCode(java.lang.String code) {
		this.code = code;
	}
	@Column(name ="memo",nullable=true)
	public java.lang.String getMemo() {
		return memo;
	}
	public void setMemo(java.lang.String memo) {
		this.memo = memo;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "tslBaseRiverLakePatrolEvent")
	public List<TSLBaseRiverLakePatrolEventPhoto1> getListEventPhoto1() {
		return listEventPhoto1;
	}

	public void setListEventPhoto1(List<TSLBaseRiverLakePatrolEventPhoto1> listGPSEventPhoto1) {
		this.listEventPhoto1 = listGPSEventPhoto1;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "tslBaseRiverLakePatrolEvent")
	public List<TSLBaseRiverLakePatrolEventPhoto> getListEventPhoto() {
		return listEventPhoto;
	}

	public void setListEventPhoto(List<TSLBaseRiverLakePatrolEventPhoto> listGPSEventPhoto) {
		this.listEventPhoto = listGPSEventPhoto;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "tslBaseRiverLakePatrolEvent")
	public List<TSLBaseRiverLakePatrolEventProcess> getListEventProcess() {
		return listEventProcess;
	}
	public void setListEventProcess(List<TSLBaseRiverLakePatrolEventProcess> listGPSEventProcess) {
		this.listEventProcess = listGPSEventProcess;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_submit_id")
	public TSUser getTsSubmitUser() {
		return tsSubmitUser;
	}
	public void setTsSubmitUser(TSUser tsSubmitUser) {
		this.tsSubmitUser = tsSubmitUser;
	}
}
