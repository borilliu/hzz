package com.saili.hzz.core.dao;

import com.saili.hzz.core.entity.TSLBaseRiverLake;
import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.Sql;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SLRiverLakeDao {

    List<TSLBaseRiverLake> testMinidao(@Param("testcode") String code);

    @Sql("select riverLake.* from t_sl_base_river_lake riverLake where riverLake.code = :testcode")
    List<TSLBaseRiverLake> testSql(@Param("testcode") String code);
}
