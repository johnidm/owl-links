package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.avaje.ebean.Ebean;

import play.data.format.Formats;
import play.db.ebean.Model;
import play.libs.F.Option;

@Entity
public class Newslatter  extends Model {

	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue
    public Long id;
	
	@Column(length = 80)
	public String name;
	
	@Column(length=100)	
	public String email;
	
	@Column(length=1)	
	public String subscribe = "S";	
	
	@Formats.DateTime(pattern="yyyy-MM-dd hh:mm:ss")
	public Date signedup = new Date();
	
	public static Finder<Long,Newslatter> find = new Finder<Long, Newslatter>(Long.class, Newslatter.class);
	
	
	public static List<Newslatter> listSubscribe () {
		return	Ebean.find(Newslatter.class).where().eq("subscribe", "S").findList();		
	}
		
	public static Newslatter findByEmail(String email) {
		
		Newslatter newslatter = Ebean.find(Newslatter.class).where().eq("email", email).findUnique();
		
		return newslatter;
	}
	
}

