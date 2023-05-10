package stronghold.controller.messages;

public enum MainMenuMessage {
	SUCCESS("Success!"),
	MAP_DOESNT_EXIST("Error: the specified map does not exist"),
	USERNAME_NOT_FOUND("Error: one of the entered usernames was not found"),
	CURRENT_USER_NOT_FOUND("Error: the current user must be one of the players"),
	;

	private String errorString;

	private MainMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}
}
