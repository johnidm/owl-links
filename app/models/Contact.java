package models;


import java.util.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;

import play.data.format.Formats;

import play.data.validation.Constraints.Email;

import play.db.ebean.Model;


@Entity 
public class Contact extends Model {
	
	public static Finder<Long, Contact> find = new Finder<Long, Contact>(Long.class, Contact.class );
	
	@Id
	@GeneratedValue
	public Long id;
	
	
	@Column(length=30)	
	public String firstname;
	
	
	@Column(length=50)
	public String lastname;
			
	
	@Column(length=100)
	@Email
	public String email;
		
	@Column(length=80)
	public String site;
	
	
	@Column(length=1000)
	public String message;
		
	@Formats.DateTime(pattern="yyyy-MM-dd hh:mm:ss")
	public Date signedup = new Date();

	
}
