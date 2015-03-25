package utils;

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
import models.Newslatter;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import play.Logger;
import play.Play;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class MailNotifycation {
	//owl.links.newslatter
	//c4#bJk8Rs
	private final static String EMAIL = "busamar@gmail.com";	
	private final static String PASSWORD = "aa03070812";

	private static HtmlEmail factoryHTMLEmail() throws EmailException {
		
		HtmlEmail email = new HtmlEmail();
		
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);				
		email.setAuthenticator(new DefaultAuthenticator(EMAIL, PASSWORD));		
		email.setSSLOnConnect(true);		
		email.setFrom(EMAIL);
		
		return email;				
	}
	
		
	private static List<InternetAddress> factoryListEmails() throws UnsupportedEncodingException, AddressException {
			
		List<InternetAddress> list = new ArrayList<InternetAddress>();
				
		
		List<Newslatter> news = Newslatter.listSubscribe();
				
		news.forEach(n -> {
			InternetAddress email;
			try {
				email = new InternetAddress(n.email, n.name);
				list.add(email);
			} catch (Exception e) {			
				e.printStackTrace();
			}
		 	
		});
				
		return list; 		
	}
	
	
	public static void send() throws EmailException, IOException, TemplateException, AddressException {
		
		HtmlEmail email = factoryHTMLEmail();
					
		List<InternetAddress> emails = factoryListEmails();		
		if ( emails.isEmpty() ) {
			Logger.info("Nenhum e-mail informado na newslatter");
			return;				
		}		
		
		List<Link> links = factoryListLinks();
		if (links.isEmpty()) {
			Logger.info("Nenhum links disponível para envio");			
			return;
		}
		
		email.setSubject("Newlatter Owl Links - Resumo de novos links");		
		email.setHtmlMsg(getTemplate(links));		
		
		email.setBcc(emails);				
		
		email.send();		

		MongoDB.notifySendNews(links);		
		
	}

	
	private static void sendAlert(String subject, String htmlMsg) throws EmailException, IOException, TemplateException, AddressException {
		HtmlEmail email = factoryHTMLEmail();
		
		email.setSubject(subject);		
		email.setHtmlMsg(htmlMsg);		
		
		email.addTo(EMAIL, "Alerta", "UTF-8");				
		
		email.send();			
	}
	
	public static void sendAlertLink(String link) throws EmailException, IOException, TemplateException, AddressException {
		sendAlert("Alerta Owl Links - Sugestão de novo link", "O link " + link + " foi sugerido através do Owl Links.");
	}	
	
	public static void sendAlertNewslatter(String nome, String email) throws EmailException, IOException, TemplateException, AddressException {
		sendAlert("Alerta Owl Links - Nova assinatura de newslatter", nome + " (" + email + ")" + " assinou a newslatter do Owl Links.");
	}	
	
	public static void sendAlertContato(String nome, String email, String site, String mensagem) throws EmailException, IOException, TemplateException, AddressException {
		String html = "Novo contato adicionado: "
					+ " <b> Nome: </b> " + nome
					+ " <b> Email: </b> " + email
					+ " <b> Site: </b> " + site
					+ " <b> Mensagem: </b> " + mensagem;
		sendAlert("Alerta Owl Links - Novo cadastro de contato", html);
	}		

	private static String getTemplate(List<Link> links) throws IOException, TemplateException {
		
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);		
		cfg.setDefaultEncoding("UTF-8");		
		cfg.setDirectoryForTemplateLoading( new File(Play.application().configuration().getString( "templates.dir" ) ) );		
				
		Map<String, Object> data = new HashMap<String, Object>();		
		
        data.put("links", links);
			
		Template template = cfg.getTemplate( "newslatter.html" );		
		StringWriter out = new StringWriter();		
		template.process(data, out);		
		return (out.getBuffer().toString());
		
	}
	
	private static List<Link> factoryListLinks() {
							
		return MongoDB.subscribe();		
	}
	
}
