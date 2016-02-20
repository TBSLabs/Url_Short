package com.skc.url.shorten.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/**
 * <p> This is the CORS Filter for which the js Client can easily give the request to the Server</p>
 * @author IgnatiusCipher(IgC)
 * @version 1.0
 * */
@Provider
public class CORSFilter implements ContainerResponseFilter {
	
	/**
	 * <p>This is a response filter which will set the Response Header after a request is performed and response will create
	 * 	 The main purpose of this filter is to support CORS.
	 * </p>
	 * */
	public void filter(ContainerRequestContext contextRequest,ContainerResponseContext contextResponse) throws IOException {
		MultivaluedMap<String, Object> headers = contextResponse.getHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		//headers.add("Access-Control-Allow-Origin", "http://podcastpedia.org"); //allows CORS requests only coming from podcastpedia.org		
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");			
		headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");
	}

}
