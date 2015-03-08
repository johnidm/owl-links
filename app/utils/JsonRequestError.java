package utils;

public class JsonRequestError {
	
	public String error;
	public String suggestion;
	
	public JsonRequestError(String error, String suggestion) {
		this.suggestion = suggestion;
		this.error = error;
				
	}
	

}
