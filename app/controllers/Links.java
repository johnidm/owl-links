package controllers;

import java.util.List;

import models.Link;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.*;

public class Links extends Controller {
	
	Form<Link> linkForm = Form.form(Link.class);
	
	public static Result index() {
		return ok(links.render());
		
    } 	
   
    public static Result delete(Long id) {
        return TODO;       
    }
    
    public static Result edit(Long id) {
        return TODO;       
    }
    
    public static Result save() {
        return TODO;       
    }
    
    public static Result show(Long id) {
        return TODO;       
    } 
    
    public static List<Link> findLimit(int limit) {
    	return Link.find.orderBy("id desc").setMaxRows(15).findList();	
    }

}
