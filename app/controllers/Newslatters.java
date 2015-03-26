package controllers;


import java.io.IOException;

import javax.mail.internet.AddressException;

import org.apache.commons.mail.EmailException;

import freemarker.template.TemplateException;
import play.Logger;
import play.data.DynamicForm;
import play.data.DynamicForm.Dynamic;
import models.Newslatter;
import utils.MailNotifycation;
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
		
		Logger.debug("[Início] Envio de e-mails " + Utils.dateNow());       
		try {
			MailNotifycation.sendAlertNewslatterUnsub(newslatter.name, newslatter.email);
			Logger.debug("E-mail de notificação de cancelamento de newslatter enviado");
		} catch (EmailException | IOException | AddressException 
				| TemplateException e) {
			Logger.error("Falha ao enviar os e-mails " + e.getMessage());								
		
		}	        			
		Logger.debug("[Fim] Envio de e-mails " + Utils.dateNow());   
		
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
        
		Logger.debug("[Início] Envio de e-mails " + Utils.dateNow());       
		try {
			MailNotifycation.sendAlertNewslatter(newslatter.name, newslatter.email);
			Logger.debug("E-mail de notificação de assinatura de newslatter enviado");
		} catch (EmailException | IOException | AddressException 
				| TemplateException e) {
			Logger.error("Falha ao enviar os e-mails " + e.getMessage());								
		
		}	        			
		Logger.debug("[Fim] Envio de e-mails " + Utils.dateNow());   		
	   	            
        
        flash("success","Obrigado por assinar a newslatter. Você ira receber os novos links registrados.");
        
        return redirect(routes.Application.index());
    }	

		
	
}
