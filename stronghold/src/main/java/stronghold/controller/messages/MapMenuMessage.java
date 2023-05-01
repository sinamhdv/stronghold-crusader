package stronghold.controller.messages;

public enum MapMenuMessage {
	INVALID_COORDINATES("Error: invalid coordinates"),
	SUCCESS("success"),
	;

	private final String errorString;

	private MapMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}
}
