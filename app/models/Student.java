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

public class Student{
    
  @Id
  public String studentId;
  public String name;
  public String loginname;
  public String password;
  public List<String> contents;

  private static JacksonDBCollection<Student, String> coll = MongoDB.getCollection("student", Student.class, String.class);

  public Student(){

  }

  public static void create(Student student) {
    Student.coll.save(student);
  }

  public static Student findStudent(String studentId) {
	    return Student.coll.findOneById(studentId);
	    //return cursor.next();
  }
}