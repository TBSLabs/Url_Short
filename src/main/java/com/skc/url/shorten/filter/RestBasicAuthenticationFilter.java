package com.skc.url.shorten.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.skc.url.shorten.auth.service.AuthenticateService;
import com.skc.url.shorten.db.Mongo;
import com.skc.url.shorten.exception.SystemGenericException;
import com.skc.url.shorten.utils.CommonConstraints;
import com.skc.url.shorten.utils.ObjectUtils;

/**
 * <p>This is a filter for basic authentication for the WebServices</p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
@Component
public class RestBasicAuthenticationFilter implements Filter{
	private static final String NULL = "null";

	final Logger LOG = Logger.getLogger(RestBasicAuthenticationFilter.class);
	
	@Autowired
	@Qualifier(value="authenticationService")
	AuthenticateService authenticateService;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain filter) throws IOException, ServletException {
		if(request instanceof HttpServletRequest){
			//authenticateService = new AuthenticateServiceImpl();
			HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			String authCredential = httpServletRequest.getHeader(CommonConstraints.AUTHENTICATION_HEADER);
			String securitySkipPath = httpServletRequest.getRequestURL().toString();
			String subPath = !ObjectUtils.checkNull(securitySkipPath)?securitySkipPath.substring(securitySkipPath.lastIndexOf(CommonConstraints.DELIM_SLASH)):NULL;
			if(subPath.equalsIgnoreCase(NULL)){
				throw new SystemGenericException(CommonConstraints.ERROR_WEB_500,CommonConstraints.ERROR_WEB_500_MSG);
			}
			boolean isAuthenticated = true;
			if(subPath.equalsIgnoreCase(CommonConstraints.PATH_USER) && httpServletRequest.getMethod().equalsIgnoreCase(HttpMethod.POST.toString())){
				isAuthenticated=false;
			}else{
				//TODO Check username from path and validated it from Header
				isAuthenticated = authenticateService.authenticate(authCredential);
			}
			if(!isAuthenticated){
				filter.doFilter(request, response);
			}else{
				if(response instanceof HttpServletResponse){
					HttpServletResponse httpServletResponse = (HttpServletResponse)response;
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED, "Basic Authenticate Failed");
				}
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext servletContext = filterConfig.getServletContext();
	    WebApplicationContext webApplicationContext = 
	            WebApplicationContextUtils.getWebApplicationContext(servletContext);

	    AutowireCapableBeanFactory autowireCapableBeanFactory =
	           webApplicationContext.getAutowireCapableBeanFactory();

	    autowireCapableBeanFactory.configureBean(this, "authenticationService");
	}

}
