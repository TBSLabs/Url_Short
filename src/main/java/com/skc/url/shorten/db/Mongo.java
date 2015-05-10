package com.skc.url.shorten.db;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * It will contain the Mongo Related Design
 * 
 * @author IgnatiusCipher(IgC)
 * */
public interface Mongo {
	/**
	 * This Method will create a MongoClient based on host,port
	 * */
	public MongoClient getMongoClient(String host,Integer port) throws MongoException,UnknownHostException;
	
	/**
	 * This Method will create the LocalHost MongoClient assuming Mongo is running on 27017 port
	 * */
	
	public MongoClient getMongoClient() throws MongoException,UnknownHostException;
	
	/**
	 * This Method will create DB Object after passing the DB Name and MongoClient into it
	 * */
	public DB getDB(MongoClient client,String db);
	
	/**
	 * We'll get the Related Collection Name Based on the DB 
	 * */
	public DBCollection getCollection(DB db,String collection);
}
