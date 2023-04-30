package stronghold.controller.messages;

public enum LoginMenuMessage {
	SUCCESSFUL("user logged in successfully!"),
	USERNAME_ERROR("Username and password didn’t match!"),
	PASSWORD_ERROR("Username and password didn’t match!"),
	USERNAME_NOT_FOUND("No user with this username was found!"),
	FORGOT_PASSWORD_SUCCESSFUL("Password recovered successsfully!"),
	FORGOT_PASSWORD_NOT_SUCCESSFUL("Password couldn't be recovered!"),
	;
	
	String errorString;

	private LoginMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}
}
