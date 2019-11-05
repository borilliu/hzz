package com.saili.hzz.core.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.saili.hzz.core.common.entity.IdEntity;

/**
 * 用户-河湖 实体
 * 河长-河湖
 * 河长列表
 * <p/>
 * <p><b>User:</b> zhanggm <a href="mailto:guomingzhang2008@gmail.com">guomingzhang2008@gmail.com</a></p>
 * <p><b>Date:</b> 2014-08-22 15:39</p>
 *
 * @author liuby
 */
@Entity
@Table(name = "t_sl_base_river_lake_user")
public class TSLBaseRiverLakeUser extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private TSLBaseRiverLake tslBaseRiverLake;
	private RiverLakeChiefEntity riverLakeChief;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_river_lake_id")
	public TSLBaseRiverLake getTslBaseRiverLake() {
		return tslBaseRiverLake;
	}
	public void setTslBaseRiverLake(TSLBaseRiverLake tslBaseRiverLake) {
		this.tslBaseRiverLake = tslBaseRiverLake;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	public RiverLakeChiefEntity getRiverLakeChief() {
		return riverLakeChief;
	}
	public void setRiverLakeChief(RiverLakeChiefEntity riverLakeChief) {
		this.riverLakeChief = riverLakeChief;
	}
}
