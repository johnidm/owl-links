
package utils;

import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import models.Link;
import models.Newsletter;
import models.NotificationNewsletter;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import play.Logger;
import play.Play;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import utils.Utils;


public class MailNotifycation {

	private final static String EMAIL = System.getenv("OWLLINKS_SMTP_EMAIL");
	private final static String PASSWORD = System.getenv("OWLLINKS_SMTP_PASSWORD");
	
	private final static String HOSTNAME = System.getenv("OWLLINKS_SMTP_HOSTNAME");
	private final static Integer PORT = Utils.parseIntWithDefault(System.getenv("OWLLINKS_SMTP_PORT"), 9000);

	private final static String EMAIL_NOTIFICACAO = System.getenv("OWLLINKS_NOTIFICATION_EMAIL");


	private static Boolean existsConfigSMPT() {
		Boolean anyIsEmpty = Utils.isEmpty(EMAIL) || Utils.isEmpty(PASSWORD) 
				|| Utils.isEmpty(HOSTNAME) || Utils.isEmpty(EMAIL_NOTIFICACAO);
		
		return !anyIsEmpty;
	}

	private static HtmlEmail factoryHTMLEmail() throws EmailException {
		
		HtmlEmail email = new HtmlEmail();

		email.setSSLOnConnect(true);			
		email.setDebug(true);  
		email.setHostName(HOSTNAME);
		email.setSmtpPort(PORT);			
		email.setAuthenticator(new DefaultAuthenticator(EMAIL, PASSWORD));				

		email.setFrom(EMAIL);
		
		return email;				
	}
	
		
	private static List<InternetAddress> factoryListEmails() throws UnsupportedEncodingException, AddressException {
			
		List<InternetAddress> list = new ArrayList<InternetAddress>();
				
		
		List<Newsletter> news = Newsletter.listSubscribe();
				
		news.forEach(n -> {
			InternetAddress email;
			try {
				email = new InternetAddress(n.email, n.name);

				Logger.info("e-mail " + n.email);

				list.add(email);
			} catch (Exception e) {			
				Logger.error("Falha ao criar a lista de e-mails " + e.getMessage());
			}
		 	
		});

		return list; 		
	}
	
	
	public static void send() throws EmailException, IOException, TemplateException, AddressException {
			
					
		List<InternetAddress> emails = factoryListEmails();		
		if ( emails.isEmpty() ) {
			Logger.info("Nenhum e-mail informado na newsletter");
			return;				
		}		
		
		List<Link> links = factoryListLinks();
		if (links.isEmpty()) {
			Logger.info("Nenhum links disponível para envio");			
			return;
		}

		// Insert a regitry of notification
		new NotificationNewsletter(new Date()).save();
		System.out.println("!");

		// Count notify
		long newsCount = NotificationNewsletter.find.all().size();

		emails.forEach(email -> {
			try {
				sendEmail(links, email, newsCount);
				
				MongoDB.notifySendNews(links);	
				
				Logger.debug("E-mails enviados " + Utils.dateNow());
				
			} catch (Exception e) {								
				Logger.error("Falha ao criar enviar email " + e.getMessage());
			}	 	
		});
				
	}
	
	private static void sendEmail(List<Link> links, InternetAddress email, long newsCount) throws EmailException, IOException, TemplateException {
		
		if (existsConfigSMPT()) {
			HtmlEmail service = factoryHTMLEmail();
			service.setSubject("Owl Links - Resumo de novos links - " + String.format("#%d", newsCount));		
			service.setHtmlMsg(processTemplate(links, email.getPersonal()));		
			service.addTo(email.getAddress(), email.getPersonal());	
			service.send();
		} else {
			Logger.error("Confogurações de envio de email não definidas - sendEmail");
		}			
	}

	
	private static void sendAlert(String subject, String htmlMsg) throws EmailException, IOException, TemplateException, AddressException {
		
		if (existsConfigSMPT()) {
		
			HtmlEmail email = factoryHTMLEmail();
			email.setSubject(subject);		
			email.setHtmlMsg(htmlMsg);		
			
			email.addTo(EMAIL_NOTIFICACAO, "Alerta", "UTF-8");				
			
			email.send();			

		} else {
			Logger.error("Confogurações de envio de email não definidas - sendAlert");
		}
	}
	
	public static void sendAlertLink(String link) throws EmailException, IOException, TemplateException, AddressException {
		sendAlert("Alerta Owl Links - Sugestão de novo link", "O link " + link + " foi sugerido através do Owl Links.");
	}	
	
	public static void sendAlertNewsletter(String nome, String email) throws EmailException, IOException, TemplateException, AddressException {
		sendAlert("Alerta Owl Links - Nova assinatura de newsletter", nome + " (" + email + ")" + " assinou a newsletter do Owl Links.");
	}	
	
	public static void sendAlertNewsletterUnsub(String nome, String email) throws EmailException, IOException, TemplateException, AddressException {
		sendAlert("Alerta Owl Links - Assinatura de newsletter cancelada", nome + " (" + email + ")" + " cancelou a assinatura da newsletter do Owl Links.");
	}		
	
	public static void sendAlertContact(String nome, String email, String site, String mensagem) throws EmailException, IOException, TemplateException, AddressException {
		String html = "Novo contato: "
					+ " <br/> <br/> <b> Nome: </b> " + nome
					+ " <br/> <b> Email: </b> " + email
					+ " <br/> <b> Site: </b> " + site
					+ " <br/> <b> Mensagem: </b> <br/> " + mensagem;
		sendAlert("Alerta Owl Links - Novo cadastro de contato", html);
	}		

	private static Template getTemplateEngine() throws IOException, TemplateException {
		
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);		
		cfg.setDefaultEncoding("UTF-8");		
		cfg.setDirectoryForTemplateLoading( new File(Play.application().configuration().getString( "templates.dir" ) ) );

		Template template = cfg.getTemplate( "newsletter.html" );

		return template;

	}

	private static String processTemplate(List<Link> links, String name) throws IOException, TemplateException {
		
		Template template = getTemplateEngine();			
				
		Map<String, Object> data = new HashMap<String, Object>();				

        data.put("links", links);
        data.put("name", name);	
				
		StringWriter out = new StringWriter();		

		template.process(data, out);		
		return (out.getBuffer().toString());
		
	}
	
	private static List<Link> factoryListLinks() {
							
		return MongoDB.subscribe();		
	}
	
}
