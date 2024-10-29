package mainApp;

public class ImproperlyFormattedLevelException extends Exception{
	
	private String message;
	
	public ImproperlyFormattedLevelException(String errorMessage) {
		this.message = "Improperly formatted level exception: " + errorMessage;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}