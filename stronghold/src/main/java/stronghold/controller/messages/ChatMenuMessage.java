package stronghold.controller.messages;

public enum ChatMenuMessage {
	SUCCESS("Success"),
	EMPTY_NAME("Error: Empty name"),
	USERNAME_NOT_FOUND("Error: a username was not found"),
	ROOM_ALREADY_EXISTS("Error: a room with this name already exists"),
	;

	private String errorString;

	private ChatMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}
}
