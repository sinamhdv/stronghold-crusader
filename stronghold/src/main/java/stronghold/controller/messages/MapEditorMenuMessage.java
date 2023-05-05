package stronghold.controller.messages;

public enum MapEditorMenuMessage {
	SUCCESS("Success!"),
	INVALID_COORDINATES("Error: Invalid coordinates"),

	// set texture
	INVALID_GROUND_TYPE("Error: invalid ground type"),
	FULL_CELL("Error: This cell is full. cannot place the requested object there"),

	// drop *
	
	;

	private String errorString;

	public String getErrorString() {
		return errorString;
	}

	private MapEditorMenuMessage(String errorString) {
		this.errorString = errorString;
	}
}
