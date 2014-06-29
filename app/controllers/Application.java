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
    
    
    public static Result content() {
    	models.Content myContent = new models.Content();
    	myContent.contentId = "c" + System.nanoTime();
    	myContent.title = "test3";
    	myContent.subject = "physics7";
    	myContent.fileId = "777.jpg";
    	myContent.accesscode = new ArrayList();
    	
    	models.Content.create(myContent);
    	return ok("created");
    }
    
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

}
