package controllers;

import models.Collectlink;
import utils.Utils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import org.apache.commons.mail.EmailException;
import freemarker.template.TemplateException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import utils.MailNotifycation;

public class Collectlinks extends Controller {
	
	public static final Form<Collectlink> formCollectlink = Form.form(Collectlink.class);

	
	public static Result send() {	
			    	
    	Form<Collectlink> form = formCollectlink.bindFromRequest();    	    	    	  
    	
    	Collectlink collectlink = form.get();
    	
    	
    	collectlink.link = StringUtils.join( "http://", collectlink.link);
    	    	
    	
        if (form.hasErrors()) { 
        	flash("error", "Falha ao gravar o registro.");
        	return redirect(routes.Application.index());        	
        }
        
        if (!Utils.validUrl( collectlink.link )) {        	
        	flash("error", String.format("A URL \"%s\" que você informou é inválida.", collectlink.link));
        	return redirect(routes.Application.index());        	
        }
        
        collectlink.save();    
        
		Logger.debug("[Início] Envio de e-mails " + Utils.dateNow());       
		try {
			MailNotifycation.sendAlertLink(collectlink.link);
			Logger.debug("E-mail de notificação de sugestão de link enviado");
		} catch (EmailException | IOException | AddressException 
				| TemplateException e) {
			Logger.error("Falha ao enviar os e-mails " + e.getMessage());								
		
		}	        			
		Logger.debug("[Fim] Envio de e-mails " + Utils.dateNow());        
                
        flash("success","Sua sugestão foi enviada. Em breve iremos compartilhar o seu link.");
        
        return redirect(routes.Application.index());        
    }	
	
	
	

}


