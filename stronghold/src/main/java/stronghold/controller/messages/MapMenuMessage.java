package stronghold.controller.messages;

public enum MapMenuMessage {
	DIMENTIONS_OUT_OF_BOUNDS("Error: the entered numbers are out of bounds")
	;

	private final String errorString;

	private MapMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}
}
