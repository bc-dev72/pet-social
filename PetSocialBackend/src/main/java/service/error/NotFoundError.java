package service.error;

public class NotFoundError extends Exception{
	private String errorMessage;
	
	public NotFoundError(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
}
