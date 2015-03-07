
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.EmailException;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import freemarker.template.TemplateException;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.api.Play;
import play.libs.Akka;
import scala.concurrent.duration.Duration;
import utils.MailNotifycation;
import utils.Utils;

public class Global extends GlobalSettings {
	
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
								Logger.error("Falha ao enviar os e-ails " + e.getMessage());								
							}	        		
		        			
		        			Logger.debug("[Fim] Envio de e-mails " + Utils.dateNow());
		                
		            }
		        },
		        
		        Akka.system().dispatcher()
		    );
		

	}

}
