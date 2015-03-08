package models;

import java.util.Date;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonFilter("linkFilter") 
public class Link  {
	 
    public String _id;
    
    
	
	public String url;	
	public String title;	
	public String description;
	
	public List<String> tags;
	
	public Date signedup;	
	
	public String notifynews = "N";
	
}
