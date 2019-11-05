package com.saili.hzz.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.saili.hzz.core.common.entity.IdEntity;
/**
 * 巡河GPS记录
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sl_base_river_lake_patrol_gps")
public class TSLBaseRiverLakePatrolGPS extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private TSLBaseRiverLakePatrol tslBaseRiverLakePatrol;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_river_lake_patrol_id")
	public TSLBaseRiverLakePatrol getTslBaseRiverLakePatrol() {
		return tslBaseRiverLakePatrol;
	}
	public void setTslBaseRiverLakePatrol(TSLBaseRiverLakePatrol tslBaseRiverLakePatrol) {
		this.tslBaseRiverLakePatrol = tslBaseRiverLakePatrol;
	}
	
	/**GPS时间*/
	private java.util.Date gpsDate;
	/**GPSLng*/
	private java.lang.String gpsLng;
	/**GPSLat*/
	private java.lang.String gpsLat;
	
	@Column(name ="gps_date",nullable=true)
	public java.util.Date getGpsDate() {
		return gpsDate;
	}
	public void setGpsDate(java.util.Date gpsDate) {
		this.gpsDate = gpsDate;
	}
	
	@Column(name ="gps_lng",nullable=true)
	public java.lang.String getGpsLng() {
		return gpsLng;
	}
	public void setGpsLng(java.lang.String gpsLng) {
		this.gpsLng = gpsLng;
	}
	
	@Column(name ="gps_lat",nullable=true)
	public java.lang.String getGpsLat() {
		return gpsLat;
	}
	public void setGpsLat(java.lang.String gpsLat) {
		this.gpsLat = gpsLat;
	}

	
	
	
	
	
}
