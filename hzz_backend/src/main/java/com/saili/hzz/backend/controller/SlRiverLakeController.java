package com.saili.hzz.backend.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saili.hzz.core.extend.hqlsearch.HqlGenerateUtil;
import com.saili.hzz.core.entity.*;
import com.saili.hzz.core.enums.RiverLakeTypeEnum;
import com.saili.hzz.backend.service.base.ProjectWaterFunctionService;
import com.saili.hzz.backend.service.base.RiverLakeChiefService;
import com.saili.hzz.backend.service.RiverLakeService;
import com.saili.hzz.backend.service.TestDataSevice;
import org.apache.commons.lang.StringUtils;
import com.saili.hzz.core.common.controller.BaseController;
import com.saili.hzz.core.common.exception.BusinessException;
import com.saili.hzz.core.common.hibernate.qbc.CriteriaQuery;
import com.saili.hzz.core.common.model.json.AjaxJson;
import com.saili.hzz.core.common.model.json.ComboTree;
import com.saili.hzz.core.common.model.json.DataGrid;
import com.saili.hzz.core.common.model.json.ValidForm;
import com.saili.hzz.core.constant.Globals;
import com.saili.hzz.core.util.DateUtils;
import com.saili.hzz.core.util.FileUtils;
import com.saili.hzz.core.util.MutiLangUtil;
import com.saili.hzz.core.util.MyBeanUtils;
import com.saili.hzz.core.util.MyClassLoader;
import com.saili.hzz.core.util.ReflectHelper;
import com.saili.hzz.core.util.ResourceUtil;
import com.saili.hzz.core.util.StringUtil;
import com.saili.hzz.core.util.oConvertUtils;
import com.saili.hzz.jwt.util.ResponseMessage;
import com.saili.hzz.jwt.util.Result;
import com.saili.hzz.tag.core.easyui.TagUtil;
import com.saili.hzz.tag.vo.datatable.SortDirection;
import com.saili.hzz.tag.vo.easyui.ComboTreeModel;
import com.saili.hzz.web.system.pojo.base.TSDepart;
import com.saili.hzz.web.system.pojo.base.TSType;
import com.saili.hzz.web.system.pojo.base.TSUser;
import com.saili.hzz.web.system.pojo.base.TSUserOrg;
import com.saili.hzz.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
15429787	
sl

