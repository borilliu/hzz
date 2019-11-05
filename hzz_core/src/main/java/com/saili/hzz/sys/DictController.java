package com.saili.hzz.sys;

import com.saili.hzz.web.system.pojo.base.DictEntity;
import com.saili.hzz.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/***
 * @Title:
 * @Description:
 * @author: whuab_mc
 * @date: 2019-09-02 14:08:14:08
 * @version: V1.0
 */
@Controller
@RequestMapping("/sys/dict/")
public class DictController {
    @Autowired
    private SystemService systemService;

    /**
     * 获取事件问题类型集
     * @author whuab_mc
     * @date 2019-08-02 08:40:13
     * @return
     */
    @RequestMapping(value = "getListEventType", method = RequestMethod.POST)
    @ResponseBody
    public List<DictEntity> getListEventType() {
        List<DictEntity> results = systemService.queryDict("", "questType", "");
        return results;
    }
}




