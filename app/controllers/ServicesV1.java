package controllers;

import models.Link;
import utils.Utils;
import utils.ResquestFormat;
import play.libs.*;


import play.mvc.Controller;
import play.mvc.Result;

public class ServicesV1 extends Controller {

	
	public static Result links(String apikey, String format) {
				
		if (!Utils.validRequestFormat(format)) {			
			return internalServerError("Erro");
		}		
		
		if (!Utils.validRequestApiKey(apikey)) {			
			return internalServerError("Erro");
		}			
				
		ResquestFormat rf = Utils.getContentType(format);
		
		response().setContentType(String.format("%s; charset=utf-8", format, rf.contentType));

		switch (rf) {
		case JSON:
			return ok(Json.toJson(Link.find.where().orderBy("id").findList()));	
			
		case XML:
			//System.out.println(XPath.selectText("links", Link.find.where().orderBy("id").findList()));
			return ok(  );
		
			
		default:
			return internalServerError();
		}
		
	}
			
}


