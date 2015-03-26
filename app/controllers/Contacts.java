package controllers;

import java.io.IOException;

import javax.mail.internet.AddressException;

import org.apache.commons.mail.EmailException;

import freemarker.template.TemplateException;
import models.Contact;
import utils.MailNotifycation;
import utils.Utils;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Contacts extends Controller {
	
	public static final Form<Contact> formContact = Form.form(Contact.class);
	
	public static Result submit() {	
			    	
    	Form<Contact> form = formContact.bindFromRequest();
    	
    	Contact contact = form.get();
    	
    	if (Utils.isEmpty(contact.firstname)) {        	
        	flash("error", "É necessário informar o seu nome.");
        	return redirect(routes.Application.index());        	
        }
    	
    	if (Utils.isEmpty(contact.lastname)) {        	
        	flash("error", "É necessário informar o seu sobrenome.");
        	return redirect(routes.Application.index());        	
        }
    	
    	if (!Utils.validEmail(contact.email)) {        	
        	flash("error", "É necessário informar um e-mail válido.");
        	return redirect(routes.Application.index());        	
        }
   	    	
    	if (Utils.isEmpty(contact.message)) {        	
        	flash("error", "É necessário informar a mensagem.");
        	return redirect(routes.Application.index());        	
        }
    	
        if (form.hasErrors()) {        
        	flash("error", "Falha ao gravar o registro.");
        	return redirect(routes.Application.index());
        }                       
        
        contact.save();        
        
		Logger.debug("[Início] Envio de e-mails " + Utils.dateNow());       
		try {
			MailNotifycation.sendAlertContact(contact.firstname + " " + contact.lastname,
					contact.email, contact.site, contact.message);
			Logger.debug("E-mail de notificação de contato enviado");
		} catch (EmailException | IOException | AddressException 
				| TemplateException e) {
			Logger.error("Falha ao enviar os e-mails " + e.getMessage());								
		
		}	        			
		Logger.debug("[Fim] Envio de e-mails " + Utils.dateNow());   
        
        flash("success","Seu contato foi registrado. Obrigado!");
        
        return redirect(routes.Application.index());
    }
	
	

}
