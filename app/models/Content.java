package models;

import java.util.*;
import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;
import org.mongojack.*;
import com.mongodb.QueryOperators;

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

  

  public static void create(Content content) {
    Content.coll.save(content);
  }

  public static Content findContent(String contentId) {
	    return Content.coll.findOneById(contentId);
	    //return cursor.next();
  }
  
  public static Content findFileId(String fileId) {
	  return Content.coll.find(DBQuery.is("fileid", fileId));
	  
	    //return cursor.next();
  }
  
  public static void update(Content content) {
	  
	  //org.mongojack.DBQuery.Query q3 = DBQuery.is("accesscode", new org.bson.types.ObjectId(strId));
	  //Content.coll.update(content);
  }
}