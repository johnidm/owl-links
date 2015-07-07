package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.data.format.Formats;

import play.db.ebean.Model;

@Entity
public class NotificationNewsletter extends Model {
    
    private static final long serialVersionUID = 1L;

    @Id 
    @GeneratedValue
    public Long id;
    
    @Formats.DateTime(pattern="yyyy-MM-dd hh:mm:ss")
    public Date notification = new Date();

    public NotificationNewsletter(Date date) {
      this.notification = date;
    }
    
    public static Finder<Long, NotificationNewsletter> find = new Finder<Long, NotificationNewsletter>(Long.class, NotificationNewsletter.class );
    
}
