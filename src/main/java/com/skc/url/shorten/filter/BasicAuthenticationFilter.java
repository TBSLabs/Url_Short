package com.skc.url.shorten.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.springframework.http.HttpMethod;

import com.skc.url.shorten.auth.service.AuthenticateService;
import com.skc.url.shorten.exception.SystemGenericException;
import com.skc.url.shorten.utils.CommonConstraints;
import com.skc.url.shorten.utils.ObjectUtils;

/**
 * <p> This is the Authentication Filter. Currently , This filter support BASIC Authentication </p>
 * @author IgnatiusCipher(IgC)
 * @version 1.0
 * */
@Provider
@PreMatching
public class BasicAuthenticationFilter implements  ContainerRequestFilter{

	@Context
	HttpServletRequest request;
	
	/**
	 * As current class instance will not be in Spring Object Pool. Hence Using JAX-RS based Injection
	 * */
	@Inject
	AuthenticateService authenticateService;

	/**
	 * String Constants
	 * */
	public static final String INVALID="invalid";

	/**
	 *<p>This method will filter the authenticated user into the System </p> 
	 * */
	public void filter(ContainerRequestContext context) throws IOException {
		
		String authCredential = request.getHeader(CommonConstraints.AUTHENTICATION_HEADER);
		String securitySkipPath = request.getRequestURL().toString();
		String subPath = !ObjectUtils.checkNull(securitySkipPath)?securitySkipPath.substring(securitySkipPath.lastIndexOf(CommonConstraints.DELIM_SLASH)):INVALID;
		if(subPath.equalsIgnoreCase(INVALID)){
			throw new SystemGenericException(CommonConstraints.ERROR_WEB_500,CommonConstraints.ERROR_WEB_500_MSG);
		}
		boolean isAuthenticated = false;
		if(subPath.equalsIgnoreCase(CommonConstraints.PATH_USER) && request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString())||authenticateService.checkForShortUrl(subPath)){
			isAuthenticated=true;
		}else{
			//TODO Check username from path and validated it from Header
			isAuthenticated = authenticateService.authenticate(authCredential);
		}
		if(!isAuthenticated){
			context.abortWith(Response.status(Status.UNAUTHORIZED).entity("Please provide valid credential").build());
		}
	} 
}
