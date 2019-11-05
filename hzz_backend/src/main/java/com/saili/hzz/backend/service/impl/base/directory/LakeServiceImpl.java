package com.saili.hzz.backend.service.impl.base.directory;

import com.saili.hzz.core.dao.mybatisdao.base.directory.LakeDao;
import com.saili.hzz.core.entity.TSLRLLakeEntity;
import com.saili.hzz.backend.service.base.directory.LakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 湖泊 service impl
 * @author whuab_mc
 */
@Service
@Transactional(readOnly = true)
public class LakeServiceImpl implements LakeService {
    @Autowired
    private LakeDao lakeDao;

    @Override
    public int countByCode(String code) {
        return lakeDao.countByCode(code);
    }

    @Override
    public TSLRLLakeEntity get(String id) {
        return lakeDao.get(id);
    }
}
