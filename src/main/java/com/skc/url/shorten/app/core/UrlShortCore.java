package com.skc.url.shorten.app.core;

import javax.ws.rs.ApplicationPath;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.skc.url.shorten.exception.MongoExceptionProvider;
import com.skc.url.shorten.exception.ParentExceptionProvider;
import com.skc.url.shorten.exception.UrlShortenException;
import com.skc.url.shorten.filter.BasicAuthenticationFilter;
import com.skc.url.shorten.rest.api.v1.UrlShorteningAPIServiceImpl;
import com.skc.url.shorten.rest.api.v1.redirect.UrlRedirectionAPIServiceImpl;
import com.skc.url.shorten.rest.api.v1.user.UserServiceImpl;

@ApplicationPath("/api/v1/*")
public class UrlShortCore extends ResourceConfig{
	final static Logger LOGGER = Logger.getLogger(UrlShortCore.class);
	public UrlShortCore(){
		LOGGER.info("Going to load the packages");
		packages("com.skc.url.shorten.filter,com.skc.url.shorten.rest.api,com.skc.url.shorten.exception,org.codehaus.jackson.jaxrs");
		LOGGER.info("Package were Register Succeessfully");
		
		LOGGER.info("Registering thirdparty Provider");
		register(JacksonJaxbJsonProvider.class);
		LOGGER.info("Thirdparty Provider were Registered Successfully");
		
		LOGGER.info("Going to Register Classes");
		registerClasses(UrlShortenException.class,UrlShorteningAPIServiceImpl.class,
				UrlRedirectionAPIServiceImpl.class,UserServiceImpl.class,BasicAuthenticationFilter.class
				,MongoExceptionProvider.class
				,ParentExceptionProvider.class);
		
		LOGGER.info("All Classes were register successfully");
	}
}
