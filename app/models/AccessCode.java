package models;

import java.util.*;
import org.joda.time.DateTime;
import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;

public class AccessCode{
    
  /*@Id
  @ObjectId
  public String id;*/
  
  @Id
  public String accessCode;
  public Date expirayDate;
  public int redemptionQuota;
  public int noOfRedemptions;
  public String contentID;

  private static JacksonDBCollection<AccessCode, String> coll = MongoDB.getCollection("accesscode", AccessCode.class, String.class);

  public AccessCode(){

  }

  public static void create(AccessCode accesscode) {
    AccessCode.coll.save(accesscode);
  }
  
  public static AccessCode generate(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();  
		cal.setTime(date);  
		cal.add(Calendar.DATE, 30); // add 30 days  
		date = cal.getTime();
		
		AccessCode m = new AccessCode();
		m.accessCode = "cde" + System.nanoTime();
		m.expirayDate = date;
		m.redemptionQuota = 1;
		m.noOfRedemptions = 0;
		
		return m;
  }

}