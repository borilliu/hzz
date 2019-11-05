package com.saili.hzz.gzts.controller;

import com.saili.hzz.core.entity.TSLBaseRiverLakePatrolEvent;
import com.saili.hzz.gzts.service.GztsService;
import org.apache.commons.lang.StringUtils;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.web.system.pojo.base.DictEntity;
import com.saili.hzz.web.system.service.MutiLangServiceI;
import com.saili.hzz.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/***
 * @Title: 公众投诉 controller
 * @Description:
 * @author: whuab_mc
 * @date: 2019-09-03 10:06:10:06
 * @version: V1.0
 */
@Controller
@RequestMapping("/gzts/")
public class GztsController {
    private static final Logger log = LoggerFactory.getLogger(GztsController.class);

    @Autowired
    private GztsService gztsService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private MutiLangServiceI mutiLangService;

    /**
     * 获取事件问题类型集
     *
     * @return
     * @author whuab_mc
     * @date 2019-08-02 08:40:13
     */
    @RequestMapping(value = "getListEventType", method = RequestMethod.POST)
    @ResponseBody
    public List<DictEntity> getListEventType() {
        List<DictEntity> results = systemService.queryDict("", "questType", "");
        return results;
    }

    /**
     * 保存公众投诉信息
     *
     * @param event
     * @return
     * @author whuab_mc
     * @date 2019-09-04 14:36:33
     */
    @RequestMapping("save")
    @ResponseBody
    public AjaxJson save(TSLBaseRiverLakePatrolEvent event, HttpServletRequest request) {
        AjaxJson result = new AjaxJson();
        HttpSession session = request.getSession();
        //验证码
        String randCode = request.getParameter("randCode");
        if (StringUtils.isEmpty(randCode)) {
            result.setMsg(mutiLangService.getLang("common.enter.verifycode"));
            result.setSuccess(false);
            return result;
        } else if (!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute("randCode")))) {
            result.setMsg(mutiLangService.getLang("common.verifycode.error"));
            log.info("验证码: {} 错误!", randCode);
            result.setSuccess(false);
            return result;
        }

        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multiRequest.getFiles("file");
        String rootContextPath = request.getSession().getServletContext().getRealPath("/");
        // TODO: 需要根据经纬度设置event（TSLBaseRiverLakePatrolEvent）的地区信息（tslDivision.code）
        boolean isSuccess = gztsService.uploadFile(files, rootContextPath, event);
        result.setSuccess(isSuccess);
        if (isSuccess) {
            result.setMsg("投诉成功！");
        } else {
            result.setMsg("投诉失败！");
        }
        return result;
    }
}
