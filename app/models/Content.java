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
<<<<<<< HEAD
import org.mongojack.*;
import com.mongodb.QueryOperators;
=======
>>>>>>> 535043e81ba088abe6bae815627b479f8716ceb4

import javax.persistence.*;

public class Content{
    
  /*@Id
  @ObjectId
  public String id;*/
  @Id
  public String contentId;
  public String title;
  public String subject;
  public String fileId;
  public List<String> accesscode;

  private static JacksonDBCollection<Content, String> coll = MongoDB.getCollection("content", Content.class, String.class);

  public Content(){

  }

  

  public static boolean create(Content content) {
	  WriteResult<Content, String> result = Content.coll.save(content);
	  
	  if(result.getError() == null){
		  return true;
	  } 
	  else {
		  return false;
	  }
  }

<<<<<<< HEAD
  public static Content findContent(String contentId) {
=======
  public static Content findOneById(String contentId) {
>>>>>>> 535043e81ba088abe6bae815627b479f8716ceb4
	    return Content.coll.findOneById(contentId);
	    //return cursor.next();
  }
  
<<<<<<< HEAD
  /*public static Content findFileId(String fileId) {
	  return Content.coll.find(DBQuery.is("fileid", fileId));
	  
	    //return cursor.next();
  }*/
  
  public static void update(Content content) {
=======
  public static List<Content> find(String searchTerm) {
	  DBCursor<Content> cursor =  Content.coll.find().is("title", searchTerm);
	  List<Content> result = new ArrayList<Content>();
	  
	  while(cursor.hasNext()){
		  result.add(cursor.next());
	  }
	  
	  return result;
  }
  
  public static List<Content> findAll() {
	  DBCursor<Content> cursor =  Content.coll.find();
	  List<Content> result = new ArrayList<Content>();
	  
	  while(cursor.hasNext()){
		  result.add(cursor.next());
	  }
	  
	  return result;
  }
  
  public static boolean update(Content content) {
	  WriteResult<Content, String> result = Content.coll.save(content);
>>>>>>> 535043e81ba088abe6bae815627b479f8716ceb4
	  
	  if(result.getError() == null){
		  return true;
	  } 
	  else {
		  return false;
	  }
  }
}