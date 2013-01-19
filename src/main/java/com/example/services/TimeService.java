package com.example.services;

import java.util.Set;

import com.example.Main;
import com.example.models.Time;
import com.mongodb.DB;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
@Produces(MediaType.TEXT_PLAIN)
public class TimeService {

    @GET
    public String get() {
    	
    	DB mdb = Main.mongo;
    	
    	Set<String> collections = mdb.getCollectionNames();
    	
    	String coll = "";
    	
    	for (String c: collections) {
    		coll += c;
    	}
    	
        return coll;
    }

}

