package com.skc.url.shorten.utils;

import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.skc.url.shorten.db.Mongo;
import com.skc.url.shorten.exception.SystemGenericException;

@Component("mongoUtils")
public class MongoUtils {
	static final Logger LOGGER = Logger.getLogger(MongoUtils.class);
	
	@Value("${mongo.db.name}")
	String mongoDatabase;
	
	@Value("${mongo.port}")
	Integer mongoport;
	
	@Value("${mongo.host}")
	String monogoHost;
	
	@Resource(name="mongo")
	Mongo mongo;
	
	public DBCursor getDataFromCollection(String collectionName,DBObject basicDBObject){
		DB db = getDB();
		
		DBCollection collection = getCollection(collectionName, db);
		return collection.find(basicDBObject);
	}

	public DBObject getOneObjectFromCollection(String collectionName,DBObject dbObject){
		DB db = getDB();
		DBCollection collection = getCollection(collectionName, db);
		return collection.findOne(dbObject);
	}
	
	/**
	 *References : http://stackoverflow.com/questions/10444038/mongo-db-query-in-java
	 * */
	public DBCursor getDataUsingOr(String collectionName,BasicDBList basicDBList){
		DB db = getDB();
		DBCollection collection = getCollection(collectionName, db);
		DBObject dbObject = new BasicDBObject(CommonConstraints.DELIM_DOLLER+CommonConstraints.MONGO_OR,basicDBList);
		return collection.find(dbObject);
	}

	public void updateCollection(String collectionName,DBObject searchObj,DBObject destinationObj){
		DB db = getDB();
		DBCollection collection = getCollection(collectionName, db);
		collection.update(searchObj, destinationObj);
	}
	
	public void saveCollection(String collectionName,DBObject saveObject){
		DB db = getDB();
		DBCollection collection = getCollection(collectionName, db);
		collection.save(saveObject);
	}

	private DBCollection getCollection(String collectionName, DB db) {
		DBCollection collection = db.getCollection(collectionName);
		return collection;
	}
	
	
	private DB getDB() {
		DB db = null;
		try {
			db = mongo.getDB(mongo.getMongoClient(monogoHost, mongoport), mongoDatabase);
		} catch (MongoException e) {
			LOGGER.error(e);
			throw new MongoException(Integer.valueOf(CommonConstraints.ERROR_DB_500), CommonConstraints.ERROR_DB_500_MSG);
		} catch (UnknownHostException e) {
			LOGGER.error(e);
			throw new SystemGenericException(CommonConstraints.ERROR_DB_400, CommonConstraints.ERROR_DB_400_MSG);
		}
		if(ObjectUtils.checkNull(db)){
			throw new SystemGenericException(CommonConstraints.ERROR_WEB_500, CommonConstraints.ERROR_WEB_500_MSG);
		}
		return db;
	}
	
	
}
