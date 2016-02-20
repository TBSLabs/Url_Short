package com.skc.url.shorten.db;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.skc.url.shorten.utils.CommonConstraints;

public abstract class AbstractMongo implements Mongo {

	/**
	 * This method will create a MongoClient after giving the Host and Port Number
	 * 
	 * @param host as {@link String}
	 * @param port as {@link String}
	 * @return {@link MongoClient}
	 * */
	public MongoClient getMongoClient(String host, Integer port)
			throws MongoException, UnknownHostException {
		return new MongoClient(host,port);
	}

	/**
	 * This method internally takes host as localhost and default port i.e 27017 as port Number
	 * 
	 * @param db as {@link String}
	 * @return {@link MongoClient}
	 * */
	
	public MongoClient getMongoClient() throws MongoException,
			UnknownHostException {
		return new MongoClient(CommonConstraints.DEFAULT_HOST,CommonConstraints.DEFAULT_PORT);
	}

	/**
	 * This method will return the DB Object using the db name from the MongoClient
	 * 
	 * @param client as {@link MongoClient}
	 * @param db as {@link String}
	 * @return {@link DB}
	 * */
	public DB getDB(MongoClient client, String db) {
		return client.getDB(db);
	}

	/**
	 * This method will take DB and will extract the specific collection 
	 * 
	 * @param db as {@link DB}
	 * @param collection as {@link String}
	 * @return {@link DBCollection}
	 * */
	public DBCollection getCollection(DB db,String collection) {
		return db.getCollection(collection);
	}
	
}
