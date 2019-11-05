package com.saili.hzz.backend.service.impl.base.directory;

import com.saili.hzz.core.dao.mybatisdao.base.directory.ReservoirDao;
import com.saili.hzz.core.entity.TSLRLReservoirEntity;
import com.saili.hzz.backend.service.base.directory.ReservoirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 水库 service impl
 * @author whuab_mc
 */
@Service
@Transactional(readOnly = true)
public class ReservoirServiceImpl implements ReservoirService {
    @Autowired
    private ReservoirDao reservoirDao;

    @Override
    public int countByCode(String code) {
        return reservoirDao.countByCode(code);
    }

    @Override
    public TSLRLReservoirEntity get(String id) {
        return null;
    }
}
