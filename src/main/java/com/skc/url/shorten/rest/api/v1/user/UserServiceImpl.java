package com.skc.url.shorten.rest.api.v1.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.skc.url.shorten.db.Mongo;
import com.skc.url.shorten.exception.SystemGenericException;
import com.skc.url.shorten.model.v1.UserDetailsRequest;
import com.skc.url.shorten.model.v1.UserDetailsResponse;
import com.skc.url.shorten.model.v1.UserDetailsResponse.UserLinks;
import com.skc.url.shorten.secure.SecureNumberGeneratorService;
import com.skc.url.shorten.utils.CommonConstraints;
import com.skc.url.shorten.utils.DateUtils;
import com.skc.url.shorten.utils.MongoUtils;
import com.skc.url.shorten.utils.ObjectUtils;
import com.skc.url.shorten.utils.StringShortenUtils;

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
	
	@Resource(name="secureNumberGeneratorService")
	SecureNumberGeneratorService secureNumber;
	
	@Resource(name="mongoUtils")
	MongoUtils utils;
	
	@Value("${host.system.url}")
	String hostUrl;
	
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
		String userId = secureNumber.getSecureId().toString();
		String password=null;
		
		collection= getUserDBCollection(CommonConstraints.DB_COLLECTION_USER);
		LOG.info("DB Collection is located and got it from DB");
		password = StringShortenUtils.getEncryptedPassword(detailsRequest.getFirstName());
		
		DBObject userObject =new  BasicDBObject(CommonConstraints.FIRSTNAME,detailsRequest.getFirstName())
							.append(CommonConstraints.LASTNAME, detailsRequest.getLastName())
							.append(CommonConstraints.PASSWORD, password)
							.append(CommonConstraints.EMAIL, detailsRequest.getUserEmail())
							.append(CommonConstraints.USERNAME, userId)
							.append(CommonConstraints.CREATED_DATE, DateUtils.convertDate(CommonConstraints.DATE_YYYY_MM_DD_HH_MM_SS_S_Z, new Date()))
							.append(CommonConstraints.IS_PASSWORD_CHANGE, false);
		if(ObjectUtils.checkNull(collection)){
			throw new SystemGenericException(CommonConstraints.ERROR_DB_500,CommonConstraints.ERROR_DB_500_MSG);
		}
		
		DBObject userNameCheck = new BasicDBObject(CommonConstraints.USERNAME,userId);
		DBObject emailCheck = new BasicDBObject(CommonConstraints.EMAIL,detailsRequest.getUserEmail());
		
		BasicDBList basicDBList = new BasicDBList();
		basicDBList.add(userNameCheck);
		basicDBList.add(emailCheck);
		
		DBCursor cursor = utils.getDataUsingOr(CommonConstraints.DB_COLLECTION_USER, basicDBList);
		if(cursor.size()>0){
			throw new SystemGenericException(CommonConstraints.ERROR_DUPLICATE_3000,CommonConstraints.ERROR_DUPLICATE_3000_MSG+CommonConstraints.EMAIL);
		}
		
		
		collection.save(userObject);
		LOG.info("User Successfully Created having userId as  "+userId);
		
		String short_url = hostUrl+request.getContextPath()+request.getServletPath()+CommonConstraints.PATH_USER+CommonConstraints.DELIM_SLASH+userId;
		UserDetailsResponse detailsResponse = new UserDetailsResponse();
		detailsResponse.setCreatedDate(DateUtils.convertDate(CommonConstraints.DATE_YYYY_MM_DD_HH_MM_SS_S_Z, new Date()));
		detailsResponse.setMessage(USER_IS_SUCCESSFULLY_CREATED);
		detailsResponse.setUsername((String) userObject.get(CommonConstraints.USERNAME));
		detailsResponse.setPassword(password);
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
			collection= getUserDBCollection(CommonConstraints.DB_COLLECTION_USER);
			shurtenCollection = getUserDBCollection(CommonConstraints.DB_COLLECTIONS_URL);
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
		detailsResponse.setCreatedDate((String) responseObject.get(CommonConstraints.CREATED_DATE));
		detailsResponse.setUsername((String) responseObject.get(CommonConstraints.USERNAME));
		detailsResponse.setHome_url(hostUrl+request.getContextPath()+request.getServletPath()+CommonConstraints.PATH_USER+CommonConstraints.DELIM_SLASH+detailsResponse.getUsername());
		detailsResponse.setMessage(USER_DETAILS);
		detailsResponse.setLinks(links);
		if(LOG.isDebugEnabled()){
			LOG.debug("Time Taken to execute UrlShorteningAPIServiceImpl is "+(new Date().getTime()-calculateTime));
		}
		return Response.ok().entity(detailsResponse).build();
	}
	
	/**
	 * <p>End point for user password update</p>
	 * @param username type of {@link String}
	 * @param detailsRequest type of {@link UserDetailsRequest}
	 * @return {@link Response}
	 * @throws SystemGenericException
	 * */
	@Path("/{username}")
	@PUT
	@Consumes(value={MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces(value={MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getUserUpdated(@PathParam(CommonConstraints.USERNAME)String username,UserDetailsRequest detailsRequest)throws SystemGenericException{
		long calculateTime = new Date().getTime();
		if(LOG.isDebugEnabled()){
			LOG.debug("Got a request for Shortening at "+new Date());
		}
		
		DBCollection collection = null;
		try{
			collection= getUserDBCollection(CommonConstraints.DB_COLLECTION_USER);
			LOG.info("DB Collection is located and got it from DB");
		}catch(Exception e){
			LOG.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_DB_400,CommonConstraints.ERROR_DB_400_MSG);
		}
		
		DBObject dbObject = collection.findOne(new BasicDBObject(CommonConstraints.USERNAME,username));
		if(ObjectUtils.checkNotNull(detailsRequest)){
			LOG.info("Validating the properties for "+username+" payload for password update");
			if(ObjectUtils.checkNotNull(detailsRequest.getFirstName()) && !ObjectUtils.checkEqualsString(detailsRequest.getFirstName(),(String) dbObject.get(CommonConstraints.FIRSTNAME))){
				throw new SystemGenericException(CommonConstraints.ERROR_VALIDATE_2000,CommonConstraints.ERROR_VALIDATE_2000_MSG);
			}
			if(ObjectUtils.checkNotNull(detailsRequest.getLastName()) && !ObjectUtils.checkEqualsString(detailsRequest.getLastName(),(String) dbObject.get(CommonConstraints.LASTNAME))){
				throw new SystemGenericException(CommonConstraints.ERROR_VALIDATE_2000,CommonConstraints.ERROR_VALIDATE_2000_MSG);
			}
			if(ObjectUtils.checkNotNull(detailsRequest.getUserEmail()) && !ObjectUtils.checkEqualsString(detailsRequest.getUserEmail(),(String) dbObject.get(CommonConstraints.EMAIL))){
				throw new SystemGenericException(CommonConstraints.ERROR_VALIDATE_2000,CommonConstraints.ERROR_VALIDATE_2000_MSG);
			}
			if(ObjectUtils.checkNull(detailsRequest.getUpdatedPassword())){
				throw new SystemGenericException(CommonConstraints.ERROR_VALIDATE_2000,CommonConstraints.ERROR_VALIDATE_2000_MSG);
			}
			LOG.info("Property are properly validated");
		}
		//Getting Password for updation
		LOG.info("Encrypting the password for userId "+username);
		String password = StringShortenUtils.getEncryptedPassword(detailsRequest.getUpdatedPassword());
		
		BasicDBObject basicDBObject = new BasicDBObject();
		basicDBObject.putAll(dbObject);
		basicDBObject.append(CommonConstraints.PASSWORD, password)
					 .append(CommonConstraints.IS_PASSWORD_CHANGE, true)
					 .append(CommonConstraints.UPDATED_DATE, DateUtils.convertDate(CommonConstraints.DATE_YYYY_MM_DD_HH_MM_SS_S_Z, new Date()));
		
		utils.updateCollection(CommonConstraints.DB_COLLECTION_USER,dbObject, basicDBObject);
		LOG.info("Password is properly updated for userId"+username);
		UserDetailsResponse detailsResponse = new UserDetailsResponse();
		detailsResponse.setUsername(username);
		detailsResponse.setMessage("Password changed successfully");
		if(LOG.isDebugEnabled()){
			LOG.debug("Time Taken to execute UrlShorteningAPIServiceImpl is "+(new Date().getTime()-calculateTime));
		}
		return Response.ok().status(Status.OK).entity(detailsResponse).build();
	}
	
	
	private DBCollection getUserDBCollection(String collectionName) {
		return mongo.getCollection(null, collectionName);
	}
	
	
}
