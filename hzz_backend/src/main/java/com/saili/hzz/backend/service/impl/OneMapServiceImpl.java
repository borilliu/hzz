package com.saili.hzz.backend.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.saili.hzz.core.entity.RiverLakeChiefEntity;
import com.saili.hzz.core.entity.TSLBaseRiverLake;
import com.saili.hzz.core.entity.TSLBaseRiverLakeUser;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.enums.RiverLakeTypeEnum;
import com.saili.hzz.backend.service.OneMapService;
import com.saili.hzz.backend.service.RiverLakeService;
import com.saili.hzz.backend.service.base.RiverLakeUserService;
import com.saili.hzz.backend.service.base.directory.LakeService;
import com.saili.hzz.backend.service.base.directory.ReachService;
import com.saili.hzz.backend.service.base.directory.ReservoirService;
import com.saili.hzz.backend.service.base.directory.RiverService;
import com.saili.hzz.core.vo.onemap.DrawRiverLakeDataVo;
import com.saili.hzz.core.vo.onemap.RiverInfoVo;
import com.saili.hzz.core.constant.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-18 10:27:10:27
 * @version: V1.0
 */
@Service
public class OneMapServiceImpl implements OneMapService {
    Logger logger = LoggerFactory.getLogger(OneMapServiceImpl.class);
    @Autowired
    private RiverLakeService riverLakeService;
    @Autowired
    private RiverService riverService;
    @Autowired
    private LakeService lakeService;
    @Autowired
    private ReachService reachService;
    @Autowired
    private ReservoirService reservoirService;
    @Autowired
    private RiverLakeUserService riverlakeUserService;

    /**
     * 获取河流信息
     *
     * @param publicSignsLng
     * @param publicSignsLat
     * @return
     */
    @Override
    public RiverInfoVo getRiverInfo(Double publicSignsLng, Double publicSignsLat) {
        if (null == publicSignsLng || null == publicSignsLat) {
            return null;
        }

        // 1、河流信息
        TSLBaseRiverLake riverLake = riverLakeService.getByMarkerPoint(publicSignsLng, publicSignsLat);
        // 2、河长信息
        List<TSLBaseRiverLakeUser> riverLakeUserList = riverlakeUserService.listByRiverLakeId(riverLake.getId());
        List<RiverLakeChiefEntity> hzList = riverLakeUserList.stream().map(TSLBaseRiverLakeUser::getRiverLakeChief).collect(Collectors.toList());

        RiverInfoVo vo = getRiverLakeInfo(riverLake);
        vo.setHzList(hzList);
        return vo;
    }

    @Override
    public List<DrawRiverLakeDataVo> listDrawRiverLakeData(TSLDivisionEntity division) {
        List<TSLBaseRiverLake> riverLakeList = riverLakeService.listByContainsParentDivision(division);

        List<DrawRiverLakeDataVo> voList = Lists.newArrayList();
        for (TSLBaseRiverLake riverLake : riverLakeList) {
            JSONArray array = JSONArray.parseArray(riverLake.getRiverRangeData());
            List<Map<String, Object>> riverLakeLinePoints = (List) array;

            DrawRiverLakeDataVo vo = new DrawRiverLakeDataVo();
            vo.setLabelName(riverLake.getName());
            vo.setPublicSignsLng(riverLake.getPublicSignsLng());
            vo.setPublicSignsLat(riverLake.getPublicSignsLat());
            vo.setRiverLakeLinePoints(riverLakeLinePoints);

            voList.add(vo);
        }
        return voList;
    }


    private RiverInfoVo getRiverLakeInfo(TSLBaseRiverLake riverLake) {
        if (null == riverLake || StringUtils.isBlank(riverLake.getRiverLakeType()) || StringUtils.isBlank(riverLake.getId())) {
            return null;
        }

        RiverInfoVo vo = new RiverInfoVo();

        String ename = RiverLakeTypeEnum.getEnameByCode(riverLake.getRiverLakeType());
        switch (ename) {
            case Constants.RIVER_ENAME:
                vo.setRiverLake(riverService.get(riverLake.getId()));
                break;
            case Constants.LAKE_ENAME:
                vo.setRiverLake(lakeService.get(riverLake.getId()));
                break;
            case Constants.REACH_ENAME:
                vo.setRiverLake(reachService.get(riverLake.getId()));
                break;
            case Constants.RESERVOIR_ENAME:
                vo.setRiverLake(reservoirService.get(riverLake.getId()));
                break;
            default:
                return vo;
        }

        return vo;
    }
}
