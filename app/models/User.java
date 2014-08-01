package models;

import java.util.*;

import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.DBQuery;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.ObjectId;
import net.vz.mongodb.jackson.WriteResult;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;

public class User {
	
	  @Id
	  public String username;
	  public String name;
	  public String passwordHash;
	  public String role;
	  public String email;

	  private static JacksonDBCollection<User, String> coll = MongoDB.getCollection("user", User.class, String.class);

	  public User(){

	  }

	  public static boolean create(User user) {
		  WriteResult<User, String> result = User.coll.save(user);
		  
		  if(result.getError() == null){
			  return true;
		  } 
		  else {
			  return false;
		  }
	  }

	  public static User findUser(String username) {
		    return User.coll.findOneById(username);
		    //return cursor.next();
	  }
	  
	  public static List<User> findAll() {
		  DBCursor<User> cursor =  User.coll.find();
		  List<User> result = new ArrayList<User>();
		  
		  while(cursor.hasNext()){
			  result.add(cursor.next());
		  }
		  
		  return result;
	  }
	  
	  
	  public static boolean delete(User user) {
		  user = User.findUser(user.username);
		  
		  if (user != null ) {
			  WriteResult<User, String> result = User.coll.remove(user);
		  
			  if(result != null && result.getError() == null){
				  return true;
			  } 
		  }

		  return false;

	  }
}