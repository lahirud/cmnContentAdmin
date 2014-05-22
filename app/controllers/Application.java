package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;

import views.html.*;

public class Application extends Controller {

//    public static Result index() {
//        return ok(index.render("Welcome to Common Content Share!"));
//    }
    
    static Form<Task> taskForm = Form.form(Task.class);
    
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

}
