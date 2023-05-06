package stronghold.controller.messages;

public enum MapEditorMenuMessage {
	SUCCESS("Success!"),
	INVALID_COORDINATES("Error: Invalid coordinates"),

	// set texture
	INVALID_GROUND_TYPE("Error: invalid ground type"),
	FULL_CELL("Error: This cell is full. cannot perform this action here"),

	// drop *
	INVALID_DIRECTION("Error: rock direction must be n/e/w/s or random"),
	;

	private String errorString;

	public String getErrorString() {
		return errorString;
	}

	private MapEditorMenuMessage(String errorString) {
		this.errorString = errorString;
	}
}
