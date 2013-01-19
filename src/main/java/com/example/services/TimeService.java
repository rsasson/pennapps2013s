package com.example.services;

import java.util.Set;

import com.example.Main;
import com.example.models.Time;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TimeService {

	DB db = Main.mongo;
	
	
	@GET
    public String get(){
    	
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
    	
    	return "";
    }
	
	@POST
	public Response addContact(
			@QueryParam("firstname") String firstname,
			@QueryParam("lastname") String lastname,
			@QueryParam("phone") String phone,
			@QueryParam("email") String email){
		
		DBCollection coll = db.getCollection("profile");
		
		BasicDBObject doc = new BasicDBObject ("firstname",firstname).
				append("lastname",lastname).
				append("phone",phone).
				append("email", email);
		
		coll.insert(doc);
		
		return Response.status(200)
				.entity("addContact is called, firstname: "+firstname+", lastname: "+lastname
						+", phone: "+phone+", email: "+email)
				.build();
	}
	
	/*@POST
	public Response addGroup(
			@QueryParam("admin") String admin,
			@QueryParam("label") String label){
				
			return Response.status(200)
			.entity("addContact is called, admin: "+admin+", label: "+label)
			.build();
	}*/
	

}

