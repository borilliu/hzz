package com.saili.hzz.core.vo.onemap;

import java.util.List;
import java.util.Map;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-21 11:25:11:25
 * @version: V1.0
 */
public class DrawRiverLakeDataVo {
    /**
     * 河流经纬度
     */
    private List<Map<String, Object>> riverLakeLinePoints;
    /**
     * 标识名称
     */
    private String labelName;
    /**
     * 河长公示牌经度
     */
    private Double publicSignsLng;
    /**
     * 河长公示牌纬度
     */
    private Double publicSignsLat;

    public DrawRiverLakeDataVo() {
    }

    public List<Map<String, Object>> getRiverLakeLinePoints() {
        return riverLakeLinePoints;
    }

    public void setRiverLakeLinePoints(List<Map<String, Object>> riverLakeLinePoints) {
        this.riverLakeLinePoints = riverLakeLinePoints;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Double getPublicSignsLng() {
        return publicSignsLng;
    }

    public void setPublicSignsLng(Double publicSignsLng) {
        this.publicSignsLng = publicSignsLng;
    }

    public Double getPublicSignsLat() {
        return publicSignsLat;
    }

    public void setPublicSignsLat(Double publicSignsLat) {
        this.publicSignsLat = publicSignsLat;
    }
}
