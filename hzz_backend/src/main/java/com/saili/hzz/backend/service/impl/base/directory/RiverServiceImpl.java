package com.saili.hzz.backend.service.impl.base.directory;

import com.saili.hzz.core.dao.mybatisdao.base.directory.RiverDao;
import com.saili.hzz.core.entity.TSLRLRiverEntity;
import com.saili.hzz.backend.service.base.directory.RiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 河流 service impl
 * @author whuab_mc
 */
@Service
@Transactional(readOnly = true)
public class RiverServiceImpl implements RiverService {
    @Autowired
    private RiverDao riverDao;

    @Override
    public int countByCode(String code) {
        return riverDao.countByCode(code);
    }

    @Override
    public TSLRLRiverEntity get(String id) {
        return riverDao.get(id);
    }
}
