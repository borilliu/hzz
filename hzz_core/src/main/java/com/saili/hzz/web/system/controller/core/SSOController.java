package com.saili.hzz.web.system.controller.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import com.saili.hzz.core.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 单点登录
 * @author zhoujf
 * 
 */
@Controller
@RequestMapping("/")
public class SSOController extends BaseController{
	private Logger log = Logger.getLogger(SSOController.class);
	
	//http://localhost:8080/slRiverManager/toLogin.do?ReturnURL=sdf
	@RequestMapping(value = "toLogin")
	public String toLogin(HttpServletRequest request) {
		String returnURL = request.getParameter("ReturnURL");
		log.info("SSO 资源路径returnURL："+returnURL);
		request.setAttribute("ReturnURL", returnURL);
		return "login/login";
	}
	
	
}