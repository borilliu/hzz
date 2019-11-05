package com.saili.hzz.core.dao.mybatisdao.statistical;

import com.saili.hzz.core.annotation.MyBatisDao;
import com.saili.hzz.core.vo.HzReportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 *
 * @author: whuab_mc
 * @date: 2019-10-06 14:31:14:31
 * @version: V1.0
 */
@MyBatisDao
public interface HzReportDao {
    List<HzReportVo> listByDivisionCode(@Param("code") String code);
}
