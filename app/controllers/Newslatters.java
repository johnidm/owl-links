package controllers;


import play.Logger;
import play.data.DynamicForm;
import play.data.DynamicForm.Dynamic;
import models.Newslatter;
import utils.Utils;
import views.html.unsubscribe;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Newslatters extends Controller {
	
	public static final Form<Newslatter> formNewslatter = Form.form(Newslatter.class);
	
	private static DynamicForm formNewslatterUnsubscribe = Form.form();
	
	public static Result unsubscribe() {				
		return ok( unsubscribe.render(formNewslatterUnsubscribe) );		          
	}
	
	public static Result confirmaUnsubscribe() {
		
		Form<Dynamic> formUnsubscribe = formNewslatterUnsubscribe.bindFromRequest();
		
		String email = formUnsubscribe.data().get("email");
						
		if (! Utils.validEmail(email)) {
			
			flash("error", "O E-mail informado é inválido.");			
        			
			
			return badRequest( unsubscribe.render(formNewslatterUnsubscribe.fill(formUnsubscribe.data())) ); 
		}
		
		
		Newslatter newslatter = Newslatter.findByEmail(email);		
		if (newslatter == null) {		
			flash("error", "O E-mail não foi encontrado.");
			
			return badRequest( unsubscribe.render(formNewslatterUnsubscribe.fill(formUnsubscribe.data())) );
		}
		
		newslatter.subscribe = "N";
		newslatter.save();			
	   	    
	    flash("success","Sua assinatura foi cancelada.");        
        return redirect(routes.Application.index());
	}
	
	public static Result subscribe() {	
			    	
    	Form<Newslatter> form = formNewslatter.bindFromRequest();
   	    
    	Newslatter newslatter = form.get();
    	
    	if (Utils.isEmpty(newslatter.name)) {
    		flash("error", "É necessário informar seu nome.");
    		return redirect(routes.Application.index());
    	}
    	
    	if (!Utils.validEmail(newslatter.email)) {
    		flash("error", "É necessário informar um e-mail válido.");
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
