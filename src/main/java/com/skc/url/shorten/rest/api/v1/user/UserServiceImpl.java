package com.skc.url.shorten.rest.api.v1.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.skc.url.shorten.db.Mongo;
import com.skc.url.shorten.exception.SystemGenericException;
import com.skc.url.shorten.model.v1.UserDetailsRequest;
import com.skc.url.shorten.model.v1.UserDetailsResponse;
import com.skc.url.shorten.model.v1.UserDetailsResponse.UserLinks;
import com.skc.url.shorten.utils.CommonConstraints;
import com.skc.url.shorten.utils.ObjectUtils;

/**
 * <p>This class contain all the method for CRUD Operation for user</p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
@Service("userService")
@Path(CommonConstraints.PATH_USER)
public class UserServiceImpl {
	final Logger LOG = Logger.getLogger(UserServiceImpl.class);

	private static final String USER_DETAILS = "User Details";
	private static final String USER_IS_SUCCESSFULLY_CREATED = "User is successfully created.";

	
	@Resource(name="mongo")
	Mongo mongo;
	
	/**
	 * <p> This is the end point to create an user</p>
	 * @param request type of {@link HttpServletRequest}
	 * @param detailsRequest type of {@link UserDetailsRequest}
	 * @throws SystemGenericException
	 * @return {@link Response}
	 * */
	@POST
	@Consumes(value={MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces(value={MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response createUser(@Context HttpServletRequest request,UserDetailsRequest detailsRequest)throws SystemGenericException{
		long calculateTime = new Date().getTime();
		if(LOG.isDebugEnabled()){
			LOG.debug("Got a request for Shortening at "+new Date());
		}
		
		DBCollection collection = null;
		try{
			collection= getUserDBCollection("user_collection");
			LOG.info("DB Collection is located and got it from DB");
		}catch(Exception e){
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_DB_400,CommonConstraints.ERROR_DB_400_MSG);
		}
	
		//TODO Password to be encrypted
		DBObject userObject =new  BasicDBObject(CommonConstraints.FIRSTNAME,detailsRequest.getFirstName())
							.append(CommonConstraints.LASTNAME, detailsRequest.getLastName())
							.append(CommonConstraints.PASSWORD, detailsRequest.getPassword())
							.append(CommonConstraints.EMAIL, detailsRequest.getUserEmail())
							.append(CommonConstraints.USERNAME, detailsRequest.getUsername())
							.append(CommonConstraints.CREATED_DATE, new Date());
		if(ObjectUtils.checkNull(collection)){
			throw new SystemGenericException(CommonConstraints.ERROR_DB_500,CommonConstraints.ERROR_DB_500_MSG);
		}
		collection.save(userObject);
		LOG.info("User Successfully Created having username "+detailsRequest.getUsername());
		
		String short_url = CommonConstraints.HOST_URL+request.getContextPath()+request.getServletPath()+CommonConstraints.PATH_USER+CommonConstraints.DELIM_SLASH+detailsRequest.getUsername();
		UserDetailsResponse detailsResponse = new UserDetailsResponse();
		detailsResponse.setCreatedDate(new Date().toString());
		detailsResponse.setMessage(USER_IS_SUCCESSFULLY_CREATED);
		detailsResponse.setUsername(detailsRequest.getUsername());
		detailsResponse.setPassword(detailsRequest.getPassword());
		detailsResponse.setHome_url(short_url);
		
		if(LOG.isDebugEnabled()){
			LOG.debug("Time Taken to execute UrlShorteningAPIServiceImpl is "+(new Date().getTime()-calculateTime));
		}
		return Response.status(Status.CREATED).entity(detailsResponse).build();
	}

	/**
	 * <p> This method to get the details of an customer</p>
	 * @param request type of {@link HttpServletRequest}
	 * @param username type of String
	 * @return {@link Response}
	 * */
	@Path("/{username}")
	@GET
	@Consumes(value={MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces(value={MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getUserDetails(@Context HttpServletRequest request,@PathParam(CommonConstraints.USERNAME) String username) throws SystemGenericException{
		long calculateTime = new Date().getTime();
		if(LOG.isDebugEnabled()){
			LOG.debug("Got a request for Shortening at "+new Date());
		}
		
		DBCollection collection = null;
		DBCollection shurtenCollection=null;
		try{
			collection= getUserDBCollection("user_collection");
			shurtenCollection = getUserDBCollection(CommonConstraints.DB_COLLECTIONS);
			LOG.info("DB Collection is located and got it from DB");
		}catch(Exception e){
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_DB_400,CommonConstraints.ERROR_DB_400_MSG);
		}
		
		if(ObjectUtils.checkNull(collection)){
			throw new SystemGenericException(CommonConstraints.ERROR_DB_500,CommonConstraints.ERROR_DB_500_MSG);
		}
		DBObject userObject = new BasicDBObject(CommonConstraints.USERNAME,username);
		DBObject responseObject = collection.findOne(userObject);
		
		UserDetailsResponse detailsResponse = new UserDetailsResponse();
		List<UserDetailsResponse.UserLinks> links = new ArrayList<UserDetailsResponse.UserLinks>();
		DBCursor cursor = shurtenCollection.find(userObject);
		while (cursor.hasNext()) {
			DBObject object = cursor.next();
			UserLinks link = new UserLinks();
			link.setRedirectNumber(((Integer) object.get(CommonConstraints.REDIRECTED_NUMBER)).toString());
			link.setRequestedLinks((String) object.get(CommonConstraints.REQUEST_URL));
			link.setShortLink((String) object.get(CommonConstraints.SHORT_URL));
			link.setVersion((String) object.get(CommonConstraints.VERSION));
			links.add(link);
		}
		detailsResponse.setCreatedDate(((Date) responseObject.get(CommonConstraints.CREATED_DATE)).toString());
		detailsResponse.setUsername((String) responseObject.get(CommonConstraints.USERNAME));
		detailsResponse.setHome_url(CommonConstraints.HOST_URL+request.getContextPath()+request.getServletPath()+CommonConstraints.PATH_USER+CommonConstraints.DELIM_SLASH+detailsResponse.getUsername());
		detailsResponse.setMessage(USER_DETAILS);
		detailsResponse.setPassword((String) responseObject.get(CommonConstraints.PASSWORD));
		detailsResponse.setLinks(links);
		if(LOG.isDebugEnabled()){
			LOG.debug("Time Taken to execute UrlShorteningAPIServiceImpl is "+(new Date().getTime()-calculateTime));
		}
		return Response.ok().entity(detailsResponse).build();
	}
	
	
	private DBCollection getUserDBCollection(String collectionName) {
		return mongo.getCollection(null, collectionName);
	}
	
	
}
