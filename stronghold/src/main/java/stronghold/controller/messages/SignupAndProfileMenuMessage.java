package stronghold.controller.messages;

public enum SignupAndProfileMenuMessage {
	PASSWORD_IS_SHORT("password is weak : password length is less than 6 characters"),
	PASSWORD_DOSNT_HAVE_UPPERCASE("Password is weak : password doesn't contain uppercase letters"),
	PASSWORD_DOSNT_HAVE_LOWERCASE("password is weak : password doesn't contain lowercase letters"),
	PASSWORD_DOSNET_HAVE_NUMBER("password is weak : password doesn't contain numbers"),
	PASSWORD_DOSENT_HAVE_OTHER_CHAR("password is weak : password must contain at least one of !@#$%^&*-_=+"),
	PASSWORD_IS_STRONG("password is strong"),
	EMPTY_FIELD("Empty field"),
	INVALID_USERNAME("Invalid format for username"),
	USERNAME_EXIST("Username already exists"),
	EMAIL_EXIST("This email already exist"),
	INVALID_EMAIL("Invalid format for email"),
	SIGNUP_SUCCESSFUL("Signup is successful"),
	CHANGE_USERNAME_SUCCESSFUL("Change userName is successful"),
	CHANGE_NICKNAME_SUCCESSFUL("Change nickname is successful"),
	CHANGE_EMAIL_SUCCESSFUL("Change email is successful"),
	CHANGE_SLOGAN_SUCCESSFUL("Change slogan is successful"),
	OLD_PASSWORD_WRONG("Current password is incorrect!"),
	OLD_AND_NEW_PASSWORD_ARE_EQUAL("Please enter a new password!"),
	REMOVE_SLOGAN_SUCCESSFUL("Slogan remove successfuly"),
	CHANGE_PASSWORD_SUCCESS("Password changed successfully"),
	;

	private final String errorString;

	private SignupAndProfileMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}
}
