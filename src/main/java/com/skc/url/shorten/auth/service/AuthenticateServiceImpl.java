package com.skc.url.shorten.auth.service;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.glassfish.jersey.internal.util.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.skc.url.shorten.db.Mongo;
import com.skc.url.shorten.exception.SystemGenericException;
import com.skc.url.shorten.utils.CommonConstraints;
import com.skc.url.shorten.utils.MongoUtils;
import com.skc.url.shorten.utils.ObjectUtils;
import com.skc.url.shorten.utils.StringShortenUtils;

/**
 * <p> An implementation of Basic Security Type</p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
@Service("authenticationService")
public class AuthenticateServiceImpl implements AuthenticateService{
	final Logger LOG = Logger.getLogger(AuthenticateServiceImpl.class);
	
	@Resource(name="mongo")
	Mongo mongo;
	
	@Resource(name="mongoUtils")
	MongoUtils utils;
	
	public boolean authenticate(String credential) {
		if(StringUtils.isEmpty(credential)){
			return false;
		}
		
		final String encodedPassword = credential.replace("Basic ", CommonConstraints.DELIM_BLANK);
		String userNameAndPwd = null;
		try{
			byte[] decodedBytes = Base64.decode(encodedPassword.getBytes());
			userNameAndPwd = new String(decodedBytes,"UTF-8");
		}catch(IOException exception){
			exception.printStackTrace();
		}
		
		final StringTokenizer tokenizer = new StringTokenizer(userNameAndPwd, CommonConstraints.DELIM_COLLON);
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();
		DBCollection collection = mongo.getCollection(null, CommonConstraints.DB_COLLECTION_USER);
		if(ObjectUtils.checkNull(collection)){
			throw new SystemGenericException(CommonConstraints.ERROR_DB_500, CommonConstraints.ERROR_DB_500_MSG);
		}
		DBObject object = utils.getOneObjectFromCollection(CommonConstraints.DB_COLLECTION_USER, new BasicDBObject(CommonConstraints.USERNAME,username));
		boolean isPasswordChanged = (Boolean) object.get(CommonConstraints.IS_PASSWORD_CHANGE);
		
		boolean authenticationStatus=true;
		
		if(isPasswordChanged){
			String storedPassword = StringShortenUtils.decryptPassword((String) object.get(CommonConstraints.PASSWORD));
			if(isPasswordChanged){
				authenticationStatus = ObjectUtils.checkEqualsString(password, storedPassword)?true:false;
			}
		}
		return authenticationStatus;
	}

	/**
	 * <p> This method will query into mongo based on the Collection Name</p>
	 * */
	public boolean checkForShortUrl(String url) {
		DBObject basicDBObject = new BasicDBObject(CommonConstraints.SHORT_LINK,url.replace(CommonConstraints.DELIM_SLASH, ""));
		DBCursor cursor = utils.getDataFromCollection(CommonConstraints.DB_COLLECTIONS_URL, basicDBObject);
		while(cursor.hasNext()){
			DBObject dbObject = (DBObject)cursor.next();
			System.out.println(dbObject);
		}
		return cursor.size()>0?true:false;
	}

}
