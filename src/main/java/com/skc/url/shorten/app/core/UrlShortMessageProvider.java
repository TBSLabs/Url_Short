package com.skc.url.shorten.app.core;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class UrlShortMessageProvider extends Application{
	final static Logger LOGGER = Logger.getLogger(UrlShortMessageProvider.class);
	
	@Override
	public Set<Object> getSingletons() {
		LOGGER.info("Going to register the MessageBodyProviders");
		final Set<Object> instances = new HashSet<Object>();
		instances.add(new JacksonJsonProvider());
		LOGGER.info("Custom MessageBodyProvider are registered successfully");
		return super.getSingletons();
	}
}