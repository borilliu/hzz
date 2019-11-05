package com.saili.hzz.backend.service.impl;

import com.saili.hzz.core.dao.SLRiverLakeDao;
import com.saili.hzz.core.dao.mybatisdao.RiverLakeDao;
import com.saili.hzz.core.entity.TSLBaseRiverLake;
import com.saili.hzz.backend.service.SLRiverLakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SLRiverLakeServiceImpl implements SLRiverLakeService {
    @Autowired
    private SLRiverLakeDao slRiverLakeDao;
    @Autowired
    private RiverLakeDao riverLakeDao;

    @Override
    public List<TSLBaseRiverLake> testMinidao() {
        String code = "32333";
        return slRiverLakeDao.testMinidao(code);
    }

    @Override
    public List<TSLBaseRiverLake> testSql() {
        String code = "32333";
        return slRiverLakeDao.testSql(code);
    }

    @Override
    public TSLBaseRiverLake getByCode(String code) throws Exception {
        return riverLakeDao.getByCode(code);
    }

}
