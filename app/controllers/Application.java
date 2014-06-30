package controllers;

import org.joda.time.DateTime;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;

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
    
    static Form<Task> taskForm = Form.form(Task.class);
    static Form<Phone> phoneForm = Form.form(Phone.class);
    
    public static Result upload() {
    	return ok(upload.render("upload page"));
    }
    
    public static Result create() {
    	return ok(create.render());
    }
    
    public static Result index() {
      return redirect(routes.Application.tasks());
    }
    
    public static Result tasks() {
      return ok(
        index.render(Task.all(), taskForm)
      );
    }
    
    public static Result newTask() {
      Form<Task> filledForm = taskForm.bindFromRequest();
        if(filledForm.hasErrors()) {
          return badRequest(
            index.render(Task.all(), filledForm)
          );
        } else {
          Task.create(filledForm.get());
          return redirect(routes.Application.tasks());
        }
    }
    
    public static Result deleteTask(String id) {
      Task.delete(id);
      return redirect(routes.Application.tasks());
    }
    
    public static Result getPhones() {
    	return ok(Json.toJson(Phone.all()));
    }
    
    @BodyParser.Of(BodyParser.Json.class) 
    public static Result savePhone() {
		JsonNode json = request().body().asJson();
		Phone phone = new Phone();
		phone.name = json.findValue("name").asText();
		phone.id = json.findPath("id").asText();
		phone.age = json.findPath("age").asInt();
		
		Phone.create(phone);
		
		return redirect(routes.Application.getPhones());

    }
    
    
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
    
    public static Result addAccessCodeToContent(String accessCode){
    	
    	models.Content myContent = models.Content.findContent("c894268118916719"); // should get from request
    	myContent.accesscode.add(accessCode); // should get from request
    	models.Content.create(myContent);
    	return ok("accesscode added");
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
}
