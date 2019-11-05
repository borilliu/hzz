package com.saili.hzz.backend.service.impl.event;

import com.saili.hzz.core.entity.TSLBaseRiverLake;
import com.saili.hzz.core.entity.TSLBaseRiverLakePatrol;
import com.saili.hzz.backend.service.base.RiverLakeChiefService;
import com.saili.hzz.backend.service.event.RiverLakePatrolService;
import com.saili.hzz.backend.service.SLRiverLakeService;
import com.saili.hzz.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional(readOnly = true)
public class RiverLakePatrolServiceImpl implements RiverLakePatrolService {

    @Autowired
    private SystemService systemService;
    @Autowired
    private SLRiverLakeService slRiverLakeService;
    @Autowired
    private RiverLakeChiefService riverLakeChiefService;

    @Override
    @Transactional(readOnly = false)
    public int save(TSLBaseRiverLakePatrol riverLakePatrol) {
        try {
            TSLBaseRiverLake riverLake = slRiverLakeService.getByCode(riverLakePatrol.getTslBaseRiverLake().getCode());
            riverLakePatrol.setTslBaseRiverLake(riverLake);
            Serializable result = systemService.save(riverLakePatrol);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
