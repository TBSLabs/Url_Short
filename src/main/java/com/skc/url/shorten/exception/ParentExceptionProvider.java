package com.skc.url.shorten.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.skc.url.shorten.utils.CommonConstraints;

/**
 * <p> Provider class for {@link Exception}. When ever {@link Exception} or its sub-class throws by this system (not specific whose provider are present).
 * Then , the provider will activate to send the relevent message</p>
 * @author IgnatiusCipher(IgC)
 * @version 1.0
 * */
@Provider
public class ParentExceptionProvider implements ExceptionMapper<Exception> {
	static final Logger LOGGER = Logger.getLogger(ParentExceptionProvider.class);
	
	public Response toResponse(Exception exception) {
		LOGGER.error(exception);
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(CommonConstraints.ERROR_WEB_500);
		errorMessage.setErrorMessage(CommonConstraints.ERROR_WEB_500_MSG);
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMessage)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

}
