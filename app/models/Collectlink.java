package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.data.format.Formats;

import play.db.ebean.Model;

@Entity
public class Collectlink extends Model {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue
    public Long id;
	
	@Column(length=80)	
	public String link;	
	
	@Formats.DateTime(pattern="yyyy-MM-dd hh:mm:ss")
	public Date signedup = new Date();
	
	public static Finder<Long, Collectlink> find = new Finder<Long, Collectlink>(Long.class, Collectlink.class );
	
}
