package stronghold.controller.messages;

public enum SignupAndProfileMenuMessage {
	PASSWORD_IS_SHORT("password is week : pawweord length is less than 6 characters"),
	PASSWORD_DOSNT_HAVE_UPPERCASE("Password is week : password doesn't contain uppercase letters"),
	PASSWORD_DOSNT_HAVE_LOWERCASE("password is week : password doesn't contain lowercase letters"),
	PASSWORD_DOSNET_HAVE_NUMBER("password is week : password doesn't contain numbers"),
	PASSWORD_DOSENT_HAVE_OTHER_CHAR("password is week : password doesn't contain other characters"),
	PASSWORD_IS_STRONG("password is strong"),
	EMPTY_FIELD("Empy fild"),
	INVALID_USERNAME("Invalid format for username"),
	USERNAME_EXIST("Username already exist"),
	PASSWORD_CONFIRMATION_IS_NOT_TRUE("Password confirmation is not true"),
	EMAIL_EXIST("This email already exist"),
	INVALID_EMAIL("Invalid format for email"),
	SIGNUP_SUCCESSFUL("Signup is successful"),
	CHANGE_USERNAME_SUCCESSFUL("Change userName is successful"),
	CHANGE_NICKNAME_SUCCESSFUL("Change nickname is successful"),
	CHANGE_EMAIL_SUCCESSFUL("Change email is successful"),
	CHANGE_SLOGAN_SUCCESSFUL("Change slogan is successful"),
	;
	String eror;

	private SignupAndProfileMenuMessage(String eror) {
		this.eror = eror;
	}
}
