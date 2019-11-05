package com.saili.hzz.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.saili.hzz.core.common.entity.IdEntity;
import com.saili.hzz.web.system.pojo.base.TSDepart;
import com.saili.hzz.web.system.pojo.base.TSUser;

/**
 * 事件问题处理流程指派的人或部门列表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_base_river_lake_patrol_event_process_du")
public class TSLBaseRiverLakePatrolEventProcessDU extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**巡河事件 */
	private TSLBaseRiverLakePatrolEvent tslBaseRiverLakePatrolEvent;
	/** 事件问题处理流程*/
	private TSLBaseRiverLakePatrolEventProcess tslBaseRiverLakePatrolEventProcess; 
	/*** 处理人 */
	private TSUser tsHandleUser;
	/** 处理部门 */
	private TSDepart tsHandleDepart;
	/** 处理人类型 10处理人11处理单位*/
	private java.lang.String processType;
	/** 状态  是否处理完成 10正在进行的任务 11已完成的任务*/
	private int iType;
	
	
	@Column(name ="i_type",nullable=true)
	public int getiType() {
		return iType;
	}
	public void setiType(int iType) {
		this.iType = iType;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_river_lake_patrol_event_id")
	public TSLBaseRiverLakePatrolEvent getTslBaseRiverLakePatrolEvent() {
		return tslBaseRiverLakePatrolEvent;
	}
	public void setTslBaseRiverLakePatrolEvent(TSLBaseRiverLakePatrolEvent tslBaseRiverLakePatrolEvent) {
		this.tslBaseRiverLakePatrolEvent = tslBaseRiverLakePatrolEvent;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_river_lake_patrol_event_process_id")
	public TSLBaseRiverLakePatrolEventProcess getTslBaseRiverLakePatrolEventProcess() {
		return tslBaseRiverLakePatrolEventProcess;
	}
	public void setTslBaseRiverLakePatrolEventProcess(
			TSLBaseRiverLakePatrolEventProcess tslBaseRiverLakePatrolEventProcess) {
		this.tslBaseRiverLakePatrolEventProcess = tslBaseRiverLakePatrolEventProcess;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "handle_user_id")
	public TSUser getTsHandleUser() {
		return tsHandleUser;
	}
	public void setTsHandleUser(TSUser tsHandleUser) {
		this.tsHandleUser = tsHandleUser;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "handle_depart_id")
	public TSDepart getTsHandleDepart() {
		return tsHandleDepart;
	}
	public void setTsHandleDepart(TSDepart tsHandleDepart) {
		this.tsHandleDepart = tsHandleDepart;
	}
	@Column(name ="process_type",nullable=true)
	public java.lang.String getProcessType() {
		return processType;
	}
	public void setProcessType(java.lang.String processType) {
		this.processType = processType;
	}
	
	
}
