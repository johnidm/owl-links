
import java.util.concurrent.TimeUnit;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import scala.concurrent.duration.Duration;
import utils.Utils;




public class Global extends GlobalSettings {
	
	@Override
	public void onStart(Application app) {
		
		Logger.info("Owl Links iniciando...");
		
		Akka.system().scheduler().schedule(
		        Duration.create(0, TimeUnit.MILLISECONDS),   // initial delay 
		        Duration.create(5, TimeUnit.SECONDS),        // run job every 5 minutes

		        new Runnable() 
		        {
		            public void run() 
		            {		            	
		                Logger.debug("Executando tarefa " + Utils.dateNow());
		            }
		        },
		        
		        Akka.system().dispatcher()
		    );
		

	}

}
