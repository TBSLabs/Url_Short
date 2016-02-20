package com.skc.url.shorten.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.mongodb.MongoException;
import com.skc.url.shorten.utils.CommonConstraints;

/**
 * <p> Provider for {@link MongoException}</p>
 * @author IgnatiusCipher(IgC)
 * @version 1.0
 * */
@Provider
public class MongoExceptionProvider implements ExceptionMapper<MongoException> {
	static final Logger LOGGER = Logger.getLogger(MongoExceptionProvider.class);
	
	public Response toResponse(MongoException exception) {
		LOGGER.error(exception);
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(CommonConstraints.ERROR_DB_400);
		errorMessage.setErrorMessage(CommonConstraints.ERROR_DB_400_MSG);
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMessage)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

}
