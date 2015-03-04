package controllers;

import models.Collectlink;
import utils.Utils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import org.apache.commons.lang3.StringUtils;

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
                
        flash("success","Sua sugestão foi enviada. Em breve iremos compartilhar o seu link.");
        
        return redirect(routes.Application.index());        
    }	
	
	
	

}


