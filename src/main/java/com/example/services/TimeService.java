package com.example.services;

import com.example.Main;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TimeService {

	DB db = Main.mongo;
	
	
	@GET
	@Path("profile/{user-id}")
    public String getProfile(
    		@PathParam("user_id") String user_id){
    	
		DBCollection coll = db.getCollection("profile");
		
    	DBObject element = coll.findOne();
    	
    	DBCursor cursor = coll.find();
    	try{
    		while(cursor.hasNext()){
    			System.out.println(cursor.next());
    		}
    	} finally{
    		cursor.close();
    	}
    	
    	return element.toString();
    }
	
	@GET
	@Path("retrieve/{label}")
    public String getContactList(
    		@PathParam("label") String label){
    	
		DBCollection coll = db.getCollection("group");
		return "";
    }
	
	@POST
	@Path("contact/{firstname}/{lastname}/{phone}/{email}")
	public Response addProfile( 
			@PathParam("firstname") String firstname,
			@PathParam("lastname") String lastname,
			@PathParam("phone") String phone,
			@PathParam("email") String email){
				
		DBCollection coll = db.getCollection("profile");
		
		BasicDBObject doc = new BasicDBObject("firstname", firstname).
				append("lastname", lastname).
				append("phone", phone).
				append("email", email);
		
		coll.insert(doc);
		
		return Response.status(201)
				.entity("addProfile is called, firstname: "+firstname+", lastname: "+lastname
						+", phone: "+phone+", email: "+email)
				.build();
	}
	
	@POST
	@Path("group/{label}/{location}")
	public Response createGroup(
			@QueryParam("label") String label,
			@QueryParam("location") String location){
		
			DBCollection coll = db.getCollection("group");
		
			BasicDBObject doc = new BasicDBObject("label", label).
				append("location", location);
		
			coll.insert(doc);
				
			return Response.status(201)
			.entity("addContact is called, label: "+label+", location: "+location)
			.build();
	}
	

}

