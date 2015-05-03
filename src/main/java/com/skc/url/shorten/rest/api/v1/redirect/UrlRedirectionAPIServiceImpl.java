package com.skc.url.shorten.rest.api.v1.redirect;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.skc.url.shorten.db.Mongo;
import com.skc.url.shorten.db.MongoImpl;
import com.skc.url.shorten.utils.CommonConstraints;

/**
 * <p>End point Service Where the call will go to the external System</p>
 * @author IgnatiusCipher(IgC)
 * @version 1.0
 * */
@Path("/{shortenPath}")
public class UrlRedirectionAPIServiceImpl {
	
	/**
	 * <p>This method will redirect the request to the respected third party System </p>
	 * @param response type of {@link HttpServletResponse}
	 * @param shortenPath type of {@link String}
	 * */
	@GET
	public void urlRedirected(@Context HttpServletResponse response,@PathParam(CommonConstraints.SHORTEN_PATH) String shortenPath) throws IOException{
		Mongo mongo = new MongoImpl();
		DBCollection collection = mongo.getCollection(null, CommonConstraints.DB_COLLECTIONS);
		
		DBObject dbObject = new BasicDBObject(CommonConstraints.SHORT_LINK,shortenPath);
		DBObject dbResponse = collection.findOne(dbObject);
		Integer integer = (Integer) dbResponse.get(CommonConstraints.REDIRECTED_NUMBER);
		
		BasicDBObject updatedObject = new BasicDBObject(CommonConstraints.UPDATED_DATE,new Date());
		updatedObject.putAll(dbResponse);
		updatedObject.append(CommonConstraints.REDIRECTED_NUMBER, ++integer);
		collection.update(dbObject, updatedObject);
		
		response.sendRedirect((String) dbResponse.get(CommonConstraints.REQUEST_URL));
		
		
	}
}
