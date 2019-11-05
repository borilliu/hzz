package com.saili.hzz.backend.service.impl;

import com.saili.hzz.core.dao.mybatisdao.RiverLakeDao;
import com.saili.hzz.core.dao.mybatisdao.base.directory.LakeDao;
import com.saili.hzz.core.dao.mybatisdao.base.directory.ReachDao;
import com.saili.hzz.core.dao.mybatisdao.base.directory.ReservoirDao;
import com.saili.hzz.core.dao.mybatisdao.base.directory.RiverDao;
import com.saili.hzz.core.dao.mybatisdao.base.project.IntakeDao;
import com.saili.hzz.core.dao.mybatisdao.base.project.SewageOutletDao;
import com.saili.hzz.core.dao.mybatisdao.base.project.SurveyStationDao;
import com.saili.hzz.core.dao.mybatisdao.base.project.WaterFunctionDao;
import com.saili.hzz.core.entity.TSLBaseRiverLake;
import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.enums.RiverLakeTypeEnum;
import com.saili.hzz.backend.service.RiverLakeService;
import com.saili.hzz.core.common.model.json.ValidForm;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 河湖名录 service impl
 * @author whuab_mc
 */

@Service
@Transactional(readOnly = true)
public class RiverLakeServiceImpl implements RiverLakeService {
    @Autowired
    private RiverDao riverDao;
    @Autowired
    private LakeDao lakeDao;
    @Autowired
    private ReachDao reachDao;
    @Autowired
    private ReservoirDao reservoirDao;
    @Autowired
    private RiverLakeDao riverLakeDao;

    @Autowired
    private IntakeDao intakeDao;
    @Autowired
    private WaterFunctionDao waterFunctionDao;
    @Autowired
    private SurveyStationDao surveyStationDao;
    @Autowired
    private SewageOutletDao sewageOutletDao;

    @Override
    public ValidForm checkRiverLakeCode(String type, String code) {
        ValidForm v = new ValidForm();
        v.setInfo("验证通过！");
        v.setStatus("y");
        StringBuilder msg = new StringBuilder();
        int count = 0;
        switch (type) {
            case "a":
                count = countRiver(code);
                msg.append(RiverLakeTypeEnum.RIVER.getName());
                break;
            case "b":
                count = countLake(code);
                msg.append(RiverLakeTypeEnum.LAKE.getName());
                break;
            case "c":
                count = countReach(code);
                msg.append(RiverLakeTypeEnum.REACH.getName());
                break;
            case "d":
                count = countReservoir(code);
                msg.append(RiverLakeTypeEnum.RESERVOIR.getName());
                break;

            case "a1":
                count = countIntake(code);
                msg.append(RiverLakeTypeEnum.INTAKE.getName());
                break;
            case "b1":
                count = countWaterFunction(code);
                msg.append(RiverLakeTypeEnum.WATER_FUNCTION.getName());
                break;
            case "c1":
                count = countSurveyStation(code);
                msg.append(RiverLakeTypeEnum.SURVEY_STATION.getName());
                break;
            case "d1":
                count = countSewageOutlet(code);
                msg.append(RiverLakeTypeEnum.SEWAGE_OUTLET.getName());
                break;
            default:
                count = 0;
        }

        if(count > 0)
        {
            v.setInfo(msg.append("编码已存在！").toString());
            v.setStatus("n");
            return v;
        }

        return v;
    }

    @Override
    public List<TSLBaseRiverLake> listByDivision(TSLDivisionEntity division) {
        if (null == division || StringUtils.isBlank(division.getCode())) {
            return null;
        }
        return riverLakeDao.listByDivision(division);
    }

    @Override
    public List<TSLBaseRiverLake> listByContainsParentDivision(TSLDivisionEntity parentDivision) {
        if (null == parentDivision || StringUtils.isBlank(parentDivision.getCode())) {
            return null;
        }
        return riverLakeDao.listByContainsParentDivision(parentDivision);
    }

    /**
     * 根据标识点坐标获取河湖信息
     *
     * @param publicSignsLng
     * @param publicSignsLat
     * @return
     */
    @Override
    public TSLBaseRiverLake getByMarkerPoint(Double publicSignsLng, Double publicSignsLat) {
        return riverLakeDao.getByMarkerPoint(publicSignsLng, publicSignsLat);
    }

    /**
     * 同一河流编码的河流数量
     * @param code
     * @return
     */
    private int countRiver(String code) {
        return riverDao.countByCode(code);
    }

    /**
     * 同一湖泊编码的湖泊数量
     * @param code
     * @return
     */
    private int countLake(String code) {
        return lakeDao.countByCode(code);
    }

    /**
     * 同一河段编码的河段数量
     * @param code
     * @return
     */
    private int countReach(String code) {
        return reachDao.countByCode(code);
    }

    /**
     * 同一水库编码的水库数量
     * @param code
     * @return
     */
    private int countReservoir(String code) {
        return reservoirDao.countByCode(code);
    }



    /**
     * 同一取水口编码的取水口数量
     * @param code
     * @return
     */
    private int countIntake(String code) {
        return intakeDao.countByCode(code);
    }

    /**
     * 同一水功能区编码的水功能区数量
     * @param code
     * @return
     */
    private int countWaterFunction(String code) {
        return waterFunctionDao.countByCode(code);
    }

    /**
     * 同一测站编码的测站数量
     * @param code
     * @return
     */
    private int countSurveyStation(String code) {
        return surveyStationDao.countByCode(code);
    }

    /**
     * 同一排污口编码的排污口数量
     * @param code
     * @return
     */
    private int countSewageOutlet(String code) {
        return sewageOutletDao.countByCode(code);
    }
}
