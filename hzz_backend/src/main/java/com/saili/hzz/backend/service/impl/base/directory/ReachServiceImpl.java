package com.saili.hzz.backend.service.impl.base.directory;

import com.saili.hzz.core.dao.mybatisdao.base.directory.ReachDao;
import com.saili.hzz.core.entity.TSLRLReachEntity;
import com.saili.hzz.backend.service.base.directory.ReachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 河段 service impl
 * @author whuab_mc
 */
@Service
@Transactional(readOnly = true)
public class ReachServiceImpl implements ReachService {
    @Autowired
    private ReachDao reachDao;

    @Override
    public int countByCode(String code) {
        return reachDao.countByCode(code);
    }

    @Override
    public TSLRLReachEntity get(String id) {
        return null;
    }
}
