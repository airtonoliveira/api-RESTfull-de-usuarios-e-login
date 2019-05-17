package br.com.airton.response;

public class MessageResponse implements  IResponse{
	
	public static final MessageResponse ERR_MISSING_FIELDS = new MessageResponse("Missing Fields", "001");
	public static final MessageResponse ERR_EMAIL_ALREADY_EXISTS = new MessageResponse("E-mail already exists", "002");
	public static final MessageResponse ERR_INVALID_FIELDS = new MessageResponse("Invalid fields", "003");
	public static final MessageResponse ERR_INVALID_EMAIL_OR_PASSWORD = new MessageResponse("Invalid e-mail or password", "004");
	public static final MessageResponse ERR_UNAUTHORIZED = new MessageResponse("Unauthorized", "005");
	public static final MessageResponse ERR_INVALID_SESSION = new MessageResponse("Unauthorized - invalid session", "006");
	public static final MessageResponse ERR_FATAL = new MessageResponse("Fatal Error", "007");

	private String message;
	private String errorCode;
	
	public MessageResponse(String message, String errorCode) {
		super();
		this.message = message;
		this.errorCode = errorCode;
	}
	
	public MessageResponse(String message) {
		super();
		this.message = message;
		this.errorCode = "000";
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
