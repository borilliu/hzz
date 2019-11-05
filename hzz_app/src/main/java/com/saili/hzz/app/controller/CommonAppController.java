package com.saili.hzz.app.controller;

import com.saili.hzz.core.common.controller.BaseController;
import com.saili.hzz.web.system.pojo.base.DictEntity;
import com.saili.hzz.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * @Title: Restful
 * @Description: 公共 app接口
 * @author: whuab_mc
 * @date: 2019-08-01 14:54:14:54
 * @version: V1.0
 */
@Controller
@RequestMapping("/app/common/")
public class CommonAppController extends BaseController {
    @Autowired
    private SystemService systemService;

    /**
     * 获取事件问题类型集
     * @author whuab_mc
     * @date 2019-08-02 08:40:13
     * @return
     */
    @RequestMapping("getListEventType")
    @ResponseBody
    public List<DictEntity> getListEventType() {
        List<DictEntity> results = systemService.queryDict("", "questType", "");
        return results;
    }
}
