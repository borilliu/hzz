package com.saili.hzz.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.saili.hzz.core.common.entity.IdEntity;
import com.saili.hzz.web.system.pojo.base.TSUser;

/**
 * 事件问题处理流程
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_base_river_lake_patrol_event_process")
public class TSLBaseRiverLakePatrolEventProcess extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**巡河事件 */
	private TSLBaseRiverLakePatrolEvent tslBaseRiverLakePatrolEvent;
	/**提交人*/
	private TSUser tsSubmitUser;
	/**提交时间*/
	private java.util.Date processDate;
	/**提交内容 梁立坚交办湖南省河长办*/
	private java.lang.String processMemo;
	//时间	已结案	
	//时间	小李已处置	
	//时间	市河长办交办到小李	
	//时间	省河长办交办到市河长办	
	//时间	小王上报到省河长办
	//时间	小王提交问题
	/** 事件处理状态放到字典表里*/
	private java.lang.String eventStat;//code=eventStat==10已提交11已上报12已交办14已处置15已结案
	/** 审批意见*/
	private java.lang.String approvalMemo;
	/** 审批是否通过 code=yesorno==YN*/
	private java.lang.String approvalIsYN;  
	//10已提交11已上报12已交办13处置中14已处置15已结案
	/*
	 *	这里不用了，要用列表
	 * * 处理人 
	private TSUser tsHandleUser;	
	* 处理部门 
	private TSDepart tsHandleDepart;
	* 处理人类型 10处理人11处理单位  
	private java.lang.String processType;
	*/	
	@Column(name ="approval_isyn",nullable=true)
	public java.lang.String getApprovalIsYN() {
		return approvalIsYN;
	}
	public void setApprovalIsYN(java.lang.String approvalIsYN) {
		this.approvalIsYN = approvalIsYN;
	}	
	@Column(name ="approval_memo",nullable=true)
	public java.lang.String getApprovalMemo() {
		return approvalMemo;
	}
	public void setApprovalMemo(java.lang.String approvalMemo) {
		this.approvalMemo = approvalMemo;
	}	
	@Column(name ="event_status",nullable=true)
	public java.lang.String getEventStat() {
		return eventStat;
	}
	public void setEventStat(java.lang.String eventStat) {
		this.eventStat = eventStat;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_river_lake_patrol_event_id")
	public TSLBaseRiverLakePatrolEvent getTslBaseRiverLakePatrolEvent() {
		return tslBaseRiverLakePatrolEvent;
	}
	public void setTslBaseRiverLakePatrolEvent(TSLBaseRiverLakePatrolEvent tslBaseRiverLakePatrolEvent) {
		this.tslBaseRiverLakePatrolEvent = tslBaseRiverLakePatrolEvent;
	}
	@Column(name ="process_date",nullable=true)
	public java.util.Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(java.util.Date processDate) {
		this.processDate = processDate;
	}
	@Column(name ="process_memo",nullable=true)
	public java.lang.String getProcessMemo() {
		return processMemo;
	}
	public void setProcessMemo(java.lang.String processMemo) {
		this.processMemo = processMemo;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_submit_id")
	public TSUser getTsSubmitUser() {
		return tsSubmitUser;
	}
	public void setTsSubmitUser(TSUser tsSubmitUser) {
		this.tsSubmitUser = tsSubmitUser;
	}
	/*
	@Column(name ="process_type",nullable=true)
	public java.lang.String getProcessType() {
		return processType;
	}
	public void setProcessType(java.lang.String processType) {
		this.processType = processType;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_handle_id")
	public TSUser getTsHandleUser() {
		return tsHandleUser;
	}
	public void setTsHandleUser(TSUser tsHandleUser) {
		this.tsHandleUser = tsHandleUser;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_handle_id")
	public TSDepart getTsHandleDepart() {
		return tsHandleDepart;
	}
	public void setTsHandleDepart(TSDepart tsHandleDepart) {
		this.tsHandleDepart = tsHandleDepart;
	}*/	
}
