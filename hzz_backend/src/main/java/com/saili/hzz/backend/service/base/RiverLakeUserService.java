package com.saili.hzz.backend.service.base;

import com.saili.hzz.core.entity.TSLBaseRiverLakeUser;

import java.util.List;

/**
 * @author: whuab_mc
 * @date: 2019-10-21 09:16:9:16
 * @version: V1.0
 */
public interface RiverLakeUserService {

    /**
     * 根据河湖id查询负责制定河湖的河长
     * @param id
     * @return
     */
    List<TSLBaseRiverLakeUser> listByRiverLakeId(String id);
}
