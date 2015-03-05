package controllers;

import models.Contact;
import utils.Utils;
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
        
        flash("success","Seu contato foi registrado. Obrigado!");
        
        return redirect(routes.Application.index());
    }
	
	

}
