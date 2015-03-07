package utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private final static String EMAIL = "johni.douglas.marangon@gmail.com";
	
	private final static String PASSWORD = "";
	
	private static HtmlEmail factoryHTMLEmail() throws EmailException {
		
		HtmlEmail email = new HtmlEmail();
		
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);				
		email.setAuthenticator(new DefaultAuthenticator(EMAIL, PASSWORD));
		email.setDebug(false);
		email.setSSLOnConnect(true);		
		email.setFrom(EMAIL);
		
		return email;		
		
	}
	
	private static InternetAddress factoryInternetAddress(String name, String email) {
		
		try {
			return new InternetAddress(email, name);
		} catch (UnsupportedEncodingException e) {			
			Logger.error(String.format("Falha ao incluir o email %s na lista de news", email));
			return null;
		}
		
	}
	
	
	private static List<InternetAddress> factoryListEmails() throws UnsupportedEncodingException {
			
		List<InternetAddress> list = new ArrayList<InternetAddress>();
				
		List<Newslatter> news = Newslatter.listSubscribe();
				
		news.forEach(n -> list.add( factoryInternetAddress(n.email, n.name) ) );					
											
		return list; 		
	}
	
	
	public static void send() throws EmailException, IOException, TemplateException {
		
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
		
		email.setCc(emails);	
						
		email.send();		
		
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
		
		List<Link> links = new ArrayList<Link>();
				
		return links;
		
	}
	
}
