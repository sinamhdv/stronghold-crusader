package stronghold.controller.messages;

public enum LoginMenuControllerMessage {
	SUCCESSFUL("user logged in successfully!"),
	USERNAME_ERROR("Username and password didn’t match!"),
	PASSWORD_ERROR("Username and password didn’t match!"),
	USERNAME_NOT_FOUND("No user with this username was found!"),
	FORGOT_PASSWORD_SUCCESSFUL("Password recovered successsfully!"),
	FORGOT_PASSWORD_NOT_SUCCESSFUL("Password couldn't be recovered!"),
	;
	String eror;

	private LoginMenuControllerMessage(String eror) {
		this.eror = eror;
	}
}
