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
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.skc.url.shorten.db.Mongo;
import com.skc.url.shorten.exception.SystemGenericException;
import com.skc.url.shorten.utils.CommonConstraints;
import com.skc.url.shorten.utils.DateUtils;
import com.skc.url.shorten.utils.MongoUtils;
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
	
	@Resource(name="mongoUtils")
	MongoUtils utils; 
	

	@Value("host.system.url")
	String hostUrl;
	
	/**
	 * <p>This method will redirect the request to the respected third party System </p>
	 * @param request type of {@link HttpServletRequest}
	 * @param response type of {@link HttpServletResponse}
	 * @param shortenPath type of {@link String}
	 * */
	@GET
	public Response urlRedirected(@Context HttpServletRequest request,@Context HttpServletResponse response,@PathParam(CommonConstraints.SHORTEN_PATH) String shortenPath) throws SystemGenericException{
		long calculateTime = new Date().getTime();
		if(LOG.isDebugEnabled()){
			LOG.debug("Got a request for Shortening at "+new Date());
		}
		
		DBObject dbObject = new BasicDBObject(CommonConstraints.SHORT_LINK,shortenPath);
		DBObject dbResponse = utils.getOneObjectFromCollection(CommonConstraints.DB_COLLECTIONS_URL, dbObject);
		
		if(ObjectUtils.checkNull(dbResponse)){
			throw new SystemGenericException(CommonConstraints.ERROR_DB_500,CommonConstraints.ERROR_DB_500_MSG,hostUrl+request.getContextPath()+request.getServletPath()+CommonConstraints.DELIM_SLASH+shortenPath);
		}
		Integer integer = (Integer) dbResponse.get(CommonConstraints.REDIRECTED_NUMBER);
		LOG.info("Increase a visited count for the Shorten URL "+shortenPath);
		//TODO Take a track of Updated Time. Need to put in nested Collection
		BasicDBObject updatedObject = new BasicDBObject(CommonConstraints.UPDATED_DATE,DateUtils.convertDate(CommonConstraints.DATE_YYYY_MM_DD_HH_MM_SS_S_Z, new Date()));
		updatedObject.putAll(dbResponse);
		updatedObject.append(CommonConstraints.REDIRECTED_NUMBER, ++integer);
		utils.updateCollection(CommonConstraints.DB_COLLECTIONS_URL, dbObject, updatedObject);
		LOG.info("Updated the Collections for redirection");
		if(LOG.isDebugEnabled()){
			LOG.debug("Time Taken to execute UrlShorteningAPIServiceImpl is "+(new Date().getTime()-calculateTime));
		}
		LOG.info("Going to interact with the thirdparty System");
		try {
			response.sendRedirect((String) dbResponse.get(CommonConstraints.REQUEST_URL));
		} catch (IOException e) {
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_DB_500,CommonConstraints.ERROR_DB_500_MSG,hostUrl+request.getContextPath()+request.getServletPath()+CommonConstraints.DELIM_SLASH+shortenPath);
		}
		return null;
	}
}
