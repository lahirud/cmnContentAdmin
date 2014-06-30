package controllers;

import org.joda.time.DateTime;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.*;
import play.libs.Json;
import play.mvc.*;
import play.data.*;

import models.*;

import views.html.*;

public class Application extends Controller {

//    public static Result index() {
//        return ok(index.render("Welcome to Common Content Share!"));
//    }
    
//    static Form<Task> taskForm = Form.form(Task.class);
//    static Form<Phone> phoneForm = Form.form(Phone.class);
    
//    public static Result upload() {
//    	return ok(upload.render("upload page"));
//    }
//    
//    public static Result create() {
//    	return ok(create.render());
//    }
//    
//    public static Result index() {
//      return redirect(routes.Application.tasks());
//    }
//    
//    public static Result tasks() {
//      return ok(
//        index.render(Task.all(), taskForm)
//      );
//    }
//    
//    public static Result newTask() {
//      Form<Task> filledForm = taskForm.bindFromRequest();
//        if(filledForm.hasErrors()) {
//          return badRequest(
//            index.render(Task.all(), filledForm)
//          );
//        } else {
//          Task.create(filledForm.get());
//          return redirect(routes.Application.tasks());
//        }
//    }
//    
//    public static Result deleteTask(String id) {
//      Task.delete(id);
//      return redirect(routes.Application.tasks());
//    }
//    
//    public static Result getPhones() {
//    	return ok(Json.toJson(Phone.all()));
//    }
//    
//    @BodyParser.Of(BodyParser.Json.class) 
//    public static Result savePhone() {
//		JsonNode json = request().body().asJson();
//		Phone phone = new Phone();
//		phone.name = json.findValue("name").asText();
//		phone.id = json.findPath("id").asText();
//		phone.age = json.findPath("age").asInt();
//		
//		Phone.create(phone);
//		
//		return redirect(routes.Application.getPhones());
//
//    }
    
    
    public static Result createContent() {
    	models.Content myContent = new models.Content();
    	myContent.contentId = "c" + System.nanoTime();
    	myContent.title = "test888";
    	myContent.subject = "physics7";
    	myContent.fileId = "888.jpg";
    	myContent.accesscode = new ArrayList();
    	
    	models.Content.create(myContent);
    	return ok("created");
    }
    
<<<<<<< HEAD
    public static Result addAccessCodeToContent(String accessCode){
    	
    	models.Content myContent = models.Content.findContent("c894268118916719"); // should get from request
    	myContent.accesscode.add(accessCode); // should get from request
    	models.Content.create(myContent);
    	return ok("accesscode added");
=======
    @BodyParser.Of(BodyParser.Json.class)
    public static Result createContent() {
    	JsonNode json = request().body().asJson();
    	
    	models.Content myContent = new models.Content();
    	myContent.contentId = "c" + System.nanoTime();
    	myContent.title = json.path("content").path("title").asText();
    	myContent.subject = json.path("content").path("subject").asText();
    	myContent.fileId = json.path("content").path("fileId").asText();;
    	myContent.accesscode = new ArrayList();
    	
    	boolean result = models.Content.create(myContent);
    	
    	ObjectNode response = Json.newObject();
    	
    	if (result) {
    		response.put("status", "OK");
    		return ok(response);
    	} else {
    		response.put("status", "NG");
    		response.put("message", "Error occured");
    		return badRequest(response);
    	}
    	
    	
>>>>>>> 535043e81ba088abe6bae815627b479f8716ceb4
    }
    
    @BodyParser.Of(BodyParser.Json.class)
    public static Result findContent() {
    	JsonNode json = request().body().asJson();
    	String searchTerm = json.path("searchTerm").asText();
    	
    	List<models.Content> result = models.Content.find(searchTerm);
    	
    	ObjectMapper mapper = new ObjectMapper();
    	JsonNode content = mapper.convertValue(result, JsonNode.class);
    	
    	ObjectNode response = Json.newObject();
    	
    	if (result.size() > 0) {
    		response.put("status", "OK");
    		response.put("content", content);
    		return ok(response);
    	} else {
    		response.put("status", "NG");
    		response.put("message", "No result found!");
    		return ok(response);
    	}
    	
    	
    }
    
<<<<<<< HEAD
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();  
    	cal.setTime(date);  
    	cal.add(Calendar.DATE, 30); // add 30 days  
    	date = cal.getTime();
    	
    	
    	models.AccessCode myAccessCode = new models.AccessCode();
    	myAccessCode.accessCode = "cde" + System.nanoTime();
    	myAccessCode.expirayDate = date;
    	myAccessCode.redemptionQuota = 1;
    	myAccessCode.noOfRedemptions = 0;
    	myAccessCode.contentID = "c894268118916719"; // should come from the request
    	
    	models.AccessCode.create(myAccessCode);
    	
    	addAccessCodeToContent(myAccessCode.accessCode);
    	
    	return ok(Json.toJson(myAccessCode.accessCode));
    }
    
