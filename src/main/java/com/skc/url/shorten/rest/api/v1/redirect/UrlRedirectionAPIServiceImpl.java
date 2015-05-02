package com.skc.url.shorten.rest.api.v1.redirect;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.skc.url.shorten.db.Mongo;
import com.skc.url.shorten.exception.SystemGenericException;
import com.skc.url.shorten.utils.CommonConstraints;
import com.skc.url.shorten.utils.ObjectUtils;

/**
 * <p>End point Service Where the call will go to the external System</p>
 * @author IgnatiusCipher(IgC)
 * @version 1.0
 * */
@Service("urlRedirctService")
@Path("/{shortenPath}")
public class UrlRedirectionAPIServiceImpl {
	final Logger LOG = Logger.getLogger(UrlRedirectionAPIServiceImpl.class);
	
	@Resource(name="mongo")
	Mongo mongo;
	/**
	 * <p>This method will redirect the request to the respected third party System </p>
	 * @param request type of {@link HttpServletRequest}
	 * @param response type of {@link HttpServletResponse}
	 * @param shortenPath type of {@link String}
	 * */
	@GET
	public void urlRedirected(@Context HttpServletRequest request,@Context HttpServletResponse response,@PathParam(CommonConstraints.SHORTEN_PATH) String shortenPath) throws SystemGenericException{
		long calculateTime = new Date().getTime();
		if(LOG.isDebugEnabled()){
			LOG.debug("Got a request for Shortening at "+new Date());
		}
		LOG.info("Checking the Shorten String from Database ");
		DBCollection collection = mongo.getCollection(null, CommonConstraints.DB_COLLECTIONS);
		
		DBObject dbObject = new BasicDBObject(CommonConstraints.SHORT_LINK,shortenPath);
		DBObject dbResponse = collection.findOne(dbObject);
		
		if(ObjectUtils.checkNull(dbResponse)){
			throw new SystemGenericException("500","Unable to process the url. Please re-check it and Try again",CommonConstraints.HOST_URL+request.getContextPath()+request.getServletPath()+CommonConstraints.DELIM_SLASH+shortenPath);
		}
		Integer integer = (Integer) dbResponse.get(CommonConstraints.REDIRECTED_NUMBER);
		LOG.info("Increase a visited count for the Shorten URL "+shortenPath);
		BasicDBObject updatedObject = new BasicDBObject(CommonConstraints.UPDATED_DATE,new Date());
		updatedObject.putAll(dbResponse);
		updatedObject.append(CommonConstraints.REDIRECTED_NUMBER, ++integer);
		collection.update(dbObject, updatedObject);
		LOG.info("Updated the Collections for redirection");
		if(LOG.isDebugEnabled()){
			LOG.debug("Time Taken to execute UrlShorteningAPIServiceImpl is "+(new Date().getTime()-calculateTime));
		}
		LOG.info("Going to interact with the thirdparty System");
		try {
			response.sendRedirect((String) dbResponse.get(CommonConstraints.REQUEST_URL));
		} catch (IOException e) {
			LOG.error(e);
			throw new SystemGenericException("500","Unable to process the url. Please re-check it and Try again",CommonConstraints.HOST_URL+request.getContextPath()+request.getServletPath()+CommonConstraints.DELIM_SLASH+shortenPath);
		}
	}
}
