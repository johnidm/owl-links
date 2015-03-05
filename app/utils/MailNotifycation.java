package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import play.api.Play;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;



public class MailNotifycation {

	
	private final static String EMAIL = "johni.douglas.marangon@gmail.com";
	
	private final static String PASSWORD = "";
			
	
	private static HtmlEmail factoryEmail() throws EmailException {
		
		HtmlEmail email = new HtmlEmail();
		
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);				
		email.setAuthenticator(new DefaultAuthenticator(EMAIL, PASSWORD));
		email.setDebug(false);
		email.setSSLOnConnect(true);		
		email.setFrom(EMAIL);
		
		return email;		
		
	}
	
	
	private static List<InternetAddress> factoryEmais() throws UnsupportedEncodingException {
	
		
		List<InternetAddress> list = new ArrayList<InternetAddress>();
		list.add(new InternetAddress("johni.douglas.marangon@gmail.com", "Johni")); 		
		//list.add(new InternetAddress("danieleklein.dk@gmail.com", "Daniele Klein"));			
							
		return list; 
		
	}
	
	
	public static void send() throws EmailException, IOException, TemplateException {
		
		HtmlEmail email = factoryEmail();		
		
		email.setSubject("Newlatter Owl Links - Resumo de novos links");
		
		email.setHtmlMsg(getTemplate());
		
		email.setCc(factoryEmais());	
						
		email.send();		
		
	}
	
	
	private static String getTemplate() throws IOException, TemplateException {
		
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);		
		cfg.setDefaultEncoding("UTF-8");		
		cfg.setDirectoryForTemplateLoading( new File( Play.current().path() + "/app/templates/" ) );		
				
		Map<String, Object> data = new HashMap<String, Object>();		
				                
        data.put("links", factoryLinks());
			
		Template template = cfg.getTemplate( "newslatter.html" );		
		StringWriter out = new StringWriter();		
		template.process(data, out);		
		return (out.getBuffer().toString());
		
	}
	
	private static List<Link> factoryLinks() {
		
		List<Link> links = new ArrayList<Link>();	
		
		links.add(new Link("Johni", "google.com") );
		links.add(new Link("Douglas", "dev.com") );
				
		return links;
		
	}
	
}
