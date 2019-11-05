package com.saili.hzz.backend.service.base;

import com.saili.hzz.core.entity.TSLRLProjectWaterFunction;
import com.saili.hzz.core.common.model.json.DataGrid;

import java.util.List;

public interface ProjectWaterFunctionService {
    List<TSLRLProjectWaterFunction> listNoRefProjectWaterFunctionQuality(TSLRLProjectWaterFunction projectWaterFunction, DataGrid dataGrid);
}
