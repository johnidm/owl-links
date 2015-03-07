package controllers;

import models.Link;

import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import play.mvc.Controller;
import play.mvc.Result;
import utils.JsonRequestError;
import utils.MongoDB;
import utils.ResquestFormat;
import utils.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;


public class ServicesV1 extends Controller {
	
	
	public static Result links(String apikey, String format) throws JsonProcessingException {
		
		if (!Utils.validRequestFormat(format)) {	
			response().setContentType(String.format("%s; charset=utf-8", ResquestFormat.JSON.contentType));
			return internalServerError(
					new ObjectMapper().writeValueAsBytes( 									
							new JsonRequestError( String.format("O formato '%s' da requisição não está implementado", format),
									"Utilize o formato .json")
							)			
					
					);
		}		
		
		if (!Utils.validRequestApiKey(apikey)) {
			response().setContentType(String.format("%s; charset=utf-8", ResquestFormat.JSON.contentType));
			
			return internalServerError(
					new ObjectMapper().writeValueAsBytes( 									
							new JsonRequestError( String.format("API Key inválida", format),
									"Acesse 'http://owl-links.herokuapp.com/#api' e gere uma nova API Key")
							)			
					
					);
		}			
				
		ResquestFormat rf = Utils.getContentType(format);				

		response().setContentType(String.format("%s; charset=utf-8", rf.contentType));
		
		switch (rf) {
		case JSON:			
			MongoCursor<Link> all = MongoDB.allLinks();
			
			ObjectMapper mapper = new ObjectMapper();			
			
		    
		    String[] ignorableFieldNames = { "_id" }; 		         
		    FilterProvider filters = new SimpleFilterProvider()
		          .addFilter("linkFilter",SimpleBeanPropertyFilter.serializeAllExcept(ignorableFieldNames));
		            
		    ObjectWriter writer = mapper.writer(filters);		    	     
		    String json = writer.writeValueAsString( all );			
			
			//all.forEach( (l) -> Logger.debug("Content: " + l.url + " > " + l.tags.toString()) );
			
			return ok(json);	
			
		case XML:
			//System.out.println(XPath.selectText("links", Link.find.where().orderBy("id").findList()));
			return status(NOT_IMPLEMENTED) ;
		
			
		default:
			return status(NOT_IMPLEMENTED) ;
		}
		
	}
			
}


