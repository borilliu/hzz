package com.saili.hzz.core.domain;

import com.saili.hzz.core.common.entity.IdEntity;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-07 11:53:11:53
 * @version: V1.0
 */
public class BasicDataStatsDo  extends IdEntity {

    /**
     * 地区数量
     */
    private int areaCount;
    /**
     * 第一总河长数量
     */
    private int firstPresidentHzCount;
    /**
     * 总河长数量
     */
    private int presidentHzCount;
    /**
     * 副总河长数量
     */
    private int vicePresidentHzCount;
    /**
     * 河长数量
     */
    private int hzCount;
    /**
     * 副河长数量
     */
    private int viceHzCount;
    /**
     * 河流数量
     */
    private int riverCount;
    /**
     * 湖泊数量
     */
    private int lakeCount;
    /**
     * 河段数量
     */
    private int reachCount;
    /**
     * 水库数量
     */
    private int reservoirCount;

    public BasicDataStatsDo() {
    }

    public int getAreaCount() {
        return areaCount;
    }

    public void setAreaCount(int areaCount) {
        this.areaCount = areaCount;
    }

    public int getFirstPresidentHzCount() {
        return firstPresidentHzCount;
    }

    public void setFirstPresidentHzCount(int firstPresidentHzCount) {
        this.firstPresidentHzCount = firstPresidentHzCount;
    }

    public int getPresidentHzCount() {
        return presidentHzCount;
    }

    public void setPresidentHzCount(int presidentHzCount) {
        this.presidentHzCount = presidentHzCount;
    }

    public int getVicePresidentHzCount() {
        return vicePresidentHzCount;
    }

    public void setVicePresidentHzCount(int vicePresidentHzCount) {
        this.vicePresidentHzCount = vicePresidentHzCount;
    }

    public int getHzCount() {
        return hzCount;
    }

    public void setHzCount(int hzCount) {
        this.hzCount = hzCount;
    }

    public int getViceHzCount() {
        return viceHzCount;
    }

    public void setViceHzCount(int viceHzCount) {
        this.viceHzCount = viceHzCount;
    }

    public int getRiverCount() {
        return riverCount;
    }

    public void setRiverCount(int riverCount) {
        this.riverCount = riverCount;
    }

    public int getLakeCount() {
        return lakeCount;
    }

    public void setLakeCount(int lakeCount) {
        this.lakeCount = lakeCount;
    }

    public int getReachCount() {
        return reachCount;
    }

    public void setReachCount(int reachCount) {
        this.reachCount = reachCount;
    }

    public int getReservoirCount() {
        return reservoirCount;
    }

    public void setReservoirCount(int reservoirCount) {
        this.reservoirCount = reservoirCount;
    }
}
