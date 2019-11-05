package com.saili.hzz.backend.service.impl;

import com.saili.hzz.core.dao.mybatisdao.TestDataDao;
import com.saili.hzz.core.entity.TestUser;
import com.saili.hzz.backend.service.TestDataSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description：
 * @author: whuab_mc
 * @create: 2019-06-27 22:07
 * @version:
 */
@Service
@Transactional(readOnly = true)
public class TestDataServiceImpl implements TestDataSevice {

    @Autowired
    private TestDataDao testDataDao;

    @Override
    public TestUser get(String id) {
        return testDataDao.get(id);
    }
}