百度地图api==key
7aLuDD5cNosFGfxoyzrWlKZwbA6oAzFK
浏览器端
 * @Title: Controller
 * @Description: 河流名录管理
 * @author Liuby
 * @date 2014-09-16 21:50:55
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/slRiverLakeController")
public class SlRiverLakeController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(SlRiverLakeController.class);

	@Autowired
	private SystemService systemService;
	@Autowired
	private TestDataSevice testDataSevice;
	@Autowired
	private RiverLakeChiefService riverLakeChiefServiceI;
	@Autowired
	private ProjectWaterFunctionService projectWaterFunctionService;
	@Autowired
    private RiverLakeService riverLakeService;

	@RequestMapping("/listRiverlakeByDivsion")
	@ResponseBody
	public List<TSLBaseRiverLake> listRiverLakeByDivision(TSLDivisionEntity division) {
		return riverLakeService.listByDivision(division);
	}


    @RequestMapping(value = "checkRiverLakeCode")
    @ResponseBody
    public ValidForm checkRiverLakeCode(String param, String type, HttpServletRequest request) {
        type = oConvertUtils.getString(type);
        String code = oConvertUtils.getString(param);
        return riverLakeService.checkRiverLakeCode(type, code);
    }

	@RequestMapping("/get_json")
	public void commonGetJson(@RequestBody String json,HttpServletRequest request,HttpServletResponse response){
	    System.out.println(json);
	    // 输出json格式字符串
	    // {"parameter":{"userName":"17737190001","userPass":"6e316845103d21d700aceecdba7eadae"},"version":100,"opcode":"U00006"}
	}
	
	/**
	 * 总列表 页面跳转
	 * 
	 * @return
	 */
	private static final String CONTROLLER_LIST = "modules/hzz/slRiverLake/slRiverLakeList";
	@RequestMapping(params = "list1")
	public String list1(HttpServletRequest request) {
//		TestUser user = testDataSevice.get("8a8ab0b246dc81120146dc81819d0053");
		//10河湖,11河湖工程
		request.setAttribute("paramType", "10");
		return CONTROLLER_LIST;
	}
	@RequestMapping(params = "list2")
	public String list2(HttpServletRequest request) {
		//10河湖,11河湖工程
		request.setAttribute("paramType", "11"); 
		return CONTROLLER_LIST;
	}
	/**
	 * 每一种的列表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "listEntity")
	public String listEntity(HttpServletRequest request) {
		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
		return CONTROLLER_LIST+"Entity";
	}
	
	
	@RequestMapping(params = "datagridEntity")
	public void datagridEntity(TSLBaseRiverLake entity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
		
		CriteriaQuery cq=null;
		if ("a".equals(paramType)){
			cq = new CriteriaQuery(TSLRLRiverEntity.class, dataGrid);
		}
		if ("b".equals(paramType)){
			cq = new CriteriaQuery(TSLRLLakeEntity.class, dataGrid);
		}
		if ("c".equals(paramType)){
			cq = new CriteriaQuery(TSLRLReachEntity.class, dataGrid);
		}
		if ("d".equals(paramType)){
			cq = new CriteriaQuery(TSLRLReservoirEntity.class, dataGrid);
		}
		if ("a1".equals(paramType)){
			cq = new CriteriaQuery(TSLRLProjectIntakeEntity.class, dataGrid);
		}
		if ("b1".equals(paramType)){
			cq = new CriteriaQuery(TSLRLProjectWaterFunction.class, dataGrid);
		}
		if ("c1".equals(paramType)){
			cq = new CriteriaQuery(TSLRLProjectSurveyStation.class, dataGrid);
		}
		if ("d1".equals(paramType)){
			cq = new CriteriaQuery(TSLRLProjectSewageOutlet.class, dataGrid);
		}
		
		//查询条件组装器
        HqlGenerateUtil.installHql(cq, entity);
        
        if ("a".equals(paramType)){
        	cq.eq("riverLakeType", RiverLakeTypeEnum.RIVER.getCode());
        }
        if ("b".equals(paramType)){
        	cq.eq("riverLakeType", RiverLakeTypeEnum.LAKE.getCode());
        }
        if ("c".equals(paramType)){
        	cq.eq("riverLakeType", RiverLakeTypeEnum.REACH.getCode());
        }
        if ("d".equals(paramType)){
        	cq.eq("riverLakeType", RiverLakeTypeEnum.RESERVOIR.getCode());
        }
        if ("a1".equals(paramType)){
        	cq.eq("riverLakeType", RiverLakeTypeEnum.INTAKE.getCode());
        }
        if ("b1".equals(paramType)){
        	cq.eq("riverLakeType", RiverLakeTypeEnum.WATER_FUNCTION.getCode());
        }
        if ("c1".equals(paramType)){
        	cq.eq("riverLakeType", RiverLakeTypeEnum.SURVEY_STATION.getCode());
        }
        if ("d1".equals(paramType)){
        	cq.eq("riverLakeType", RiverLakeTypeEnum.SEWAGE_OUTLET.getCode());
        }

        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        
        this.systemService.getDataGridReturn(cq, true);
        
         for (Object o : dataGrid.getResults()) {
        	if (o instanceof TSLBaseRiverLake) {
        		TSLBaseRiverLake cfe = (TSLBaseRiverLake) o;
        		if (cfe.getId() != null && !"".equals(cfe.getId())) {
        			
        			//List<TSLBaseRiverLakeUser> roleUser = systemService.findByProperty(TSLBaseRiverLakeUser.class, "tslBaseRiverLake.id", cfe.getId());
        			List<TSLBaseRiverLakeUser> roleUser =cfe.getBaseRiverLakeUserList();
        			if (roleUser.size() > 0) {
        				String roleName = "";
        				for (TSLBaseRiverLakeUser ru : roleUser) {
        					String userDutys=ru.getRiverLakeChief().getDuty();
        					if(StringUtils.isNotBlank(userDutys)){
        						roleName += ru.getRiverLakeChief().getName() +"("+userDutys+")"+ ",";
        					}else{
        						roleName += ru.getRiverLakeChief().getName() + ",";
        					}
                        }
                        roleName = roleName.substring(0, roleName.length() - 1);
                        cfe.setCreateName(roleName);
        			}
        		}
        	}
        }
        TagUtil.datagrid(response, dataGrid);
	}
	
	/** 增加或修改*/
	private static final String CONTROLLER_ADD_OR_UPDATE = "modules/hzz/slRiverLake/slRiverLake";
	@RequestMapping(params = "addorupdateEntity")
	public String addorupdateEntity(TSLBaseRiverLake entity,HttpServletRequest request) {
		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
		String id=entity.getId();
		if ("a".equals(paramType)){
			entity=new TSLRLRiverEntity();
		}
		if ("b".equals(paramType)){
			entity=new TSLRLLakeEntity();
		}
		if ("c".equals(paramType)){
			entity=new TSLRLReachEntity();
		}
		if ("d".equals(paramType)){
			entity=new TSLRLReservoirEntity();
		}
		if ("a1".equals(paramType)){
			entity=new TSLRLProjectIntakeEntity();
		}
		if ("b1".equals(paramType)){
			entity=new TSLRLProjectWaterFunction();
		}
		if ("c1".equals(paramType)){
			entity=new TSLRLProjectSurveyStation();
		}
		if ("d1".equals(paramType)){
			entity=new TSLRLProjectSewageOutlet();
		}
		entity.setId(id);
		if (StringUtil.isNotEmpty(entity.getId())) {
			if ("a".equals(paramType)){
				entity=systemService.get(TSLRLRiverEntity.class, entity.getId());	
			}
			if ("b".equals(paramType)){
				entity=systemService.get(TSLRLLakeEntity.class, entity.getId());
			}
			if ("c".equals(paramType)){
				entity=systemService.get(TSLRLReachEntity.class, entity.getId());
			}
			if ("d".equals(paramType)){
				entity=systemService.get(TSLRLReservoirEntity.class, entity.getId());
			}
			if ("a1".equals(paramType)){
				entity=systemService.get(TSLRLProjectIntakeEntity.class, entity.getId());	
			}
			if ("b1".equals(paramType)){
				entity=systemService.get(TSLRLProjectWaterFunction.class, entity.getId());	
			}
			if ("c1".equals(paramType)){
				entity=systemService.get(TSLRLProjectSurveyStation.class, entity.getId());	
			}
			if ("d1".equals(paramType)){
				entity=systemService.get(TSLRLProjectSewageOutlet.class, entity.getId());	
			}
			getBaseRiverLakeUserInfos(request, entity);
		}
		if (entity.getParent() != null
				&& StringUtil.isNotEmpty(entity.getParent().getCode())) {
			TSLBaseRiverLake parent = systemService.findUniqueByProperty(TSLBaseRiverLake.class, "code", entity.getParent().getCode());
			entity.setParent(parent);
		}
		request.setAttribute("entity", entity);
		return CONTROLLER_ADD_OR_UPDATE+"Entity";
	}
	
	public void getBaseRiverLakeUserInfos(HttpServletRequest request, TSLBaseRiverLake entity) {
		List<TSLBaseRiverLakeUser> tSUserOrgs = systemService.findByProperty(TSLBaseRiverLakeUser.class, "tslBaseRiverLake.id", entity.getId());
		String orgIds = "";
		String departname = "";
		if (tSUserOrgs.size() > 0) {
			for (TSLBaseRiverLakeUser tSUserOrg : tSUserOrgs) {
				orgIds += tSUserOrg.getRiverLakeChief().getId() + ",";
				String userDutys=tSUserOrg.getRiverLakeChief().getDuty();
				if(StringUtils.isNotBlank(userDutys)){
					departname += tSUserOrg.getRiverLakeChief().getName() +"("+userDutys+")"+ ",";
				}else{
					departname += tSUserOrg.getRiverLakeChief().getName() + ",";
				}
				
			}
		}
		request.setAttribute("hzids", orgIds);
		request.setAttribute("hzname", departname);
	}
	
	
	@RequestMapping(params = "saveEntity")
	@ResponseBody
	public AjaxJson saveEntity(TSLBaseRiverLake entity1, HttpServletRequest request) {
		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
		TSLBaseRiverLake entity=null;
		if ("a".equals(paramType)){
			entity=new TSLRLRiverEntity();
			try {
				MyBeanUtils.copyBeanNotNull2Bean(entity1, entity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			entity.setRiverLakeType(RiverLakeTypeEnum.RIVER.getCode());
			String riverType=oConvertUtils.getString(request.getParameter("riverType"));
			String riverLength=oConvertUtils.getString(request.getParameter("riverLength"));
			String startPositionName=oConvertUtils.getString(request.getParameter("startPositionName"));
			String endPositiomName=oConvertUtils.getString(request.getParameter("endPositiomName"));
			String riverDesc=oConvertUtils.getString(request.getParameter("riverDesc"));
			
			((TSLRLRiverEntity)entity).setRiverType(riverType);
			((TSLRLRiverEntity)entity).setRiverLength(Float.parseFloat(riverLength));
			((TSLRLRiverEntity)entity).setStartPositionName(startPositionName);
			((TSLRLRiverEntity)entity).setEndPositiomName(endPositiomName);
			((TSLRLRiverEntity)entity).setRiverDesc(riverDesc);
		}
		if ("b".equals(paramType)){
			entity=new TSLRLLakeEntity();
			try {
				MyBeanUtils.copyBeanNotNull2Bean(entity1, entity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			entity.setRiverLakeType(RiverLakeTypeEnum.LAKE.getCode());
			String saltyAttr=oConvertUtils.getString(request.getParameter("saltyAttr"));
			String meanDepth=oConvertUtils.getString(request.getParameter("meanDepth"));
			String lakeVolume=oConvertUtils.getString(request.getParameter("lakeVolume"));
			String maxWaterDepth=oConvertUtils.getString(request.getParameter("maxWaterDepth"));
			((TSLRLLakeEntity)entity).setSaltyAttr(saltyAttr);
			((TSLRLLakeEntity)entity).setMeanDepth(Float.parseFloat(meanDepth));
			((TSLRLLakeEntity)entity).setLakeVolume(Float.parseFloat(lakeVolume));
			((TSLRLLakeEntity)entity).setMaxWaterDepth(Float.parseFloat(maxWaterDepth));
		}
		if ("c".equals(paramType)){
			entity=new TSLRLReachEntity();
			try {
				MyBeanUtils.copyBeanNotNull2Bean(entity1, entity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			entity.setRiverLakeType(RiverLakeTypeEnum.REACH.getCode());
			String riverType=oConvertUtils.getString(request.getParameter("riverType"));
			String riverLength=oConvertUtils.getString(request.getParameter("riverLength"));
			String startPositionName=oConvertUtils.getString(request.getParameter("startPositionName"));
			String endPositiomName=oConvertUtils.getString(request.getParameter("endPositiomName"));
			String riverDesc=oConvertUtils.getString(request.getParameter("riverDesc"));
			
			((TSLRLReachEntity)entity).setRiverType(riverType);
			((TSLRLReachEntity)entity).setRiverLength(Float.parseFloat(riverLength));
			((TSLRLReachEntity)entity).setStartPositionName(startPositionName);
			((TSLRLReachEntity)entity).setEndPositiomName(endPositiomName);
			((TSLRLReachEntity)entity).setRiverDesc(riverDesc);
		}
		if ("d".equals(paramType)){
			entity=new TSLRLReservoirEntity();
			try {
				MyBeanUtils.copyBeanNotNull2Bean(entity1, entity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			entity.setRiverLakeType(RiverLakeTypeEnum.RESERVOIR.getCode());
			String reservoirType=oConvertUtils.getString(request.getParameter("reservoirType"));
			String engineeringGrade=oConvertUtils.getString(request.getParameter("engineeringGrade"));
			String engineeringScale=oConvertUtils.getString(request.getParameter("engineeringScale"));
			String reservoirGrade=oConvertUtils.getString(request.getParameter("reservoirGrade"));
			String highFloodLevel=oConvertUtils.getString(request.getParameter("highFloodLevel"));
			String normalWaterLevel=oConvertUtils.getString(request.getParameter("normalWaterLevel"));
			String floodPeriodLevel=oConvertUtils.getString(request.getParameter("floodPeriodLevel"));
			String totalCapacity=oConvertUtils.getString(request.getParameter("totalCapacity"));
			String floodCapacity=oConvertUtils.getString(request.getParameter("floodCapacity"));
			String floodStorage=oConvertUtils.getString(request.getParameter("floodStorage"));
			
			((TSLRLReservoirEntity)entity).setReservoirType(reservoirType);
			((TSLRLReservoirEntity)entity).setEngineeringGrade(engineeringGrade);
			((TSLRLReservoirEntity)entity).setEngineeringScale(engineeringScale);
			((TSLRLReservoirEntity)entity).setReservoirGrade(reservoirGrade);
			((TSLRLReservoirEntity)entity).setHighFloodLevel(Float.parseFloat(highFloodLevel));
			((TSLRLReservoirEntity)entity).setNormalWaterLevel(Float.parseFloat(normalWaterLevel));
			((TSLRLReservoirEntity)entity).setFloodPeriodLevel(Float.parseFloat(floodPeriodLevel));
			((TSLRLReservoirEntity)entity).setTotalCapacity(Float.parseFloat(totalCapacity));
			((TSLRLReservoirEntity)entity).setFloodCapacity(Float.parseFloat(floodCapacity));
			((TSLRLReservoirEntity)entity).setFloodStorage(Float.parseFloat(floodStorage));
		}
		if ("a1".equals(paramType)){
			entity=new TSLRLProjectIntakeEntity();
			try {
				MyBeanUtils.copyBeanNotNull2Bean(entity1, entity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			entity.setRiverLakeType(RiverLakeTypeEnum.INTAKE.getCode());
			String address=oConvertUtils.getString(request.getParameter("address"));
			String takeType=oConvertUtils.getString(request.getParameter("takeType"));
			String gateName=oConvertUtils.getString(request.getParameter("gateName"));
			String diversionProjectName=oConvertUtils.getString(request.getParameter("diversionProjectName"));
			String waterSourceSName=oConvertUtils.getString(request.getParameter("waterSourceSName"));
			String waterSourceType=oConvertUtils.getString(request.getParameter("waterSourceType"));
			String waterSourceName=oConvertUtils.getString(request.getParameter("waterSourceName"));
			
			((TSLRLProjectIntakeEntity)entity).setAddress(address);
			((TSLRLProjectIntakeEntity)entity).setTakeType(takeType);
			((TSLRLProjectIntakeEntity)entity).setGateName(gateName);
			((TSLRLProjectIntakeEntity)entity).setDiversionProjectName(diversionProjectName);
			((TSLRLProjectIntakeEntity)entity).setWaterSourceSName(waterSourceSName);
			((TSLRLProjectIntakeEntity)entity).setWaterSourceType(waterSourceType);
			((TSLRLProjectIntakeEntity)entity).setWaterSourceName(waterSourceName);
		}
		if ("b1".equals(paramType)){
			entity=new TSLRLProjectWaterFunction();
			try {
				MyBeanUtils.copyBeanNotNull2Bean(entity1, entity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			entity.setRiverLakeType(RiverLakeTypeEnum.WATER_FUNCTION.getCode());
			String primaryName=oConvertUtils.getString(request.getParameter("primaryName"));
			String secondaryName=oConvertUtils.getString(request.getParameter("secondaryName"));
			String useWaterType=oConvertUtils.getString(request.getParameter("useWaterType"));
			String waterFunctionLength=oConvertUtils.getString(request.getParameter("waterFunctionLength"));
			String waterQuality=oConvertUtils.getString(request.getParameter("waterQuality"));
			String startSection=oConvertUtils.getString(request.getParameter("startSection"));
			String endSection=oConvertUtils.getString(request.getParameter("endSection"));
			String monitoringSection=oConvertUtils.getString(request.getParameter("monitoringSection"));
			
			((TSLRLProjectWaterFunction)entity).setPrimaryName(primaryName);
			((TSLRLProjectWaterFunction)entity).setSecondaryName(secondaryName);
			((TSLRLProjectWaterFunction)entity).setUseWaterType(useWaterType);
			((TSLRLProjectWaterFunction)entity).setWaterFunctionLength(Float.parseFloat(waterFunctionLength));
			((TSLRLProjectWaterFunction)entity).setWaterQuality(waterQuality);
			((TSLRLProjectWaterFunction)entity).setStartSection(startSection);
			((TSLRLProjectWaterFunction)entity).setEndSection(endSection);
			((TSLRLProjectWaterFunction)entity).setMonitoringSection(monitoringSection);
		}
		if ("c1".equals(paramType)){
			entity=new TSLRLProjectSurveyStation();
			try {
				MyBeanUtils.copyBeanNotNull2Bean(entity1, entity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			entity.setRiverLakeType(RiverLakeTypeEnum.SURVEY_STATION.getCode());
			String location=oConvertUtils.getString(request.getParameter("location"));
			String stationYear=oConvertUtils.getString(request.getParameter("stationYear"));
			String stationType=oConvertUtils.getString(request.getParameter("stationType"));
			
			((TSLRLProjectSurveyStation)entity).setLocation(location);
			((TSLRLProjectSurveyStation)entity).setStationYear(stationYear);
			((TSLRLProjectSurveyStation)entity).setStationType(stationType);
		}
		if ("d1".equals(paramType)){
			entity=new TSLRLProjectSewageOutlet();
			try {
				MyBeanUtils.copyBeanNotNull2Bean(entity1, entity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			entity.setRiverLakeType(RiverLakeTypeEnum.SEWAGE_OUTLET.getCode());
			String location=oConvertUtils.getString(request.getParameter("location"));
			String sewageOutType=oConvertUtils.getString(request.getParameter("sewageOutType"));
			String riverEntryMode=oConvertUtils.getString(request.getParameter("riverEntryMode"));
			String emissionMode=oConvertUtils.getString(request.getParameter("emissionMode"));
			String intoPrimary=oConvertUtils.getString(request.getParameter("intoPrimary"));
			String intoSecondary=oConvertUtils.getString(request.getParameter("intoSecondary"));
			String sourcesSewageType=oConvertUtils.getString(request.getParameter("sourcesSewageType"));
			String pollutantDischarge=oConvertUtils.getString(request.getParameter("pollutantDischarge"));
			
			((TSLRLProjectSewageOutlet)entity).setLocation(location);
			((TSLRLProjectSewageOutlet)entity).setSewageOutType(sewageOutType);
			((TSLRLProjectSewageOutlet)entity).setRiverEntryMode(riverEntryMode);
			((TSLRLProjectSewageOutlet)entity).setEmissionMode(emissionMode);
			((TSLRLProjectSewageOutlet)entity).setIntoPrimary(intoPrimary);
			((TSLRLProjectSewageOutlet)entity).setIntoSecondary(intoSecondary);
			((TSLRLProjectSewageOutlet)entity).setSourcesSewageType(sourcesSewageType);
			((TSLRLProjectSewageOutlet)entity).setPollutantDischarge(Float.parseFloat(pollutantDischarge));
		}
		
		
		String hzids = oConvertUtils.getString(request.getParameter("hzids"));
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(entity.getId())) {
			j.setMsg("更新成功");
			try {
				if ("a".equals(paramType)){
					TSLRLRiverEntity t = systemService.get(TSLRLRiverEntity.class,entity.getId());
					MyBeanUtils.copyBeanNotNull2Bean(entity, t);
					systemService.saveRLRiver(t,hzids.split(","));
				}
				if ("b".equals(paramType)){
					TSLRLLakeEntity t = systemService.get(TSLRLLakeEntity.class,entity.getId());
					MyBeanUtils.copyBeanNotNull2Bean(entity, t);
					systemService.saveRLRiver(t,hzids.split(","));
				}
				if ("c".equals(paramType)){
					TSLRLReachEntity t = systemService.get(TSLRLReachEntity.class,entity.getId());
					MyBeanUtils.copyBeanNotNull2Bean(entity, t);
					systemService.saveRLRiver(t,hzids.split(","));
				}
				if ("d".equals(paramType)){
					TSLRLReservoirEntity t = systemService.get(TSLRLReservoirEntity.class,entity.getId());
					MyBeanUtils.copyBeanNotNull2Bean(entity, t);
					systemService.saveRLRiver(t,hzids.split(","));
				}
				if ("a1".equals(paramType)){
					TSLRLProjectIntakeEntity t = systemService.get(TSLRLProjectIntakeEntity.class,entity.getId());
					MyBeanUtils.copyBeanNotNull2Bean(entity, t);
					systemService.saveRLRiver(t,hzids.split(","));
				}
				if ("b1".equals(paramType)){
					TSLRLProjectWaterFunction t = systemService.get(TSLRLProjectWaterFunction.class,entity.getId());
					MyBeanUtils.copyBeanNotNull2Bean(entity, t);
					systemService.saveRLRiver(t,hzids.split(","));
				}
				if ("c1".equals(paramType)){
					TSLRLProjectSurveyStation t = systemService.get(TSLRLProjectSurveyStation.class,entity.getId());
					MyBeanUtils.copyBeanNotNull2Bean(entity, t);
					systemService.saveRLRiver(t,hzids.split(","));
				}
				if ("d1".equals(paramType)){
					TSLRLProjectSewageOutlet t = systemService.get(TSLRLProjectSewageOutlet.class,entity.getId());
					MyBeanUtils.copyBeanNotNull2Bean(entity, t);
					systemService.saveRLRiver(t,hzids.split(","));
				}
				systemService.addLog(j.getMsg(), Globals.Log_Type_UPDATE,
						Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				logger.error(e.getMessage(), e.fillInStackTrace());
				j.setMsg("更新失败");
			}
		} else {
			j.setMsg("添加成功");
			
			systemService.saveRLRiver(entity,hzids.split(","));
			systemService.addLog(j.getMsg(), Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		}
		

		//防止多级报错
		if (entity!=null){
			try {
				TSLRLRiverEntity t =new TSLRLRiverEntity();
				MyBeanUtils.copyBeanNotNull2Bean(entity, t);
				t.setBaseRiverLakeUserList(null);
				t.setTSLDivision(null);
				t.setList(null);
				t.setParent(null);
				j.setObj(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return j;
	}
	
	@RequestMapping(params = "delEntity")
	@ResponseBody
	public AjaxJson delEntity(TSLBaseRiverLake entity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
		if ("a".equals(paramType)){
			entity = systemService.getEntity(TSLRLRiverEntity.class,
					entity.getId());
			systemService.delete(entity);
		}
		if ("b".equals(paramType)){
			entity = systemService.getEntity(TSLRLLakeEntity.class,
					entity.getId());
			systemService.delete(entity);
		}
		if ("c".equals(paramType)){
			entity = systemService.getEntity(TSLRLReachEntity.class,
					entity.getId());
			systemService.delete(entity);
		}
		if ("d".equals(paramType)){
			entity = systemService.getEntity(TSLRLReservoirEntity.class,
					entity.getId());
			systemService.delete(entity);
		}
		if ("a1".equals(paramType)){
			entity = systemService.getEntity(TSLRLProjectIntakeEntity.class,
					entity.getId());
			systemService.delete(entity);
		}
		if ("b1".equals(paramType)){
			entity = systemService.getEntity(TSLRLProjectWaterFunction.class,
					entity.getId());
			systemService.delete(entity);
		}
		if ("c1".equals(paramType)){
			entity = systemService.getEntity(TSLRLProjectSurveyStation.class,
					entity.getId());
			systemService.delete(entity);
		}
		if ("d1".equals(paramType)){
			entity = systemService.getEntity(TSLRLProjectSewageOutlet.class,
					entity.getId());
			systemService.delete(entity);
		}
		j.setMsg("删除成功");
		systemService.addLog(j.getMsg(), Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		return j;
	}
	
	
	@RequestMapping(params = "combotree")
	@ResponseBody
	public List<ComboTree> combotree(String selfCode, ComboTree comboTree,HttpServletRequest request) {
		CriteriaQuery cq = new CriteriaQuery(TSLBaseRiverLake.class);
		
		String param_river_lake_type=oConvertUtils.getString(request.getParameter("param_river_lake_type"));
		request.setAttribute("param_river_lake_type", param_river_lake_type);
		String param_id=oConvertUtils.getString(comboTree.getId());
		if (StringUtils.isEmpty(param_id)){
			cq.eq("riverLakeType", "00");
			cq.add();
			List<TSLBaseRiverLake> categoryList = systemService
					.getListByCriteriaQuery(cq, false);
			
			if (param_river_lake_type.indexOf("10_")>=0){
				TSLBaseRiverLake defaultEntity = new TSLBaseRiverLake();
				defaultEntity.setCode("10");
				defaultEntity.setName("河流");
				
				CriteriaQuery cq1 = new CriteriaQuery(TSLBaseRiverLake.class);
				cq1.eq("riverLakeType", "10");
				cq1.add();
				List<TSLBaseRiverLake> entityList = systemService
						.getListByCriteriaQuery(cq1, false);
				defaultEntity.setList(entityList);
				categoryList.add(defaultEntity);
			}
			if (param_river_lake_type.indexOf("11_")>=0){
				TSLBaseRiverLake defaultEntity = new TSLBaseRiverLake();
				defaultEntity.setCode("11");
				defaultEntity.setName("湖泊");
				CriteriaQuery cq1 = new CriteriaQuery(TSLBaseRiverLake.class);
				cq1.eq("riverLakeType", "11");
				cq1.add();
				List<TSLBaseRiverLake> entityList = systemService
						.getListByCriteriaQuery(cq1, false);
				defaultEntity.setList(entityList);
				categoryList.add(defaultEntity);
			}
			TSLBaseRiverLake defaultEntity = new TSLBaseRiverLake();
			defaultEntity.setCode("");
			defaultEntity.setName("请选择河湖");
			categoryList.add(0, defaultEntity);
			List<ComboTree> comboTrees = new ArrayList<ComboTree>();
			ComboTreeModel comboTreeModel = new ComboTreeModel("code", "name", "list");
			comboTrees = systemService.ComboTree(categoryList, comboTreeModel,
					null, false);
			MutiLangUtil.setMutiTree(comboTrees);
			return comboTrees;
		}
			
		
		
		if (StringUtils.isNotEmpty(param_id)) {
			if ("1".equals(param_id.substring(0,1))){
				cq.eq("riverLakeType", param_id);
				//cq.isNull("parent");
				//cq.add(Restrictions.sqlRestriction(" (this_.PARENT_CODE is null or length(this_.PARENT_CODE)=0)  "));
				cq.sql(" (this_.PARENT_CODE is null or length(this_.PARENT_CODE)=0)  ");
			}else{
				cq.createAlias("parent", "parent");
				cq.eq("parent.code", param_id);
			}
		} else if (StringUtils.isNotEmpty(selfCode)) {
			cq.eq("code", selfCode);
		} else {
			cq.isNull("parent");
		}
		cq.add();
		List<TSLBaseRiverLake> categoryList = systemService
				.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("code", "name", "list");
		comboTrees = systemService.ComboTree(categoryList, comboTreeModel,
				null, false);
		MutiLangUtil.setMutiTree(comboTrees);
		return comboTrees;
	}
	/** 地图资料查询*/
	private static final String MapDetail = "modules/hzz/slRiverLake/slRiverMapDetail";
    @RequestMapping(params = "mapDetail")
    public String mapDetail(HttpServletRequest request) {
    	String paramType=oConvertUtils.getString(request.getParameter("paramType"));
    	if (StringUtils.isEmpty(paramType)) {
    		paramType = "10";
		}
		request.setAttribute("paramType", paramType);
        return MapDetail;
    }
    /** 河流地图显示*/
    private static final String MapDetail1 = "modules/hzz/slRiverLake/slRiverMapDetail1";
    @RequestMapping(params = "mapDetail1")
    public String mapDetail1(TSLBaseRiverLake entity,HttpServletRequest request) {
    	String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
		if (StringUtil.isNotEmpty(entity.getId())) {
			entity=systemService.get(TSLBaseRiverLake.class, entity.getId());
			//map.put("entity", entity);
			request.setAttribute("entity", entity);
			getBaseRiverLakeUserInfos(request, entity);
		}
        return MapDetail1;
    }
    /** 图片地图显示*/
    private static final String MapDetail2 = "modules/hzz/slRiverLake/slRiverMapDetail2";
    @RequestMapping(params = "mapDetail2")
    public String mapDetail2(HttpServletRequest request) {
    	String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
    	String photoLat=oConvertUtils.getString(request.getParameter("photoLat"));
		request.setAttribute("photoLat", photoLat);
		String photoLng=oConvertUtils.getString(request.getParameter("photoLng"));
		request.setAttribute("photoLng", photoLng);
        return MapDetail2;
    }
    
    /** 巡河列表外壳*/
	private static final String patrolList = "modules/hzz/slRiverLake/slRiverPatrolList";
    @RequestMapping(params = "patrolList")
    public String patrolList(HttpServletRequest req) {
        return patrolList;
    }
    /** 巡河信息列表 */
    private static final String patrolList1 = "modules/hzz/slRiverLake/slRiverPatrolList1";
    @RequestMapping(params = "patrolList1")
    public String patrolList1(HttpServletRequest req) {
        return patrolList1;
    }
    
    @RequestMapping(params = "patrolDataGrid")
	public void patrolDataGrid(TSLBaseRiverLakePatrol entity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
		CriteriaQuery cq = new CriteriaQuery(TSLBaseRiverLakePatrol.class, dataGrid);
		//查询条件组装器
        HqlGenerateUtil.installHql(cq, entity);
        
        this.systemService.getDataGridReturn(cq, true);
        
        TagUtil.datagrid(response, dataGrid);
	}
    
    
    private static final String patrolList20 = "modules/hzz/slRiverLake/slRiverPatrolList20";
    @RequestMapping(params = "patrolList20")
    public String patrolList20(HttpServletRequest req) {
        return patrolList20;
    }
    
    
    @RequestMapping(params = "patrolDataGrid20")
	public void patrolDataGrid20(TSLBaseRiverLakePatrolPhoto entity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
		
		String mainId=oConvertUtils.getString(request.getParameter("mainId"));
		String rowId=oConvertUtils.getString(request.getParameter("rowId"));
		if(oConvertUtils.isNotEmpty(mainId)){
			CriteriaQuery cq = new CriteriaQuery(TSLBaseRiverLakePatrolPhoto.class, dataGrid);
			//查询条件组装器
	        HqlGenerateUtil.installHql(cq, entity);
	        
	        cq.eq("tslBaseRiverLakePatrol.id", mainId);
	        cq.add();
	        this.systemService.getDataGridReturn(cq, true);	
	        TagUtil.datagrid(response, dataGrid);
		}else if(oConvertUtils.isNotEmpty(rowId)){
			dataGrid.setField("photo");
	        List<Map<String,Object>> maplist=systemService.findForJdbc("select photo from t_sl_base_river_lake_patrol_photo where base_river_lake_patrol_id=(select base_river_lake_patrol_id from t_sl_base_river_lake_patrol_photo where id='"+rowId+"')", null);
	        dataGrid.setTotal(maplist.size());
			dataGrid.setResults(maplist);
			TagUtil.datagrid(response, dataGrid);
		}
	}
    
    
    private static final String patrolList21 = "modules/hzz/slRiverLake/slRiverPatrolList21";
    @RequestMapping(params = "patrolList21")
    public String patrolList21(HttpServletRequest req) {
    	String mainId=oConvertUtils.getString(req.getParameter("mainId"));
    	req.setAttribute("mainId", mainId);
        return patrolList21;
    }
    
    @RequestMapping(params = "patrolDataGrid21")
   	public void patrolDataGrid21(TSLBaseRiverLakePatrolGPS entity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
   		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
   		request.setAttribute("paramType", paramType);
   		
   		String mainId=oConvertUtils.getString(request.getParameter("mainId"));
   		if(oConvertUtils.isNotEmpty(mainId)){
   			CriteriaQuery cq = new CriteriaQuery(TSLBaseRiverLakePatrolGPS.class, dataGrid);
   			//查询条件组装器
   	        HqlGenerateUtil.installHql(cq, entity);
   	        
   	        cq.eq("tslBaseRiverLakePatrol.id", mainId);
   	        cq.add();
   	        this.systemService.getDataGridReturn(cq, true);	
   	        TagUtil.datagrid(response, dataGrid);
   		}
   	}
    
    private static final String patrolList22 = "modules/hzz/slRiverLake/slRiverPatrolList22";
    @RequestMapping(params = "patrolList22")
    public String patrolList22(HttpServletRequest req) {
    	String mainId=oConvertUtils.getString(req.getParameter("mainId"));
    	req.setAttribute("mainId", mainId);
        return patrolList22;
    }
	
    @RequestMapping(params = "patrolDataGrid22")
   	public void patrolDataGrid22(TSLBaseRiverLakePatrolEvent entity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
   		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
   		request.setAttribute("paramType", paramType);
   		
   		String mainId=oConvertUtils.getString(request.getParameter("mainId"));
   		if(oConvertUtils.isNotEmpty(mainId)){
   			CriteriaQuery cq = new CriteriaQuery(TSLBaseRiverLakePatrolEvent.class, dataGrid);
   			//查询条件组装器
   	        HqlGenerateUtil.installHql(cq, entity);
   	        
   	        cq.eq("tslBaseRiverLakePatrol.id", mainId);
   	        cq.eq("sourceType", "10");
   	        cq.add();
   	        this.systemService.getDataGridReturn(cq, true);	
			for (Object o : dataGrid.getResults()) {
				if (o instanceof TSLBaseRiverLakePatrolEvent) {
					TSLBaseRiverLakePatrolEvent cfe = (TSLBaseRiverLakePatrolEvent) o;
					List<TSLBaseRiverLakePatrolEventPhoto> ss=cfe.getListEventPhoto();
					JSONObject joall = new JSONObject();
					JSONArray json = new JSONArray();
					for (int i = 0; i < ss.size(); i++) {
						String stphoto=ss.get(i).getPhoto();
						JSONObject jo = new JSONObject();
		                jo.put("Photo",stphoto);
						json.add(jo);
					}
					joall.put("EventPhoto", json);
					List<TSLBaseRiverLakePatrolEventPhoto1> ss1=cfe.getListEventPhoto1();
					JSONArray json1 = new JSONArray();
					for (int i = 0; i < ss1.size(); i++) {
						String stphoto=ss1.get(i).getPhoto();
						JSONObject jo = new JSONObject();
		                jo.put("Photo",stphoto);
						json1.add(jo);
					}
					joall.put("EventPhoto1", json1);
					List<TSLBaseRiverLakePatrolEventProcess> ss2=cfe.getListEventProcess();
					JSONArray json2 = new JSONArray();
					for (int i = 0; i < ss2.size(); i++) {
						java.util.Date processDate=ss2.get(i).getProcessDate();
						String stmemo=ss2.get(i).getProcessMemo();
						JSONObject jo = new JSONObject();
		                jo.put("Memo",stmemo);
		                jo.put("processDate",DateUtils.date2Str(processDate, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		                json2.add(jo);
					}
					joall.put("EventProcess", json2);
					ResourceUtil.getCacheTypesByDictionary("eventStat",cfe.getEventStatus(),joall);
					ResourceUtil.getCacheTypesByDictionary("questType",cfe.getQuestType(),joall);
					TSUser tsSubmitUser=cfe.getTsSubmitUser();
					if (tsSubmitUser!=null){
						String RealName="";
						String userDutys=tsSubmitUser.getUserDutys();
    					if(StringUtils.isNotBlank(userDutys)){
    						RealName += tsSubmitUser.getRealName() +"("+userDutys+")";
    					}else{
    						RealName += tsSubmitUser.getRealName();	
    					}
    					joall.put("userName",RealName);
					}
					
					String jsonStr=joall.toString();
					cfe.setTempName(jsonStr);
				}
			}
   	        TagUtil.datagrid(response, dataGrid);
   		}
   	}
	/**
     * 用户选择机构列表跳转页面
     *
     * @return
     */
	private static final String RiverChargeSelect = "modules/hzz/slRiverLake/slRiverChargeSelect";
    @RequestMapping(params = "riverChargeSelect")
    public String riverChargeSelect(HttpServletRequest req) {
    	req.setAttribute("orgIds", req.getParameter("orgIds"));
    	//是否选中部门YN
    	String ifcheckdept=oConvertUtils.getString(req.getParameter("ifcheckdept"));
    	if ("Y".equals(ifcheckdept)||"y".equals(ifcheckdept)){
    		ifcheckdept="Y";
    	}else{
    		ifcheckdept="N";
    	}
    	req.setAttribute("ifcheckdept", ifcheckdept);
        return RiverChargeSelect;
    }
    
    //这个是按部门来找人的
    @RequestMapping(params = "getRiverChargeInfo")
	@ResponseBody
	public AjaxJson getRiverChargeInfo(HttpServletRequest request, HttpServletResponse response){
    	//是否选中部门YN
    	String ifcheckdept=oConvertUtils.getString(request.getParameter("ifcheckdept"));
    	if ("Y".equals(ifcheckdept)||"y".equals(ifcheckdept)){
    		ifcheckdept="Y";
    	}else{
    		ifcheckdept="N";
    	}
    	
		AjaxJson j = new AjaxJson();
		String orgIds = request.getParameter("orgIds");
		String[] ids = new String[]{}; 
		if(StringUtils.isNotBlank(orgIds)){
			orgIds = orgIds.substring(0, orgIds.length()-1);
			ids = orgIds.split("\\,");
		}
		String currId = request.getParameter("currId");
		String currCode = request.getParameter("currCode");
		TSDepart parentDepart=null;
		List<TSDepart> domains = new ArrayList<TSDepart>();
		
		StringBuffer hql = new StringBuffer(" from TSDepart t where 1=1 ");
		if(StringUtils.isNotBlank(currCode)){
			parentDepart =systemService.findUniqueByProperty(TSDepart.class, "orgCode", currCode);
			hql.append(" and TSPDepart = ?");
			domains = this.systemService.findHql(hql.toString(), parentDepart);
		} else {
			hql.append(" and t.TSPDepart is null");
			domains = this.systemService.findHql(hql.toString());
		}
		String sql = null;
		Map<String,Object> map = null;
		List<Map<String,Object>> dateList = new ArrayList<Map<String,Object>>();
		if(StringUtils.isNotBlank(currId)){
			List<RiverLakeChiefEntity> riverLakeChiefList = riverLakeChiefServiceI.listRiverLakeChiefs(currId);
			for(RiverLakeChiefEntity riverLakeChief : riverLakeChiefList){
				map = new HashMap<String,Object>();
				map.put("id", riverLakeChief.getId());
				String userDutys = riverLakeChief.getDuty();
				if(StringUtils.isNotBlank(userDutys)){
					map.put("name", riverLakeChief.getName()+"("+userDutys+")");
				}else{
					map.put("name", riverLakeChief.getName());
				}
				
				map.put("chkDisabled", false);
				if(ids.length>0){
					for(String id:ids){
						if(id.equals(riverLakeChief.getAccount().getId())){
							map.put("checked", true);
						}
					}
				}
				map.put("pId", currId);
				map.put("isParent",false);
				dateList.add(map);
			}
		}
		if(domains.size()>0){
			map = null;
			sql = null;
			Object[] params = null;
			for(TSDepart domain:domains){
				map = new HashMap<String,Object>();
				map.put("id", domain.getId());
				map.put("name", domain.getDepartname());

				map.put("code",domain.getOrgCode());
				//map.put("chkDisabled", true);
				if ("N".equals(ifcheckdept)){
					map.put("chkDisabled", true);	
				}else{
					map.put("chkDisabled", false);
				}
				

				if(ids.length>0){
					for(String id:ids){
						if(id.equals(domain.getId())){
							map.put("checked", true);
						}
					}
				}
				
				if(StringUtils.isNotBlank(currId)){
					map.put("pId", currId);
				} else{
					map.put("pId", "1");
				}
				//根据id判断是否有子节点
				//sql = "select count(1) from t_sl_division t where t.parent_code = ?";
				//params = new Object[]{domain.getDivisionCode()};
				//long count = this.systemService.getCountForJdbcParam(sql, params);
				//if(count>0){
					//map.put("isParent",true);
				//}
				map.put("isParent",true);
				dateList.add(map);
			}
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(dateList);
		j.setMsg(jsonArray.toString());
		return j;
	}
    
    //这个是按行政区划来找人的
    @RequestMapping(params = "getRiverChargeInfoBak")
	@ResponseBody
	public AjaxJson getRiverChargeInfoBak(HttpServletRequest request, HttpServletResponse response){
		
		AjaxJson j = new AjaxJson();
		
		String orgIds = request.getParameter("orgIds");
		
		String[] ids = new String[]{}; 
		if(StringUtils.isNotBlank(orgIds)){
			orgIds = orgIds.substring(0, orgIds.length()-1);
			ids = orgIds.split("\\,");
		}
		
		String parentid = request.getParameter("parentid");
		
		List<TSLDivisionEntity> domains = new ArrayList<TSLDivisionEntity>();
		
		StringBuffer hql = new StringBuffer(" from TSLDivisionEntity t where 1=1 ");
		if(StringUtils.isNotBlank(parentid)){
			
			//TSLDivisionEntity dePart = this.systemService.getEntity(TSLDivisionEntity.class, parentid);
			TSLDivisionEntity dePart =systemService.findUniqueByProperty(TSLDivisionEntity.class, "code", parentid);
			hql.append(" and parent = ?");
			domains = this.systemService.findHql(hql.toString(), dePart);
		} else {
			hql.append(" and t.parent is null");
			domains = this.systemService.findHql(hql.toString());
		}
		String sql = null;
		Map<String,Object> map = null;
		List<Map<String,Object>> dateList = new ArrayList<Map<String,Object>>();
		if(StringUtils.isNotBlank(parentid)){
			List<TSUser> sss=systemService.findByProperty(TSUser.class,"TSLDivision.code",parentid);
			for(TSUser ss:sss){
				map = new HashMap<String,Object>();
				map.put("id", ss.getId());
				String userDutys=ss.getUserDutys();
				if(StringUtils.isNotBlank(userDutys)){
					map.put("name", ss.getRealName()+"("+userDutys+")");	
				}else{
					map.put("name", ss.getRealName());
				}
				
				map.put("code",ss.getId());
				map.put("chkDisabled", false);
				//map.put("checked", false);
				if(ids.length>0){
					for(String id:ids){
						if(id.equals(ss.getId())){
							map.put("checked", true);
						}
					}
				}
				map.put("pId", parentid);
				map.put("isParent",false);
				dateList.add(map);
			}
		}
		if(domains.size()>0){
			map = null;
			sql = null;
			Object[] params = null;
			for(TSLDivisionEntity domain:domains){
				map = new HashMap<String,Object>();
				map.put("id", domain.getCode());
				map.put("name", domain.getName());

				map.put("code",domain.getCode());
				map.put("chkDisabled", true);
				if(ids.length>0){
					for(String id:ids){
						if(id.equals(domain.getId())){
							map.put("checked", true);
						}
					}
				}
				
				if(StringUtils.isNotBlank(parentid)){
					map.put("pId", parentid);
				} else{
					map.put("pId", "1");
				}
				//根据id判断是否有子节点
				//sql = "select count(1) from t_sl_division t where t.parent_code = ?";
				//params = new Object[]{domain.getDivisionCode()};
				//long count = this.systemService.getCountForJdbcParam(sql, params);
				//if(count>0){
					//map.put("isParent",true);
				//}
				map.put("isParent",true);
				dateList.add(map);
			}
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(dateList);
		j.setMsg(jsonArray.toString());
		return j;
	}

    
    /**
	 * 文件添加跳转
	 *
	 * @param req
	 * @return
	 */
	private static final String CONTROLLER_ADD_OR_UPDATE_FILE = "modules/hzz/slRiverLake/slFiles";
	@RequestMapping(params = "addorupdateFiles")
	public String addorupdateFiles(TSLBaseRiverLake entity,HttpServletRequest request) {
		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
		String paramType1=oConvertUtils.getString(request.getParameter("paramType1"));
		request.setAttribute("paramType1", paramType1);
		if (StringUtil.isNotEmpty(entity.getId())) {
			if ("a".equals(paramType)){
				entity=systemService.get(TSLRLRiverEntity.class, entity.getId());
			}
			if ("b".equals(paramType)){
				entity=systemService.get(TSLRLLakeEntity.class, entity.getId());
			}
			if ("c".equals(paramType)){
				entity=systemService.get(TSLRLReachEntity.class, entity.getId());
			}
			if ("d".equals(paramType)){
				entity=systemService.get(TSLRLReservoirEntity.class, entity.getId());
			}
		}
		request.setAttribute("entity", entity);
		return CONTROLLER_ADD_OR_UPDATE_FILE;
	}
	
	/**
	 * 保存文件
	 *
	 * @param request
	 * @param response
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "saveFiles", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson saveFiles(HttpServletRequest request, HttpServletResponse response,TSLBaseRiverLake entity) {
		AjaxJson j = new AjaxJson();
		String riverId = oConvertUtils.getString(request.getParameter("riverId"));// 文件标题
		String paramType1=oConvertUtils.getString(request.getParameter("paramType1"));
		String publicSignsLngLat=oConvertUtils.getString(request.getParameter("publicSignsLngLat"));
		
		if (StringUtil.isNotEmpty(riverId)) {
			entity.setId(riverId);
			entity=systemService.getEntity(TSLBaseRiverLake.class, riverId);
			if (StringUtil.isNotEmpty(paramType1)) {
				entity.setTempName(paramType1);	
			}
			if (StringUtil.isNotEmpty(publicSignsLngLat)) {
				entity.setPublicSignsLngLat(publicSignsLngLat);
			}
			systemService.saveOrUpdate(entity);
			systemService.addLog(j.getMsg(), Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		}
		j.setMsg("文件添加成功");
		//防止多级报错
		if (entity!=null){
			try {
				TSLRLRiverEntity t =new TSLRLRiverEntity();
				MyBeanUtils.copyBeanNotNull2Bean(entity, t);
				t.setBaseRiverLakeUserList(null);
				t.setTSLDivision(null);
				t.setList(null);
				t.setParent(null);
				j.setObj(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return j;
	}

	/**
	 * 附件预览页面打开链接
	 * 
	 * @return
	 */
	@RequestMapping(params = "openViewFile")
	public ModelAndView openViewFile(HttpServletRequest request) {
		String fileid = request.getParameter("fileid");
		TSLBaseRiverLake tslBaseRiverLake=systemService.getEntity(TSLBaseRiverLake.class, fileid);
		String subclassname = oConvertUtils.getString(request.getParameter("subclassname"), "com.saili.hzz.web.system.pojo.base.TSAttachment");
		String contentfield = oConvertUtils.getString(request.getParameter("contentfield"));
		Class<?> fileClass = MyClassLoader.getClassByScn(subclassname);// 附件的实际类
		
		//Object fileobj =null;
		String fileobj="";
		String paramType1 = oConvertUtils.getString(request.getParameter("paramType1"));
		if (StringUtil.isNotEmpty(paramType1)) {
			if ("archivesDoc".equals(paramType1)){
				//if (tslBaseRiverLake.getArchivesDoc()!=null){
					//fileobj = systemService.getEntity(fileClass, tslBaseRiverLake.getArchivesDoc().getId());	
				//}
				fileobj=tslBaseRiverLake.getArchivesDoc();
			}
			if ("policyDoc".equals(paramType1)){
				//if (tslBaseRiverLake.getPolicyDoc()!=null){
					//fileobj = systemService.getEntity(fileClass, tslBaseRiverLake.getPolicyDoc().getId());	
				//}
				fileobj=tslBaseRiverLake.getPolicyDoc();
			}
			if ("publicSignsDoc".equals(paramType1)){
				//if (tslBaseRiverLake.getPublicSignsDoc()!=null){
					//fileobj = systemService.getEntity(fileClass, tslBaseRiverLake.getPublicSignsDoc().getId());	
				//}
				fileobj=tslBaseRiverLake.getPublicSignsDoc();
			}	
		}
		
		if (StringUtil.isEmpty(fileobj)){
			String realpath ="images/example.jpg";
			request.setAttribute("realpath", realpath);
			return new ModelAndView("common/upload/imageView");
		}
		ReflectHelper reflectHelper = new ReflectHelper(fileobj);
		String extend = oConvertUtils.getString(reflectHelper.getMethodValue("extend"));
		if ("dwg".equals(extend)) {
			String realpath = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
			request.setAttribute("realpath", realpath);
			return new ModelAndView("common/upload/dwgView");
		} else if (FileUtils.isPicture(extend)) {
			String realpath = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
			request.setAttribute("realpath", realpath);
			request.setAttribute("fileid", fileid);
			request.setAttribute("subclassname", subclassname);
			request.setAttribute("contentfield", contentfield);
			return new ModelAndView("common/upload/imageView");
		} else {
			String swfpath = oConvertUtils.getString(reflectHelper.getMethodValue("swfpath"));

			swfpath=swfpath.replace("\\","/");

			request.setAttribute("swfpath", swfpath);
			return new ModelAndView("common/upload/swfView");
		}
	}
	
	
	/**
	 * 公众投诉 页面跳转
	 * Complaint
	 * @return
	 */
	private static final String COMPLAINT_CONTROLLER_LIST = "modules/hzz/slRiverLake/complaint/slRiverLakeComplaintList";
	@RequestMapping(params = "complaintlist")
	public String complaintList(HttpServletRequest request) {
		return COMPLAINT_CONTROLLER_LIST;
	}
	
	@RequestMapping(params = "complaintlist1")
	public String complaintList1(HttpServletRequest request) {
		request.setAttribute("userRight", "userRight");
		return COMPLAINT_CONTROLLER_LIST+"1";
	}
	
	@RequestMapping(params = "datagridComplaintEntity")
	public void datagridComplaintEntity(TSLBaseRiverLakePatrolEvent entity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String userRight=oConvertUtils.getString(request.getParameter("userRight"));
		request.setAttribute("userRight", userRight);
		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
		CriteriaQuery cq = new CriteriaQuery(TSLBaseRiverLakePatrolEvent.class, dataGrid);
		//查询条件组装器
        HqlGenerateUtil.installHql(cq, entity);
        String[] sourceType = new String[]{"10","11", "12", "13"};
        cq.in("sourceType", sourceType);
        cq.addOrder("createDate", SortDirection.desc);
        if ("userRight".equals(userRight)){
        	TSUser u = ResourceUtil.getSessionUser();
        	List<TSUserOrg> tsUserOrgList=systemService.findByProperty(TSUserOrg.class, "tsUser.id", u.getId());
        	
        	StringBuffer idsStr = new StringBuffer();
        	for(int i = 0; i < tsUserOrgList.size(); i++){
        		String st1=tsUserOrgList.get(i).getTsDepart().getId();
        		if (i > 0) {
    				idsStr.append(",");
    			}
    			idsStr.append("'").append(st1).append("'");
        	}
        	cq.sql(" (this_.id in (select base_river_lake_patrol_event_id from t_sl_base_river_lake_patrol_event_process_du "
        			+ "where i_type='10' and (handle_user_id='"+u.getId()+"' or handle_depart_id in("+idsStr+"))) or this_.create_by='"+u.getUserName()+"')  ");
        }
        cq.add();
        
        this.systemService.getDataGridReturn(cq, true);
        
        for (Object o : dataGrid.getResults()) {
        	if (o instanceof TSLRLProjectIntakeEntity) {
        		TSLRLProjectIntakeEntity cfe = (TSLRLProjectIntakeEntity) o;
        		if (cfe.getId() != null && !"".equals(cfe.getId())) {
        			
        			
        		}
        	}
        }
        
        TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "addorupdateComplaintEntity")
	public String addorupdateComplaintEntity(TSLBaseRiverLakePatrolEvent entity,HttpServletRequest request) {
		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
		if (StringUtil.isNotEmpty(entity.getId())) {
			entity=systemService.get(TSLBaseRiverLakePatrolEvent.class, entity.getId());
			//map.put("entity", entity);
			//getBaseRiverLakeUserInfos(request, entity);
		}
		
		request.setAttribute("entity", entity);
		return COMPLAINT_CONTROLLER_LIST+"Entity";
	}
	
	
	@RequestMapping(params = "viewComplaintProcess")
	public String viewComplaintProcess(TSLBaseRiverLakePatrolEvent entity,HttpServletRequest request) {
		String paramType=oConvertUtils.getString(request.getParameter("paramType"));
		request.setAttribute("paramType", paramType);
		if (StringUtil.isNotEmpty(entity.getId())) {
			entity=systemService.get(TSLBaseRiverLakePatrolEvent.class, entity.getId());
			//map.put("entity", entity);
			//getBaseRiverLakeUserInfos(request, entity);
		}
		
		request.setAttribute("entity", entity);
		return COMPLAINT_CONTROLLER_LIST+"ViewProcess";
	}
	
	@RequestMapping(params = "saveComplaintEntity")
	@ResponseBody
	public AjaxJson saveComplaintEntity(TSLBaseRiverLakePatrolEvent entity, HttpServletRequest request) {
		//entity.setRiverLakeType(TSLRLRiverEntity.RiverLakeType_Project_Intake);
		String parentId = oConvertUtils.getString(request.getParameter("parentId"));
		if (StringUtil.isNotEmpty(parentId)) {
			TSLBaseRiverLake tslBaseRiverLake =systemService.findUniqueByProperty(TSLBaseRiverLake.class, "code", parentId);
			//TSLBaseRiverLake tslBaseRiverLake =systemService.get(TSLBaseRiverLake.class, parentId);
			entity.setTslBaseRiverLake(tslBaseRiverLake);
		}
		
		//这时传不过来的
		//String testFile1 = oConvertUtils.getString(request.getParameter("testFile1"));
		//if (StringUtil.isEmpty(testFile1))entity.setTempName(testFile1);
		
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(entity.getId())) {
			j.setMsg("公众投诉更新成功");
			TSLBaseRiverLakePatrolEvent t = systemService.get(TSLBaseRiverLakePatrolEvent.class,entity.getId());
			//t.getParent().setCode(entity.getParent()==null||"".equals(entity.getParent().getCode())? null :entity.getParent().getCode());

			try {
				MyBeanUtils.copyBeanNotNull2Bean(entity, t);
				//systemService.saveRLRiver(t,hzids.split(","));
				if (StringUtil.isEmpty(entity.getSourceType())) entity.setSourceType("11");
				
				if (StringUtil.isNotEmpty(t.getHandleHour())){
					
				}else{
					t.setHandleHour(0);
				}
				if (StringUtil.isNotEmpty(t.getFinishedDay())){
					
				}else{
					t.setFinishedDay(0);
				}
				if (StringUtil.isNotEmpty(t.getEventStatus())){
					
				}else{
					t.setEventStatus("20");
				}
				if (StringUtil.isNotEmpty(parentId)) {
					TSLBaseRiverLake tslBaseRiverLake =systemService.findUniqueByProperty(TSLBaseRiverLake.class, "code", parentId);
					//TSLBaseRiverLake tslBaseRiverLake =systemService.get(TSLBaseRiverLake.class, parentId);
					t.setTslBaseRiverLake(tslBaseRiverLake);
				}else{
					t.setTslBaseRiverLake(null);
				}
				systemService.saveOrUpdate(t);
				systemService.addLog(j.getMsg(), Globals.Log_Type_UPDATE,
						Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				logger.error(e.getMessage(), e.fillInStackTrace());
				j.setMsg("公众投诉更新失败");
			}
		} else {
			j.setMsg("公众投诉添加成功");
			
			//systemService.saveRLRiver(entity,hzids.split(","));
			
			if (StringUtil.isEmpty(entity.getSourceType())) {
				entity.setSourceType("11");
			}
			entity.setHandleHour(0);
			entity.setFinishedDay(0);
			entity.setEventStatus("20");
//			entity.setLid(-1);
			systemService.save(entity);
			systemService.addLog(j.getMsg(), Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		}
		//防止多级报错
		if (entity!=null){
			try {
				TSLBaseRiverLakePatrolEvent t =new TSLBaseRiverLakePatrolEvent();
				MyBeanUtils.copyBeanNotNull2Bean(entity, t);
				if (t.getTslBaseRiverLake()!=null){
					t.getTslBaseRiverLake().setParent(null);
					t.getTslBaseRiverLake().setList(null);
					t.getTslBaseRiverLake().setArchivesDoc(null);
					t.getTslBaseRiverLake().setPolicyDoc(null);
					t.getTslBaseRiverLake().setPublicSignsDoc(null);
					
				}
				j.setObj(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return j;
	}
	
	@RequestMapping(params = "delComplaintEntity")
	@ResponseBody
	public AjaxJson delComplaintEntity(TSLBaseRiverLakePatrolEvent entity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		entity = systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,
				entity.getId());
		j.setMsg("公众投诉删除成功");
		systemService.delete(entity);
		systemService.addLog(j.getMsg(), Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		return j;
	}
	
	/**
	 * 批量删除业务SQL表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDelComplaintEntity")
	@ResponseBody
	public AjaxJson doBatchDelComplaintEntity(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "公众投诉表删除成功";
		try{
			for(String id:ids.split(",")){
				TSLBaseRiverLakePatrolEvent entity = systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,id);
				systemService.delete(entity);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "公众投诉表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	 
	@RequestMapping(params = "goComplaintCheck")
	public ModelAndView goComplaintCheck( HttpServletRequest request) {
		logger.info("----审核-----");
		String id=request.getParameter("id");
		if (StringUtil.isNotEmpty(id)) {
			TSLBaseRiverLakePatrolEvent entity =  systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,  id);
			request.setAttribute("entity", entity);
		}
		return new ModelAndView(COMPLAINT_CONTROLLER_LIST+"Entity-Check");
		
	}
	
	@RequestMapping(params = "doComplaintCheck")
	@ResponseBody
	public AjaxJson doComplaintCheck(String content,String id,String status) {
		logger.info("-------审核意见:"+content);//demo简单作打印,实际项目可酌情处理
		String message = null;
		AjaxJson j = new AjaxJson();
		//TSLBaseRiverLakePatrolEvent entity =  systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,  id);
		message = "审核成功";
		try{
			JSONObject jo = new JSONObject();
			jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE,"TSLBaseRiverLakePatrolEvent");
			jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE_TYPE,"doComplaintCheck");
			jo.put("content",content);
			jo.put("id",id);
			jo.put("status",status);
			systemService.updateByJSONObject(jo);
			//jeecgDemo.setStatus(status);
			//this.jeecgDemoService.updateEntitie(jeecgDemo);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			j.setSuccess(true);
		}catch(Exception e){
			j.setSuccess(false);
			e.printStackTrace();
			message = "审核失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(params = "goViewProcess")
	public ModelAndView goViewProcess( HttpServletRequest request) {
		logger.info("----查看流程-----");
		String id=request.getParameter("id");
		if (StringUtil.isNotEmpty(id)) {
			List<TSLBaseRiverLakePatrolEventProcess> processList = 
					systemService.findByProperty(TSLBaseRiverLakePatrolEventProcess.class, "tslBaseRiverLakePatrolEvent.id", id);
			request.setAttribute("processList", processList);
		}
		return new ModelAndView(COMPLAINT_CONTROLLER_LIST+"Entity-View-Process");
	}
	/**
	 * 送审
	 * 
	 * @return
	 */
	@RequestMapping(params = "processComplaint")
	@ResponseBody
	public AjaxJson processComplaint(String id,HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		try {
			message = "送审成功";
			j.setSuccess(true);
			JSONObject jo = new JSONObject();
			jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE,"TSLBaseRiverLakePatrolEvent");
			jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE_TYPE,"processComplaint");
			jo.put("id",id);
			jo.put("status","21");
			systemService.updateByJSONObject(jo);
			//jeecgDemo.setStatus(status);
			//this.jeecgDemoService.updateEntitie(jeecgDemo);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			logger.info(e.getMessage());
			message = "送审失败";
			j.setSuccess(false);
		}
		j.setMsg(message);
		return j;
	}
	
	
	@RequestMapping(params = "goComplaintTransfer")
	public ModelAndView goComplaintTransfer( HttpServletRequest request) {
		logger.info("----转派-----");
		String id=request.getParameter("id");
		if (StringUtil.isNotEmpty(id)) {
			TSLBaseRiverLakePatrolEvent entity =  systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,  id);
			request.setAttribute("entity", entity);
		}
		return new ModelAndView(COMPLAINT_CONTROLLER_LIST+"Entity-Transfer");
	}
	@RequestMapping(params = "doComplaintTransfer")
	@ResponseBody
	public AjaxJson doComplaintTransfer(String content,String id,String hzids,String hzname) {
		logger.info("-------转派意见:"+content);//demo简单作打印,实际项目可酌情处理
		String message = null;
		AjaxJson j = new AjaxJson();
		//TSLBaseRiverLakePatrolEvent entity =  systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,  id);
		message = "转派成功";
		try{
			JSONObject jo = new JSONObject();
			jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE,"TSLBaseRiverLakePatrolEvent");
			jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE_TYPE,"doComplaintTransfer");
			jo.put("content",content);
			jo.put("id",id);
			jo.put("hzids",hzids);
			jo.put("hzname",hzname);
			systemService.updateByJSONObject(jo);
			//jeecgDemo.setStatus(status);
			//this.jeecgDemoService.updateEntitie(jeecgDemo);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			j.setSuccess(true);
		}catch(Exception e){
			j.setSuccess(false);
			e.printStackTrace();
			message = "转派失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(params = "goComplaintProcess")
	public ModelAndView goComplaintProcess( HttpServletRequest request) {
		logger.info("----处理-----");
		String id=request.getParameter("id");
		if (StringUtil.isNotEmpty(id)) {
			TSLBaseRiverLakePatrolEvent entity =  systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,  id);
			request.setAttribute("entity", entity);
			request.setAttribute("curDate", new Date());
		}
		return new ModelAndView(COMPLAINT_CONTROLLER_LIST+"Entity-Process");
	}
	@RequestMapping(params = "doComplaintProcess")
	@ResponseBody
	public AjaxJson doComplaintProcess(String handleDept,String handlePhone,String handleName,String handleType,
			String handleIsTF,String handleDate,String handleMemo,String id) {
		logger.info("---处理---");//demo简单作打印,实际项目可酌情处理
		String message = null;
		AjaxJson j = new AjaxJson();
		//TSLBaseRiverLakePatrolEvent entity =  systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,  id);
		message = "处理成功";
		try{
			JSONObject jo = new JSONObject();
			jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE,"TSLBaseRiverLakePatrolEvent");
			jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE_TYPE,"doComplaintProcess");
			jo.put("handleDept",handleDept);
			jo.put("handlePhone",handlePhone);
			jo.put("handleName",handleName);
			jo.put("handleType",handleType);
			jo.put("handleIsTF",handleIsTF);
			jo.put("handleDate",handleDate);
			jo.put("handleMemo",handleMemo);
			jo.put("id",id);
			systemService.updateByJSONObject(jo);
			//jeecgDemo.setStatus(status);
			//this.jeecgDemoService.updateEntitie(jeecgDemo);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			j.setSuccess(true);
		}catch(Exception e){
			j.setSuccess(false);
			e.printStackTrace();
			message = "处理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	//==CommonDao==uploadFile==文件上传
	//==CgUploadController==delFile==删除文件
	
	
	@RequestMapping(params = "goComplaintReturn")
	public ModelAndView goComplaintReturn( HttpServletRequest request) {
		logger.info("----回访-----");
		String id=request.getParameter("id");
		if (StringUtil.isNotEmpty(id)) {
			TSLBaseRiverLakePatrolEvent entity =  systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,  id);
			request.setAttribute("entity", entity);
			request.setAttribute("curDate", new Date());
			
		}
		return new ModelAndView(COMPLAINT_CONTROLLER_LIST+"Entity-Return");
	}
	@RequestMapping(params = "doComplaintReturn")
	@ResponseBody
	public AjaxJson doComplaintReturn(String massSatisP,String returnDate,String id) {
		logger.info("-------回访意见:"+returnDate);//demo简单作打印,实际项目可酌情处理
		String message = null;
		AjaxJson j = new AjaxJson();
		//TSLBaseRiverLakePatrolEvent entity =  systemService.getEntity(TSLBaseRiverLakePatrolEvent.class,  id);
		message = "回访成功";
		try{
			JSONObject jo = new JSONObject();
			jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE,"TSLBaseRiverLakePatrolEvent");
			jo.put(SystemService.UPDATE_BY_JSONOBJECT_TABLE_TYPE,"doComplaintReturn");
			jo.put("returnDate",returnDate);
			jo.put("massSatisP",massSatisP);
			jo.put("id",id);
			systemService.updateByJSONObject(jo);
			//jeecgDemo.setStatus(status);
			//this.jeecgDemoService.updateEntitie(jeecgDemo);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			j.setSuccess(true);
		}catch(Exception e){
			j.setSuccess(false);
			e.printStackTrace();
			message = "回访失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	private static final String CONTROLLER_WQ_LIST = "modules/hzz/slRiverLake/wq/slWaterFunctionQualityList";
	@RequestMapping(params = "listwq")
	public String listwq(HttpServletRequest request) {
		return CONTROLLER_WQ_LIST;
	}
	@RequestMapping(params = "datagridwq")
	@ResponseBody
	public void datagridwq(TSLRLProjectWaterFunctionQuality entity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSLRLProjectWaterFunctionQuality.class, dataGrid);
		//查询条件组装器
        HqlGenerateUtil.installHql(cq, entity);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	private static final String CONTROLLER_WQ_ADD_OR_UPDATE = "modules/hzz/slRiverLake/wq/slWaterFunctionQuality";
	@RequestMapping(params = "addorupdateEntityWQ")
	public String addorupdateEntityWQ(TSLRLProjectWaterFunctionQuality entity,HttpServletRequest request) {
		if (StringUtil.isNotEmpty(entity.getId())) {
			entity=systemService.get(TSLRLProjectWaterFunctionQuality.class, entity.getId());	
		}else{
			entity=new TSLRLProjectWaterFunctionQuality();
		}
		request.setAttribute("entity", entity);
		return CONTROLLER_WQ_ADD_OR_UPDATE;
	}
	
	/**
	 * easyui AJAX请求数据
	 *
	 * @param projectWaterFunction
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagridWF")
	public void datagridWF(TSLRLProjectWaterFunction projectWaterFunction,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		List<TSLRLProjectWaterFunction> list = projectWaterFunctionService.listNoRefProjectWaterFunctionQuality(projectWaterFunction, dataGrid);
		Map<String,Map<String,Object>> extMap = new HashMap<String, Map<String,Object>>();
		List<TSType> useWaterType=ResourceUtil.getCacheTypes("useWaterType".toLowerCase());
		List<TSType> waterQuality=ResourceUtil.getCacheTypes("waterQuality".toLowerCase());
		for(TSLRLProjectWaterFunction temp : list){
			//此为针对原来的行数据，拓展的新字段
	        Map m = new HashMap();
	        for(TSType tsType:useWaterType){
	        	if (tsType.getTypecode().equals(temp.getUseWaterType())){
	        		m.put("extField1",tsType.getTypename());
	        		break;
	        	}
	        }
	        for(TSType tsType:waterQuality){
	        	if (tsType.getTypecode().equals(temp.getWaterQuality())){
	        		m.put("extField2",tsType.getTypename());
	        		break;
	        	}
	        }
	        TSLBaseRiverLake parent=temp.getParent();
	        if (parent != null){
	        	m.put("extField3",parent.getName());
	        }else{
	        	m.put("extField3","");
	        }
	        extMap.put(temp.getId(), m);
	        
		}
		TagUtil.datagrid(response, dataGrid, extMap);
	}
	
	@RequestMapping(params = "saveEntityWQ")
	@ResponseBody
	public AjaxJson saveEntityWQ(TSLRLProjectWaterFunctionQuality entity, HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		String cc=oConvertUtils.getString(request.getParameter("cc"));
		if (StringUtil.isNotEmpty(cc)) {
			TSLRLProjectWaterFunction tslRLProjectWaterFunction=new TSLRLProjectWaterFunction();
			tslRLProjectWaterFunction.setId(cc);
			entity.setTslRLProjectWaterFunction(tslRLProjectWaterFunction);
		}
		if (StringUtil.isNotEmpty(entity.getId())) {
			TSLRLProjectWaterFunctionQuality t = systemService.get(TSLRLProjectWaterFunctionQuality.class,entity.getId());
			MyBeanUtils.copyBeanNotNull2Bean(entity, t);
			systemService.save(t);
			j.setMsg("更新成功");
		} else {
			j.setMsg("添加成功");
			systemService.save(entity);
			systemService.addLog(j.getMsg(), Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		}
		return j;
	}
	@RequestMapping(params = "delEntityWQ")
	@ResponseBody
	public AjaxJson delEntityWQ(TSLRLProjectWaterFunctionQuality entity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		entity = systemService.getEntity(TSLRLProjectWaterFunctionQuality.class,entity.getId());
		systemService.delete(entity);
		j.setMsg("删除成功");
		systemService.addLog(j.getMsg(), Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		return j;
	}
	
	private static final String CONTROLLER_PR_LIST = "modules/hzz/slRiverLake/pr/slPatrolReportList";
	@RequestMapping(params = "listpr")
	public String listpr(HttpServletRequest request) {
		return CONTROLLER_PR_LIST;
	}
	@RequestMapping(params = "datagridpr")
	@ResponseBody
	public void datagridpr(TSLPatrolReport entity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSLPatrolReport.class, dataGrid);
		//查询条件组装器
        HqlGenerateUtil.installHql(cq, entity);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	private static final String CONTROLLER_PR_ADD_OR_UPDATE = "modules/hzz/slRiverLake/pr/slPatrolReport";
	@RequestMapping(params = "addorupdateEntityPR")
	public String addorupdateEntityPR(TSLPatrolReport entity,HttpServletRequest request) {
		if (StringUtil.isNotEmpty(entity.getId())) {
			entity=systemService.get(TSLPatrolReport.class, entity.getId());	
		}else{
			entity=new TSLPatrolReport();
		}
		request.setAttribute("entity", entity);
		return CONTROLLER_PR_ADD_OR_UPDATE;
	}
	
	@RequestMapping(params = "saveEntityPR")
	@ResponseBody
	public AjaxJson saveEntityPR(TSLPatrolReport entity, HttpServletRequest request) throws Exception {
		// 设置上级部门
		String pid = request.getParameter("parent.code");
		if (StringUtils.isEmpty(pid)) {
			entity.setTslDivision(null);
		}else{
			TSLDivisionEntity tslDivision=new TSLDivisionEntity();
			tslDivision.setCode(pid);
			entity.setTslDivision(tslDivision);
		}
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(entity.getId())) {
			TSLPatrolReport t = systemService.get(TSLPatrolReport.class,entity.getId());
			MyBeanUtils.copyBeanNotNull2Bean(entity, t);
			systemService.save(t);
			j.setMsg("更新成功");
		} else {
			j.setMsg("添加成功");
			systemService.save(entity);
			systemService.addLog(j.getMsg(), Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		}
		return j;
	}
	@RequestMapping(params = "delEntityPR")
	@ResponseBody
	public AjaxJson delEntityPR(TSLPatrolReport entity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		entity = systemService.getEntity(TSLPatrolReport.class,entity.getId());
		systemService.delete(entity);
		j.setMsg("删除成功");
		systemService.addLog(j.getMsg(), Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		return j;
	}
	
	
	
	private static final String CONTROLLER_LIST_APICTURE = "modules/hzz/slRiverLake/aPicture/slAPicture";
	@RequestMapping(params = "apicture")
	public String apicture(HttpServletRequest request) {
		return CONTROLLER_LIST_APICTURE;
	}
	
	@RequestMapping(params = "getAPictureInfo")
	@ResponseBody
	public ResponseMessage<?> getAPictureInfo(HttpServletRequest request) {
		String sql=" select name,river_lake_type,mapCenterLngLat,division_divisionCode,"
				+ " (select count(*) from t_sl_base_river_lake_patrol p where p.base_river_lake_id=d.id) as count0 "
				+ " from t_sl_base_river_lake d where LENGTH(d.mapCenterLngLat)>0 and LENGTH(d.mapZoom)>0 "
				+ " and d.river_lake_type<>21"
				+ " union "
				+ " select f.monitoringSection as name,d.river_lake_type,d.mapCenterLngLat,d.division_divisionCode,"
				+ " f.waterQuality as count0 "
				+ " from t_sl_base_river_lake d left "
				+ " join t_sl_rl_project_water_function f on d.id=f.id "
				+ " where LENGTH(d.mapCenterLngLat)>0 and LENGTH(d.mapZoom)>0 "
				+ " and d.river_lake_type=21 and  LENGTH(f.monitoringSection )>0 ";
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("home_info_01", ResourceUtil.getCacheTypes("home_info_01"));
		resultMap.put("info_data", systemService.findForJdbc(sql));
		return Result.success(resultMap);
	}
	
}
