package com.skc.url.shorten.db;

import java.net.UnknownHostException;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.skc.url.shorten.utils.CommonConstraints;
import com.skc.url.shorten.utils.ObjectUtils;

/**
 * <p>Concreate Class for Mongo Implementation </p>
 * @author IgnatiusCipher
 * @version 1.0
 * */
@Component("mongo")
public class MongoImpl extends AbstractMongo {
	
	@Override
	public DB getDB(MongoClient client, String db) {
		if(StringUtils.isEmpty(db)){
			db=CommonConstraints.DATABASE_NAME;
		}
		return super.getDB(client, db);
	}
	
	@Override
	public DBCollection getCollection(DB db, String collection) {
		if(ObjectUtils.checkNull(db)){
			try {
				db=this.getDB(super.getMongoClient(), null);
			} catch (MongoException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return super.getCollection(db, collection);
	}
}
