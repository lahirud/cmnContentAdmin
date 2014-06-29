package models;

import java.util.*;

import play.libs.Json;
import play.modules.mongodb.jackson.MongoDB;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;

public class Phone{
  
  @Id
  @ObjectId
  public String objectId;
  public String id;
  public String name;
  public int age;

  private static JacksonDBCollection<Phone, String> coll = MongoDB.getCollection("phones", Phone.class, String.class);

  public Phone(){

  }

  public Phone(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public static List<Phone> all() {
    List<Phone> phones = Phone.coll.find().toArray();
    System.out.println("phones:" + Json.toJson(phones));
    return phones;
  }

  public static void create(Phone phone) {
	  Phone.coll.save(phone);
  }

  public static void create(String id, String name){
      create(new Phone(id, name));
  }

  public static void delete(String objectId) {
	  Phone phone = Phone.coll.findOneById(objectId);
    if (phone != null)
    	Phone.coll.remove(phone);
  }

  public static void removeAll(){
	  Phone.coll.drop();
  }

}