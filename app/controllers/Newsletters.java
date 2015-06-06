package controllers;


import java.io.IOException;

import javax.mail.internet.AddressException;

import org.apache.commons.mail.EmailException;

import freemarker.template.TemplateException;
import play.Logger;
import play.data.DynamicForm;
import play.data.DynamicForm.Dynamic;
import models.Newsletter;
import utils.MailNotifycation;
import utils.Utils;
import views.html.unsubscribe;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Newsletters extends Controller {
	
	public static final Form<Newsletter> formNewsletter = Form.form(Newsletter.class);
	
	private static DynamicForm formNewsletterUnsubscribe = Form.form();
	
	public static Result unsubscribe() {				
		return ok( unsubscribe.render(formNewsletterUnsubscribe) );		          
	}
	
	public static Result confirmaUnsubscribe() {
		
		Form<Dynamic> formUnsubscribe = formNewsletterUnsubscribe.bindFromRequest();
		
		String email = formUnsubscribe.data().get("email");
						
		if (! Utils.validEmail(email)) {
			
			flash("error", "O E-mail informado é inválido.");			
        			
			
			return badRequest( unsubscribe.render(formNewsletterUnsubscribe.fill(formUnsubscribe.data())) ); 
		}
		
		
		Newsletter newsletter = Newsletter.findByEmail(email);		
		if (newsletter == null) {		
			flash("error", "O E-mail não foi encontrado.");
			
			return badRequest( unsubscribe.render(formNewsletterUnsubscribe.fill(formUnsubscribe.data())) );
		}
		
		newsletter.subscribe = "N";
		newsletter.save();			
		
		Logger.debug("[Início] Envio de e-mails " + Utils.dateNow());       
		try {
			MailNotifycation.sendAlertNewsletterUnsub(newsletter.name, newsletter.email);
			Logger.debug("E-mail de notificação de cancelamento de newsletter enviado");
		} catch (EmailException | IOException | AddressException 
				| TemplateException e) {
			Logger.error("Falha ao enviar os e-mails " + e.getMessage());								
		
		}	        			
		Logger.debug("[Fim] Envio de e-mails " + Utils.dateNow());   
		
	    flash("success","Sua assinatura foi cancelada.");        
        return redirect(routes.Application.index());
	}
	
	public static Result subscribe() {	
			    	
    	Form<Newsletter> form = formNewsletter.bindFromRequest();
   	    
    	Newsletter newsletter = form.get();
    	
    	if (Utils.isEmpty(newsletter.name)) {
    		flash("error", "É necessário informar seu nome.");
    		return redirect(routes.Application.index());
    	}
    	
    	if (!Utils.validEmail(newsletter.email)) {
    		flash("error", "É necessário informar um e-mail válido.");
    		return redirect(routes.Application.index());
    	}
    	
        if (form.hasErrors()) {  
        	flash("error", "Falha ao gravar o registro.");
        	return redirect(routes.Application.index());        	        	
        }  
                            
        newsletter.save();        
        
		Logger.debug("[Início] Envio de e-mails " + Utils.dateNow());       
		try {
			MailNotifycation.sendAlertNewsletter(newsletter.name, newsletter.email);
			Logger.debug("E-mail de notificação de assinatura de newsletter enviado");
		} catch (EmailException | IOException | AddressException 
				| TemplateException e) {
			Logger.error("Falha ao enviar os e-mails " + e.getMessage());								
		
		}	        			
		Logger.debug("[Fim] Envio de e-mails " + Utils.dateNow());   		
	   	            
        
        flash("success","Obrigado por assinar a newsletter. Você ira receber os novos links registrados.");
        
        return redirect(routes.Application.index());
    }	

		
	
}
