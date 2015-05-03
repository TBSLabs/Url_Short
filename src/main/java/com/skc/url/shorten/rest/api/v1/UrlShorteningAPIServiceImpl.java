package com.skc.url.shorten.rest.api.v1;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.skc.url.shorten.db.Mongo;
import com.skc.url.shorten.db.MongoImpl;
import com.skc.url.shorten.model.v1.UrlModelResponse;
import com.skc.url.shorten.utils.CommonConstraints;
import com.skc.url.shorten.utils.StringShortenUtils;

/**
 * <p> This is the endpoint for the accepting the Longer URL and will make that url as short and return it to User</p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
@Path("/link_short/{username}")
public class UrlShorteningAPIServiceImpl {
	
	private static final String URL_SHORTED = "URL Shorted";
	private static final String ERROR_STATUS_MESSAGE = "Unable to make url Shorting. Please try after sometime";
	private static final String URL_ALREADY_EXIST = "URL Already Exist";

	/**
	 * <p> This method will accept the Longer URL and Save it on the System and will create the response for the User</p>
	 * @param request type of {@link HttpServletRequest}
	 * @param userName type of {@link String}
	 * @param url type of {@link String}
	 * @return {@link Response}
	 * */
	@GET
	@Consumes(value={MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces(value={MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response makeShortLink(@Context HttpServletRequest request,@PathParam(CommonConstraints.USERNAME) String userName,@QueryParam(CommonConstraints.URL) String url){
		UrlModelResponse response = new UrlModelResponse();
		boolean isException=false;
		boolean isExist = false;
		String shortLink=null;
		String defaultMessage=URL_SHORTED;
		try {
			shortLink = StringShortenUtils.getShortenURL(url, userName);
		} catch (InvalidKeyException e) {
			isException=true;
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			isException=true;
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			isException=true;
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			isException=true;
			e.printStackTrace();
		} catch (BadPaddingException e) {
			isException=true;
			e.printStackTrace();
		}
		
		String short_url = CommonConstraints.HOST_URL+request.getContextPath()+request.getServletPath()+CommonConstraints.DELIM_SLASH+shortLink;
		Mongo mongo = new MongoImpl();
		DBCollection collection = mongo.getCollection(null, CommonConstraints.DB_COLLECTIONS);
		
		DBCursor cursor = collection.find(new BasicDBObject(CommonConstraints.REQUEST_URL,url).append(CommonConstraints.USERNAME, userName));
		
		while(cursor.hasNext()){
			DBObject dbObjectQueried = cursor.next();
			short_url = (String) dbObjectQueried.get(CommonConstraints.SHORT_URL);
			defaultMessage=URL_ALREADY_EXIST;
			String requested_url = (String) dbObjectQueried.get(CommonConstraints.REQUEST_URL);
			if(url.equalsIgnoreCase(requested_url)){
				isExist=true;	
			}
		}
		
		if(cursor.size()<1 && !isExist){
			DBObject dbObject = new BasicDBObject(CommonConstraints.USERNAME,userName)
			.append(CommonConstraints.REQUEST_URL, url)
			.append(CommonConstraints.SHORT_URL,short_url)
			.append(CommonConstraints.SHORT_LINK, shortLink)
			.append(shortLink, url)
			.append(CommonConstraints.CREATED_DATE, new Date())
			.append(CommonConstraints.VERSION, CommonConstraints.VERSION_V1)
			.append(CommonConstraints.REDIRECTED_NUMBER, 0);
			collection.save(dbObject);
		}
		
		
		
		
		if(isException){
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.toString());
			response.setStatusMessage(ERROR_STATUS_MESSAGE);
		}else{
			response.setShortenUrl(short_url);
			response.setStatusCode(Status.OK.toString());
			response.setUrlGiven(url);
			response.setStatusMessage(defaultMessage);
		}
		
		return Response.ok().entity(response).build();
	}
}
