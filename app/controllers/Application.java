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
import java.io.*;
import java.net.HttpCookie;

import views.html.*;

public class Application extends Controller {
    
    public static Result x_createContent() {
    	models.Content myContent = new models.Content();
    	myContent.contentId = "c" + System.nanoTime();
    	myContent.title = "test888";
    	myContent.subject = "physics7";
    	myContent.fileId = "888.jpg";
    	myContent.accesscode = new ArrayList();
    	
    	models.Content.create(myContent);
    	return ok("created");
    }
    
    public static Result addAccessCodeToContent(String accessCode){
    	
    	models.Content myContent = models.Content.findContent("c894268118916719"); // should get from request
    	myContent.accesscode.add(accessCode); // should get from request
    	models.Content.create(myContent);
    	return ok("accesscode added");
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
    
	public static Result createAccessCode() {
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
    
    public static Result uploadContent(){
    	
    	Http.MultipartFormData body = request().body().asMultipartFormData();
    	
    	Http.MultipartFormData.FilePart picture = body.getFile("file");
        if (picture != null) {
            String fileName = picture.getFilename();
            String contentType = picture.getContentType();
            File file = picture.getFile();

            ContentFile.saveFile(file, fileName, contentType);
            return ok("File uploaded");
        } else {
            flash("error", "Missing file");
            return ok("Upload failiure");
        }

    }
    
    @BodyParser.Of(BodyParser.Json.class)
    public static Result getContent(String fileName) {
    	JsonNode json = request().body().asJson();
    	//String contentId = json.path("fileId").asText();
    	
    	List<Object> result = ContentFile.getFile(fileName);
    	
    	InputStream file = (InputStream) result.get(0);
    	String contentType = (String) result.get(1);
    	
    	response().setContentType(contentType);
    	return ok(file);
    	
    }
}
