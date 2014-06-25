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
    
    
    public static Result content() {
    	models.Content myContent = new models.Content();
    	myContent.contentId = "c" + System.nanoTime();
    	myContent.title = "test3";
    	myContent.subject = "physics2";
    	myContent.fileId = "666.jpg";
    	myContent.accesscode = new ArrayList();
    	
    	models.Content.create(myContent);
    	return ok("created");
    }
    
    public static Result addAccessCode(){
    	
    	models.Content myContent = models.Content.find("c804033125905038"); // should get from request
    	myContent.accesscode.add("cde802085812928808");
    	//models.Content.update("c804033125905038", "cde802085812928808"); // contentID & accesscode should get from request
    	/*models.Content myContent = new models.Content();
    	myContent.contentId = "c804033125905038"; // should get from the request
    	myContent.title = "test2";
    	myContent.subject = "physics";
    	myContent.fileId = "678.jpg";
    	myContent.accesscode*/
    	
    	models.Content.update(myContent);
    	return ok("updated");
    }
    
    public static Result createAccessCode() {
    
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();  
    	cal.setTime(date);  
    	cal.add(Calendar.DATE, 30); // add 30 days  
    	date = cal.getTime();
    	
    	models.AccessCode myContent = new models.AccessCode();
    	myContent.accessCode = "cde" + System.nanoTime();
    	myContent.expirayDate = date;
    	myContent.redemptionQuota = 1;
    	myContent.noOfRedemptions = 0;
    	myContent.contentID = "C1234567893"; // should come from the request
    	
    	models.AccessCode.create(myContent);
    	return ok("Access Code created");
    }

}
