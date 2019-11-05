package com.saili.hzz.core.dao.mybatisdao.base;

import com.saili.hzz.core.annotation.MyBatisDao;
import com.saili.hzz.core.entity.TSLBaseRiverLakeUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-21 09:40:9:40
 * @version: V1.0
 */
@MyBatisDao
public interface RiverLakeUserDao {

    List<TSLBaseRiverLakeUser> listByRiverLakeId(@Param("riverLakeId") String riverLakeId);
}
