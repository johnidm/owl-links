package controllers;


import models.Newslatter;
import utils.Utils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Newslatters extends Controller {
	
	public static final Form<Newslatter> formNewslatter = Form.form(Newslatter.class);
	
	public static Result subscribe() {	
			    	
    	Form<Newslatter> form = formNewslatter.bindFromRequest();
   	    
    	Newslatter newslatter = form.get();
    	
    	if (newslatter.name.trim().isEmpty()) {
    		flash("error", "É necessário informa o seu nome.");
    		return redirect(routes.Application.index());
    	}
    	
    	if (Utils.validEmail(newslatter.email)) {
    		flash("error", "É necessário informa o seu nome.");
    		return redirect(routes.Application.index());
    	}
    	
        if (form.hasErrors()) {  
        	flash("error", "Falha ao gravar o registro.");
        	return redirect(routes.Application.index());        	        	
        }  
                            
        newslatter.save();        
        
        flash("success","Obrigado por assinar a newslatter. Você ira receber os novos links registrados.");
        
        return redirect(routes.Application.index());
    }	

		
	
}
