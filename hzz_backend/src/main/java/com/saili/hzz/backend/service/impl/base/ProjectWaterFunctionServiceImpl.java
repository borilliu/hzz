package com.saili.hzz.backend.service.impl.base;

import com.saili.hzz.core.dao.mybatisdao.base.ProjectWaterFunctionDao;
import com.saili.hzz.core.entity.TSLRLProjectWaterFunction;
import com.saili.hzz.backend.service.base.ProjectWaterFunctionService;
import com.saili.hzz.core.common.model.json.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectWaterFunctionServiceImpl implements ProjectWaterFunctionService {

    @Autowired
    private ProjectWaterFunctionDao projectWaterFunctionDao;

    @Override
    public List<TSLRLProjectWaterFunction> listNoRefProjectWaterFunctionQuality(TSLRLProjectWaterFunction projectWaterFunction, DataGrid dataGrid) {
        List<TSLRLProjectWaterFunction> results = projectWaterFunctionDao.listNoRefProjectWaterFunctionQuality(projectWaterFunction.getId());
        dataGrid.setResults(results);
        dataGrid.setTotal(results.size());
        return results;
    }
}
