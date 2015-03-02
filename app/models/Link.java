package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.data.format.Formats;
import play.db.ebean.*;

@Entity
public class Link extends Model {
	
	@Id 
	@GeneratedValue
    public Long id;
	
	@Column(length = 80)
	public String url;
	
	@Column(length = 40)
	public String title;
	
	@Column(length = 100)
	public String description;
	
	@Formats.DateTime(pattern="yyyy-MM-dd hh:mm:ss")
	public Date signedup = new Date();	
	
	public static Finder<Long,Link> find = new Finder<Long,Link>(Long.class, Link.class);	
	
	
}
