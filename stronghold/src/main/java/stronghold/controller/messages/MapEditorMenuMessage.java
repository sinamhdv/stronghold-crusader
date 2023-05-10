package stronghold.controller.messages;

public enum MapEditorMenuMessage {
	SUCCESS("Success!"),
	INVALID_COORDINATES("Error: Invalid coordinates"),

	// set texture
	INVALID_GROUND_TYPE_NAME("Error: invalid ground type name"),
	FULL_CELL("Error: This cell is full. cannot perform this action here"),

	// drop *
	INVALID_DIRECTION("Error: rock direction must be n/e/w/s or random"),
	BAD_GROUND("Error: Cannot place the requested item on this ground type"),
	INVALID_TREE_NAME("Error: Invalid tree name"),
	INVALID_COUNT("Error: count must be positive"),
	INVALID_UNIT_TYPE("Error: invalid unit type"),
	INVALID_BUILDING_TYPE("Error: invalid building type"),

	// select government
	INVALID_GOVERNMENT_INDEX("Error: invalid government index"),
	;

	private String errorString;

	public String getErrorString() {
		return errorString;
	}

	private MapEditorMenuMessage(String errorString) {
		this.errorString = errorString;
	}
}
