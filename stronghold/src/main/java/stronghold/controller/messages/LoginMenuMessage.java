package stronghold.controller.messages;

public enum LoginMenuMessage {
	SPECIFY_REQUIRED_FIELDS("Error: please specify the required fields"),

	// login
	LOGIN_SUCCESS("User logged in successfully!"),
	USERNAME_NOT_FOUND("Username and password didn't match!"),
	INCORRECT_PASSWORD("Username and password didn't match!"),
	TRY_AFTER_DELAY("Try again after the delay"),

	// auto-login
	AUTO_LOGIN_SUCCESS("Auto-login successful"),
	AUTO_LOGIN_FAILED("Auto-login failed"),

	// forgot password
	USERNAME_DOESNT_EXIST("Error: This username doesn't exist"),
	INCORRECT_ANSWER("Error: Incorrect answer for the security question"),
	PASSWORD_CONFIRM_WRONG("Error: password confirmation must match the entered password"),
	PASSWORD_RESET_SUCCESS("Your password has been successfully reset"),
	;

	private String errorString;

	private LoginMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}
}
