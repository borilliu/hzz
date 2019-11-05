package com.saili.hzz.core.vo.onemap;

import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.TSLBaseRiverLake;

import java.util.List;
import java.util.stream.Collectors;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-18 09:47:9:47
 * @version: V1.0
 */
public class RiverInfoVo<T extends TSLBaseRiverLake> {
    /**
     * 河长信息
     */
    private List<RiverLakeChiefEntity> hzList;
    /**
     * 河湖信息
     */
    private T riverLake;

    public RiverInfoVo() {
    }

    public String getHzName() {
        if (null == hzList || hzList.size() <= 0) {
            return null;
        }
        return hzList.stream().map(RiverLakeChiefEntity::getName).collect(Collectors.joining(","));
    }


    public List<RiverLakeChiefEntity> getHzList() {
        return hzList;
    }

    public void setHzList(List<RiverLakeChiefEntity> hzList) {
        this.hzList = hzList;
    }

    public T getRiverLake() {
        return riverLake;
    }

    public void setRiverLake(T riverLake) {
        this.riverLake = riverLake;
    }
}
