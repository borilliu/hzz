package com.saili.hzz.core.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saili.hzz.core.util.StringUtil;
import org.jeecgframework.p3.core.logger.Logger;
import org.jeecgframework.p3.core.logger.LoggerFactory;

public class CORSFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(CORSFilter.class);
	private String allowOrigin;  
    private String allowMethods;  
    private String allowCredentials;  
    private String allowHeaders;  
    private String exposeHeaders;  

	  
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("==== 初始化系统允许跨域请求 ====");
		allowOrigin = filterConfig.getInitParameter("allowOrigin");  
        allowMethods = filterConfig.getInitParameter("allowMethods");  
        allowCredentials = filterConfig.getInitParameter("allowCredentials");  
        allowHeaders = filterConfig.getInitParameter("allowHeaders");  
        exposeHeaders = filterConfig.getInitParameter("exposeHeaders");  
        System.out.println("allowOrigin = " + allowOrigin);
        System.out.println("allowMethods = " + allowMethods);
        System.out.println("allowCredentials = " + allowCredentials);
        System.out.println("allowHeaders = " + allowHeaders);
        System.out.println("exposeHeaders = " + exposeHeaders);
	}

	@Override  
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {  
        HttpServletRequest request = (HttpServletRequest) req;  
        HttpServletResponse response = (HttpServletResponse) res;  
        if (StringUtil.isNotEmpty(allowOrigin)) {
            List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));  
            if (allowOriginList != null && allowOriginList.size() > 0) {  
                String currentOrigin = request.getHeader("Origin");  
                if (allowOriginList.contains(currentOrigin)) {  
                	logger.debug("===currentOrigin=="+currentOrigin);
                    response.setHeader("Access-Control-Allow-Origin", currentOrigin);  
                }  
            }  
        }  
        
        if (StringUtil.isNotEmpty(allowMethods)) {  
            response.setHeader("Access-Control-Allow-Methods", allowMethods);  
        }  
        if (StringUtil.isNotEmpty(allowCredentials)) {  
            response.setHeader("Access-Control-Allow-Credentials", allowCredentials);  
        }  
        if (StringUtil.isNotEmpty(allowHeaders)) {  
            response.setHeader("Access-Control-Allow-Headers", allowHeaders);  
        }  
        if (StringUtil.isNotEmpty(exposeHeaders)) {  
            response.setHeader("Access-Control-Expose-Headers", exposeHeaders);  
        }  
        
        Object userName1=request.getAttribute("userName");
		Object userName2=request.getHeader("userName");
		
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
		response.addHeader("Access-Control-Allow-Credentials", "true"); // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态   
		
        chain.doFilter(req, res);  
    } 


	@Override
	public void destroy() {
		
	}

}
