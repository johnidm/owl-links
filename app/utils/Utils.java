package utils;

import java.util.HashMap;
import java.util.Map;

public class Utils {
	
	
	private static Map<String, ResquestFormat> formats = new HashMap<String, ResquestFormat>()
			
			{ private static final long serialVersionUID = 1L;
			{
		        
				put("json",ResquestFormat.JSON);
				put("xml", ResquestFormat.XML);
		        
		    }};
			
	
	
	public static boolean validUrl(String url) {
		if (Utils.isEmpty(url))
			return false;
					
		return true;		
	}
	
	public static boolean validEmail(String email) {
		if (Utils.isEmpty(email))
			return false;
		
		return true;
	}
	
	public static boolean isEmpty(String value) {
		return (value == null) || (value.trim().isEmpty()); 							
	}
	
	public static boolean validRequestApiKey(String apikey) {
		return true;
	}
	
	public static boolean validRequestFormat(String format) {
		return formats.containsKey(format);
	}

	public static ResquestFormat getContentType(String format) {
		return formats.get(format);
	}
	
	
	

}
