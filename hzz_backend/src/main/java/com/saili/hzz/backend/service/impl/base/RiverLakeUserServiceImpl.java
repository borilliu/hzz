package com.saili.hzz.backend.service.impl.base;

import com.saili.hzz.core.dao.mybatisdao.base.RiverLakeUserDao;
import com.saili.hzz.core.entity.TSLBaseRiverLakeUser;
import com.saili.hzz.backend.service.base.RiverLakeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-21 09:17:9:17
 * @version: V1.0
 */
@Service
public class RiverLakeUserServiceImpl implements RiverLakeUserService {
    @Autowired
    private RiverLakeUserDao riverLakeUserDao;

    @Override
    public List<TSLBaseRiverLakeUser> listByRiverLakeId(String riverLakeId) {
        return riverLakeUserDao.listByRiverLakeId(riverLakeId);
    }
}
