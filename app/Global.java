
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.EmailException;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import views.html.page404;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import freemarker.template.TemplateException;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import play.libs.F.Promise;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import utils.MailNotifycation;
import utils.Utils;
import play.mvc.Results;
import play.mvc.Http.RequestHeader;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;



public class Global extends GlobalSettings {
	
	
	@Override
	public Promise<Result> onHandlerNotFound(RequestHeader request) {
	    return Promise.<Result> pure(Results.notFound(views.html.page404.render()));	    
	}

	@Override
	public Promise<Result> onBadRequest(RequestHeader request, String error) {
    	return Promise.<Result> pure(Results.badRequest(views.html.page500.render()));
	}
	
	@Override
	public void onStart(Application app) {

		
		Logger.info("Owl Links iniciando...");
		
				
		Akka.system().scheduler().schedule(
		        Duration.create(5, TimeUnit.MILLISECONDS),         // initial delay 
		        Duration.create(24, TimeUnit.HOURS),        // run job 

		        new Runnable() 
		        {
		            public void run() 
		            {		
		            	
		            		Logger.debug("[Início] Envio de e-mails " + Utils.dateNow());
		                
		        			try {
								MailNotifycation.send();
							} catch (EmailException | IOException
									| TemplateException e) {
								Logger.error("Falha ao enviar os e-mails " + e.getMessage());								
							}	        		
		        			
		        			Logger.debug("[Fim] Envio de e-mails " + Utils.dateNow());
		                
		            }
		        },
		        
		        Akka.system().dispatcher()
		    );
		

	}

}
