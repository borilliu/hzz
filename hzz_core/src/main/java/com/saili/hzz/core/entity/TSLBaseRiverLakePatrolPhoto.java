package com.saili.hzz.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.saili.hzz.core.common.entity.IdEntity;

/**
 * 巡河记录的照片记录
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_base_river_lake_patrol_photo")
public class TSLBaseRiverLakePatrolPhoto extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 河湖 */
	private TSLBaseRiverLakePatrol tslBaseRiverLakePatrol;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_river_lake_patrol_id")
	public TSLBaseRiverLakePatrol getTslBaseRiverLakePatrol() {
		return tslBaseRiverLakePatrol;
	}
	public void setTslBaseRiverLakePatrol(TSLBaseRiverLakePatrol tslBaseRiverLakePatrol) {
		this.tslBaseRiverLakePatrol = tslBaseRiverLakePatrol;
	}
	
	/**时间*/
	private java.util.Date photoDate;
	/**Lng*/
	private java.lang.String photoLng;
	/**Lat*/
	private java.lang.String photoLat;

	/**巡河图片地址*/
	private java.lang.String phono;

	@Column(name ="photo_date",nullable=true)
	public java.util.Date getPhotoDate() {
		return photoDate;
	}
	public void setPhotoDate(java.util.Date photoDate) {
		this.photoDate = photoDate;
	}
	@Column(name ="photo_lng",nullable=true)
	public java.lang.String getPhotoLng() {
		return photoLng;
	}
	public void setPhotoLng(java.lang.String photoLng) {
		this.photoLng = photoLng;
	}
	@Column(name ="photo_lat",nullable=true)
	public java.lang.String getPhotoLat() {
		return photoLat;
	}
	public void setPhotoLat(java.lang.String photoLat) {
		this.photoLat = photoLat;
	}
	@Column(name ="photo",nullable=true)
	public java.lang.String getPhono() {
		return phono;
	}
	public void setPhono(java.lang.String phono) {
		this.phono = phono;
	}
	
	

}
