package com.example.services;

import java.util.Set;

import com.example.Main;
import com.example.models.Time;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TimeService {

    @GET
    public String get() {
    	
    	DB db = Main.mongo;
    	
    	DBCollection coll = db.getCollection("profile");
    	
        return coll.toString();
    }

}

