package controllers;


import java.util.ArrayList;

import models.Link;



import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;



public class Application extends Controller {
	
    public static Result index() {  
    	
        return ok(index.render(
        		controllers.Contacts.formContact, 
        		controllers.Newsletters.formNewsletter,
        		controllers.Links.links(),
        		controllers.Links.countLinks() ));                
    }
       
    
}

