package controllers;


import java.util.List;

import models.Link;



import play.mvc.Controller;
import utils.MongoDB;

public class Links extends Controller  {		
	public static List<Link> links() {
		
		return MongoDB.links();		
	}
	
	
}
