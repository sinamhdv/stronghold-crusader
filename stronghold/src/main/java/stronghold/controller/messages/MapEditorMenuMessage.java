package stronghold.controller.messages;

public enum MapEditorMenuMessage {
	SUCCESS("Success!"),
	INVALID_COORDINATES("Error: Invalid coordinates"),

	// set texture
	INVALID_GROUND_TYPE_NAME("Error: invalid ground type name"),
	FULL_CELL("Error: This cell is full. cannot perform this action here"),

	// drop *
	INVALID_DIRECTION("Error: rock direction must be n/e/w/s or random"),
	BAD_GROUND("Error: Cannot place the requested item on this ground type")
	INVALID_TREE_NAME("Error: Invalid tree name"),
	;

	private String errorString;

	public String getErrorString() {
		return errorString;
	}

	private MapEditorMenuMessage(String errorString) {
		this.errorString = errorString;
	}
}