   /* public static Result findContent(){  //find content by fileId
    	
    	models.Content myContent = models.Content.findFileId("777.jpg"); // should get from request
    	return ok(myContent.contentId);
    }*/
    
    public static Result createStudent() {
    	models.Student myStudent = new models.Student();
    	myStudent.studentId = "st" + System.nanoTime();
    	myStudent.name = "Lahiru Dharmasena";
    	myStudent.loginname = "lahirud";
    	myStudent.password = "1qaz2wsx";
    	myStudent.contents = new ArrayList();
    	
    	models.Student.create(myStudent);
    	return ok("created");
    }
    
    public static Result getStudentDetails(){
    	
    	models.Student myStudent = models.Student.findStudent("st1201750295103078"); // should get from request
    	return ok(Json.toJson(myStudent));
    }
    
    public static Result enrollContent(){
    
    	models.AccessCode myAccessCode = models.AccessCode.findAccessCode("cde895895745504247");// should get from request
    	models.Student myStudent = models.Student.findStudent("st1201750295103078"); //Shoiuld pass from the request info
    	models.Content myContent = models.Content.findContent(myAccessCode.contentID);
    	
    	if ( Integer.valueOf(myAccessCode.redemptionQuota) > Integer.valueOf(myAccessCode.noOfRedemptions)){
    		
    		myStudent.contents.add(myContent.fileId);
        	models.Student.create(myStudent);
    		myAccessCode.noOfRedemptions = myAccessCode.noOfRedemptions + 1;
    		models.AccessCode.create(myAccessCode);
    		return ok(Json.toJson(myStudent));
    	}else{
    		return ok("Access Code is invalid or Redemption quota expired");
    	}
    }
    
    public static Result addContentToStudent(String contentId){
    	
    	models.Student myStudent = models.Student.findStudent("st1201750295103078"); //Shoiuld pass from the request info
    	models.Content myContent = models.Content.findContent(contentId);
    	myStudent.contents.add(myContent.fileId);
    	models.Student.create(myStudent);
    	return ok("Content enrollment successfull");
    }
=======
    @BodyParser.Of(BodyParser.Json.class)
    public static Result generateAccessCode() {
    	JsonNode json = request().body().asJson();
    	String contentId = json.path("contentId").asText();
    	
    	models.Content content = models.Content.findOneById(contentId);
    	models.AccessCode accessCode = models.AccessCode.generate();
    	accessCode.contentID = contentId;
    	
    	content.accesscode.add(accessCode.accessCode);
    	
    	models.AccessCode.create(accessCode);
    	boolean result = models.Content.update(content);
    	
    	ObjectNode response = Json.newObject();
    	
    	if (result) {
    		response.put("status", "OK");
    		response.put("accessKey", accessCode.accessCode);
    		return ok(response);
    	} else {
    		response.put("status", "NG");
    		response.put("message", "No result found!");
    		return ok(response);
    	}
    	
    	
    }
    
    @BodyParser.Of(BodyParser.Json.class)
    public static Result getAllContents() {
    	JsonNode json = request().body().asJson();
    	
    	List<models.Content> result = models.Content.findAll();
    	
    	ObjectMapper mapper = new ObjectMapper();
    	JsonNode content = mapper.convertValue(result, JsonNode.class);
    	
    	ObjectNode response = Json.newObject();
    	
    	if (result.size() > 0) {
    		response.put("status", "OK");
    		response.put("content", content);
    		return ok(response);
    	} else {
    		response.put("status", "NG");
    		response.put("message", "No result found!");
    		return ok(response);
    	}
    	
    	
    }    
    
    
//    public static Result addAccessCode(){
//    	
//    	models.Content myContent = models.Content.findOneById("c894268118916719"); // should get from request
//    	myContent.accesscode.add("cde802085812928555"); // should get from request
//    	models.Content.create(myContent);
//    	return ok("accesscode added");
//    }
//    
//    public static Result createAccessCode() {
//    
//    	Date date = new Date();
//    	Calendar cal = Calendar.getInstance();  
//    	cal.setTime(date);  
//    	cal.add(Calendar.DATE, 30); // add 30 days  
//    	date = cal.getTime();
//    	
//    	models.AccessCode myContent = new models.AccessCode();
//    	myContent.accessCode = "cde" + System.nanoTime();
//    	myContent.expirayDate = date;
//    	myContent.redemptionQuota = 1;
//    	myContent.noOfRedemptions = 0;
//    	myContent.contentID = "c894268118916719"; // should come from the request
//    	
//    	models.AccessCode.create(myContent);
//    	return ok("Access Code created");
//    }

>>>>>>> 535043e81ba088abe6bae815627b479f8716ceb4
}
