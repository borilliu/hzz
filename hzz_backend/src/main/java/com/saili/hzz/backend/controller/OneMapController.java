package com.saili.hzz.backend.controller;

import com.saili.hzz.core.entity.TSLDivisionEntity;
import com.saili.hzz.core.enums.RiverLakeTypeEnum;
import com.saili.hzz.backend.service.OneMapService;
import com.saili.hzz.core.vo.onemap.DrawRiverLakeDataVo;
import com.saili.hzz.core.vo.onemap.RiverInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/***
 * 一张图 controller
 * @author: whuab_mc
 * @date: 2019-10-18 08:53:8:53
 * @version: V1.0
 */
@Controller
@RequestMapping("/oneMap")
public class OneMapController {
    @Autowired
    private OneMapService oneMapService;

    @RequestMapping("/goHome")
    public String goHome() {
        return "modules/hzz/onemap/one_map";
    }

    @RequestMapping(value = "/loadControl", method = {RequestMethod.GET, RequestMethod.POST})
    public String loadControl() {
        return "/modules/hzz/onemap/control";
    }

    @RequestMapping(value = "/loadInfoWindows", method = {RequestMethod.GET, RequestMethod.POST})
    public String loadInfoWindows(Double publicSignsLng, Double publicSignsLat, Model model) {
        RiverInfoVo vo = oneMapService.getRiverInfo(publicSignsLng, publicSignsLat);
        String riverLakeTypeEnme = RiverLakeTypeEnum.getEnameByCode(vo.getRiverLake().getRiverLakeType());
        model.addAttribute("riverInfoVo", vo);
        return "/modules/hzz/onemap/"+ riverLakeTypeEnme +"_info_windows";
    }

    @RequestMapping("/listDrawRiverLakeData")
    @ResponseBody
    public List<DrawRiverLakeDataVo> listDrawRiverLakeData(TSLDivisionEntity division) {
        return oneMapService.listDrawRiverLakeData(division);
    }
}
