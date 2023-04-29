package stronghold.controller.messages;

public enum MapMenuMessage {
	INVALID_COORDINATES("Error: invalid coordinates"),
	SPECIFY_XY("Error: please specify both x and y"),
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
