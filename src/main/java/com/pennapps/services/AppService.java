package com.pennapps.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.pennapps.Main;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
public class AppService {

	DB db = Main.mongo;
	
	@GET
	@Path("profile/{phone}")
    public Response getProfile(@PathParam("phone") String phone){
    	
		DBCollection coll = db.getCollection("profile");
		
		BasicDBObject query = new BasicDBObject("phone", phone);
		DBObject profile = null;
    	DBCursor cursor = coll.find(query);
    	
    	try{
    		while(cursor.hasNext()){
    			profile = cursor.next();
    		}
    	} finally{
    		cursor.close();
    	}
    	
    	return Response.ok(profile.toString()).build();
    }
	
	@POST
	@Path("contact/{first_name}/{last_name}/{phone}/{email}")
	public Response addProfile( 
			@PathParam("first_name") String firstname,
			@PathParam("last_name") String lastname,
			@PathParam("phone") String phone,
			@PathParam("email") String email){
				
		DBCollection coll = db.getCollection("profile");
		
		BasicDBObject doc = new BasicDBObject("first_name", firstname).
				append("last_name", lastname).
				append("phone", phone).
				append("email", email).
				append("events", new ArrayList());
		
		coll.insert(doc);
		
		return Response.status(201)
				.entity("addProfile is called, firstname: "+firstname+", lastname: "+lastname
						+", phone: "+phone+", email: "+email)
				.build();
	}
	
	@POST
	@Path("event/{phone}/{name}/{location}")
	public Response createEvent(
			@PathParam("phone") String phone,
			@PathParam("name") String name,
			@PathParam("location") String location){
		
			DBCollection events = db.getCollection("event");
			DBCollection profiles = db.getCollection("profile");
			
			BasicDBObject query = new BasicDBObject("phone", phone);

	        DBCursor cursor = profiles.find(query);
	        DBObject founder = null;

	        try {
	            while(cursor.hasNext()) {
	                founder = cursor.next();
	            }
	        } finally {
	            cursor.close();
	        }
			
			List<DBObject> list = new LinkedList<DBObject>();
			list.add(founder);
			
			BasicDBObject event = new BasicDBObject("name", name).
					append("location", location).
					append("profiles", list);
		
			events.insert(event);
			
			List<DBObject> userEvents = (List<DBObject>)founder.get("events");
			
			userEvents.add(new BasicDBObject("name", name).append("location", location));
			founder.put("events", userEvents);
			profiles.save(founder);
			
			return Response.status(201)
			.entity("addContact is called, name: "+ name +", location: "+location)
			.build();
	}

	@POST
	@Path("new/{event_name}/{first_name}/{last_name}/{phone}/{email}")
	public Response addToEvent(
			@PathParam("event_name") String event_name,
			@PathParam("first_name") String firstname,
			@PathParam("last_name") String lastname,
			@PathParam("phone") String phone,
			@PathParam("email") String email) {
		
		DBCollection events = db.getCollection("event");
		DBCollection profiles = db.getCollection("profile");
		
		BasicDBObject userQuery = new BasicDBObject("phone", phone); 
		BasicDBObject eventQuery = new BasicDBObject("name", event_name);
		
		DBObject event = null;
        DBObject user = null;
		
        DBCursor cursor = profiles.find(userQuery);
		
        try {
            while(cursor.hasNext()) {
                user = cursor.next();
            }
        } finally {
            cursor.close();
        }
		
		cursor = events.find(eventQuery);
		
		try {
            while(cursor.hasNext()) {
                event = cursor.next();
            }
        } finally {
            cursor.close();
        }
		
		//cast object to linked list?
		List<DBObject> users = (List<DBObject>)event.get("profiles");
		
		users.add(user);
		event.put("profiles", users);
		events.save(event);
		
		List<DBObject> userEvents = (List<DBObject>)user.get("events");
		
		userEvents.add(new BasicDBObject("name", event_name).append("location", event.get("location")));
		user.put("events", userEvents);
		profiles.save(user);
		
		return Response.status(201).entity("I like how much?").build();
	}
	
	@GET
	@Path("retrieve/{event_name}")
	public Response retrieveContacts(@PathParam("event_name") String event_name) {
		
		DBCollection events = db.getCollection("event");
				
		BasicDBObject query = new BasicDBObject("name", event_name);

        DBCursor cursor = events.find(query);
        DBObject event = null;

        try {
            while(cursor.hasNext()) {
                event = cursor.next();
            }
        } finally {
            cursor.close();
        }
		
        return Response.ok(event.get("profiles").toString()).build();
	}
	
	@GET
	@Path("user_events/{phone}")
	public Response getUserEvents(@PathParam("phone") String phone) {
		
		DBCollection profiles = db.getCollection("profile");
		BasicDBObject query = new BasicDBObject("phone", phone);

        DBCursor cursor = profiles.find(query);
        DBObject profile = null;

        try {
            while(cursor.hasNext()) {
                profile = cursor.next();
            }
        } finally {
            cursor.close();
        }
		
        return Response.ok(profile.get("events").toString()).build();
	}
	
}

