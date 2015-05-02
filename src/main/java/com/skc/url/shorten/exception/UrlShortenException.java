package com.skc.url.shorten.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.skc.url.shorten.utils.ObjectUtils;

/**
 * <p>This is Generic Web Exception for WebServices</p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
@Provider
public class UrlShortenException implements ExceptionMapper<SystemGenericException> {
	

	public Response toResponse(SystemGenericException systemException) {	
		ErrorMessage errorMsg = new ErrorMessage();
		errorMsg.setErrorCode(systemException.getErrorCode());
		errorMsg.setErrorMessage(systemException.getErrorMessage());
		if(!ObjectUtils.checkNull(systemException.getErrorLink())){
			errorMsg.setErrorLink(systemException.getErrorLink());
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMsg)
				.type(MediaType.APPLICATION_JSON)
				.build();	
	}

}
