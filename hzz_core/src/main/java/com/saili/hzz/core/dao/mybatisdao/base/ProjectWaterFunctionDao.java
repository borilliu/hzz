package com.saili.hzz.core.dao.mybatisdao.base;

import com.saili.hzz.core.annotation.MyBatisDao;
import com.saili.hzz.core.entity.TSLRLProjectWaterFunction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface ProjectWaterFunctionDao {
    /**
     * 查询没有映射水质报表的水功能区集
     * @return
     */
    List<TSLRLProjectWaterFunction> listNoRefProjectWaterFunctionQuality(@Param("id") String id);
}
