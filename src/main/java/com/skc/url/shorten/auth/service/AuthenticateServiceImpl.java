package com.skc.url.shorten.auth.service;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.skc.url.shorten.db.Mongo;
import com.skc.url.shorten.exception.SystemGenericException;
import com.skc.url.shorten.utils.CommonConstraints;
import com.skc.url.shorten.utils.ObjectUtils;
import com.sun.jersey.core.util.Base64;

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
	
	public boolean authenticate(String credential) {
		if(StringUtils.isEmpty(credential)){
			return true;
		}
		
		final String encodedPassword = credential.replace("Basic ", "");
		String userNameAndPwd = null;
		try{
			byte[] decodedBytes = Base64.decode(encodedPassword);
			userNameAndPwd = new String(decodedBytes,"UTF-8");
		}catch(IOException exception){
			exception.printStackTrace();
		}
		
		final StringTokenizer tokenizer = new StringTokenizer(userNameAndPwd, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();
		DBCollection collection = mongo.getCollection(null, CommonConstraints.DB_COLLECTION_USER);
		if(ObjectUtils.checkNull(collection)){
			throw new SystemGenericException(CommonConstraints.ERROR_DB_500, CommonConstraints.ERROR_DB_500_MSG);
		}
		
		DBObject object = collection.findOne(new BasicDBObject(CommonConstraints.USERNAME,username).append(CommonConstraints.PASSWORD, password));
		boolean authenticationStatus = ObjectUtils.checkNull(object)?true:false;
		return authenticationStatus;
	}

}
