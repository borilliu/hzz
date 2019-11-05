package com.saili.hzz.backend.util.bussiness;

import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.backend.service.base.DivisionService;
import com.saili.hzz.core.constant.Constants;
import com.saili.hzz.core.util.ApplicationContextUtil;

import java.util.List;

/***
 *
 * @author: whuab_mc
 * @date: 2019-09-23 18:38:18:38
 * @version: V1.0
 */
public class DivisionUtils {

    public static List<TSLDivisionEntity> listByCity() {
        DivisionService divisionService = ApplicationContextUtil.getContext().getBean(DivisionService.class);
        return divisionService.listByParentCode(Constants.ROOT_DIVISION_CODE);
    }
}
