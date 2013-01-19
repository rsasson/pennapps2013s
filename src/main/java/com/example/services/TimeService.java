package com.example.services;

import java.util.Set;

import com.example.Main;
import com.example.models.Time;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
@Produces(MediaType.TEXT_PLAIN)
public class TimeService {

    @GET
    public String get() {
    	
    	DB db = Main.mongo;
    	
    	DBCollection coll = db.getCollection("profile");
    	DBObject element = coll.findOne();
    	
    	BasicDBObject doc = new BasicDBObject ("_firstname","William").
    									append("_lastname","Zhang").
    									append("_contact","8583665371");
    	coll.insert(doc);
    	DBCursor cursor = coll.find();
    	try{
    		while(cursor.hasNext()){
    			System.out.println(cursor.next());
    		}
    	} finally{
    		cursor.close();
    	}
    	return "";
    }

}

