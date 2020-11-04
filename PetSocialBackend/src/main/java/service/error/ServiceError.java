package service.error;

public class ServiceError extends Exception {
	private String errorMessage;
	public ServiceError(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
}
