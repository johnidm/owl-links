package utils;

public enum ResquestFormat {
		
		JSON("application/json"), XML("text/xml");
		
	    public String contentType; 
	 
	    
		ResquestFormat(String contentType) {
			this.contentType = contentType;
		}

}
