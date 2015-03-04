package controllers;

import controllers.Contacts;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
    public static Result index() {  
    	
    	
    	
        return ok(index.render(
        		controllers.Contacts.formContact, 
        		controllers.Newslatters.formNewslatter,
        		controllers.Links.findLimit(12)));                
    }
}
