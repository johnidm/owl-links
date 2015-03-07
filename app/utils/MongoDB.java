package utils;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Link;

import org.apache.commons.collections.IteratorUtils;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import play.Logger;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDB {

	private static MongoCollection factoryCollectionLinks() {
		
		try {
		
			DB db = new MongoClient( new MongoClientURI("mongodb://owl:owl@ds051851.mongolab.com:51851/owl-links")).getDB("owl-links");
								
			Jongo jongo = new Jongo(db);
			return jongo.getCollection("links");
		
		} catch (UnknownHostException e1) {			
			Logger.error("Falha ao conectar com o MongoDB " + e1.getMessage());
			return null;
		}		
			
	}
	
	
	public static MongoCursor<Link> allLinks() {
		
		MongoCollection links = MongoDB.factoryCollectionLinks();
		
		return links.find().as(Link.class);	
		 
	}
	
	public static List<Link> links() {	
		
		//return  IteratorUtils.toList(allLinks());
		List<Link> list = new ArrayList<Link>();
		
		allLinks().forEachRemaining(list::add);
		
		//list.forEach( (l) -> Logger.debug( l._id ) );	
		
		return list;
		 
	}
	
	
	
	
	
	
}
