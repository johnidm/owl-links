package utils;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;

import play.Logger;



public class Utils {
	

	
	private static Map<String, ResquestFormat> formats = new HashMap<String, ResquestFormat>()
			
			{ private static final long serialVersionUID = 1L;
			{
		        
				put("json",ResquestFormat.JSON);
				//put("xml", ResquestFormat.XML);
		        
		    }};
			
	public static Integer parseIntWithDefault(String number, Integer standard) {

		Integer __number__ = standard;
		try {
            __number__ = Integer.parseInt(number);
        } catch (NumberFormatException ex) {
        	__number__ = standard;
        }      
        return __number__;

	}
	
	public static boolean validUrl(String url) {
		if (Utils.isEmpty(url))
			return false;
		
		return GenericValidator.isUrl(url);
		
	}
	
	public static boolean validEmail(String email) {
		if (Utils.isEmpty(email))
			return false;
			
		
		return GenericValidator.isEmail(email);
		
	
	}
	
	public static boolean isEmpty(String value) {
		return (value == null) || (value.trim().isEmpty()); 							
	}
	
	public static boolean validRequestApiKey(String apikey) {
		
		// padrão validade 2F-aRtD
		if (! apikey.matches("[0-9][A-Z]-[a-z][A-Z][a-z][A-Z]")) {
			return false;
		}
		try {
							
			
			String num = StringUtils.substring(apikey, 0, 1);
			String cifra = apikey.substring(1, 2);	
			
			String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			
			int pos = possible.indexOf(cifra);
			
			String api_key = num.toString() + cifra + "-" ;
			
			for (int i = 1; i <= 4; i++) {
				
				  int index = Integer.parseInt(num) * i + pos;			  	  
				  
				  if (index >= possible.length()) {
					  index = index - possible.length();
				  }
				  
				  String latter = possible.substring(index, index + 1);
				  
				  if ((i % 2) == 0) {
					  api_key += latter.toUpperCase();
					  
				  } else {
					  api_key += latter.toLowerCase();
				  }
				
			}
			
			
			
			return api_key.equals(apikey);
			
			
		} catch (Exception e) {
			Logger.debug(e.getMessage());
			return false;
		}
		
		
	}
	
	public static boolean validRequestFormat(String format) {
		return formats.containsKey(format);
	}

	public static ResquestFormat getContentType(String format) {
		return formats.get(format);
	}
	
	public static String dateNow() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		LocalDateTime dateTime = LocalDateTime.now();
		
		return dateTime.format(formatter);
		
	}

}

