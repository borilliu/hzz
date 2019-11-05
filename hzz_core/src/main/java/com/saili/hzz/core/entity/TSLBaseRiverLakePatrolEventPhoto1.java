package com.saili.hzz.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.saili.hzz.core.common.entity.IdEntity;

/**
 * 巡河事件处理的照片记录
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_base_river_lake_patrol_event_photo1")
public class TSLBaseRiverLakePatrolEventPhoto1 extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**巡河事件记录 */
	private TSLBaseRiverLakePatrolEvent tslBaseRiverLakePatrolEvent;
	/**照片时间*/
	private java.util.Date photoDate;
	/**图片地址*/
	private java.lang.String photo;
	/** 上传文件的id*/
	private java.lang.String cgUploadId;
	
	@Column(name ="cg_upload_id",nullable=true)
	public java.lang.String getCgUploadId() {
		return cgUploadId;
	}
	public void setCgUploadId(java.lang.String cgUploadId) {
		this.cgUploadId = cgUploadId;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_river_lake_patrol_event_id")
	public TSLBaseRiverLakePatrolEvent getTslBaseRiverLakePatrolEvent() {
		return tslBaseRiverLakePatrolEvent;
	}
	public void setTslBaseRiverLakePatrolEvent(TSLBaseRiverLakePatrolEvent tslBaseRiverLakePatrolEvent) {
		this.tslBaseRiverLakePatrolEvent = tslBaseRiverLakePatrolEvent;
	}
	@Column(name ="photo_date",nullable=true)
	public java.util.Date getPhotoDate() {
		return photoDate;
	}
	public void setPhotoDate(java.util.Date photoDate) {
		this.photoDate = photoDate;
	}
	@Column(name ="photo",nullable=true)
	public java.lang.String getPhoto() {
		return photo;
	}
	public void setPhoto(java.lang.String photo) {
		this.photo = photo;
	}
}
