package com.skc.url.shorten.db;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.skc.url.shorten.exception.SystemGenericException;
import com.skc.url.shorten.utils.CommonConstraints;
import com.skc.url.shorten.utils.ObjectUtils;

/**
 * <p>Concrete Class for Mongo Implementation </p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
@Component("mongo")
public class MongoImpl extends AbstractMongo {
	final Logger LOG = Logger.getLogger(MongoImpl.class);
	
	@Value("${mongo.port}")
	Integer mongoport;
	
	@Value("${mongo.host}")
	String monogoHost;
	
	@Value("${mongo.db.name}")
	String mongoDatabase;
	
	@Override
	public DB getDB(MongoClient client, String db) {
		if(StringUtils.isEmpty(db)){
			db=mongoDatabase;
			LOG.info("Database Name Set as "+db);
		}
		LOG.info("DB is going to create ... ");
		return super.getDB(client, db);
	}
	
	@Override
	public DBCollection getCollection(DB db, String collection) {
		if(ObjectUtils.checkNull(db)){
			try {
				db=this.getDB(super.getMongoClient(monogoHost,mongoport), null);
				LOG.info("DB value was passed as null. Hence , Created default DB");
			} catch (MongoException e) {
				LOG.error(e);
				throw new SystemGenericException(CommonConstraints.ERROR_DB_400,CommonConstraints.ERROR_DB_400_MSG);
			} catch (UnknownHostException e) {
				LOG.error(e);
				throw new SystemGenericException(CommonConstraints.ERROR_DB_400,CommonConstraints.ERROR_DB_400_MSG);
			}
		}
		return super.getCollection(db, collection);
	}
}
