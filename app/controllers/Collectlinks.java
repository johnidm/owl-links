package controllers;

import models.Collectlink;
import utils.Utils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Collectlinks extends Controller {
	
	public static final Form<Collectlink> formCollectlink = Form.form(Collectlink.class);

	
	public static Result send() {	
			    	
    	Form<Collectlink> form = formCollectlink.bindFromRequest();    	    	    	  
    	
    	Collectlink collectlink = form.get();
    	
        if (form.hasErrors()) { 
        	flash("error", "Falha ao gravar o registro.");
        	return redirect(routes.Application.index());        	
        }
        
        if (!Utils.validUrl(collectlink.link)) {        	
        	flash("error", "O link que você informou é inválido. Verifique se é um domínio válido.");
        	return redirect(routes.Application.index());        	
        }
        
        collectlink.save();        
                
        flash("success","Sua sugestão foi enviada. Em breve iremos compartilhar o seu link.");
        
        return redirect(routes.Application.index());        
    }	
	
	
	

}
