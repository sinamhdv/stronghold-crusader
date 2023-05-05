package stronghold.controller.messages;

public enum MapEditorMenuMessage {
	SUCCESS("Success!"),
	INVALID_COORDINATES("Error: Invalid coordinates"),

	// set texture
	INVALID_GROUND_TYPE("Error: invalid ground type"),
	OBJECT_FOUND("Error: cannot change ground type under a building/person/environment item"),

	;

	private String errorString;

	public String getErrorString() {
		return errorString;
	}

	private MapEditorMenuMessage(String errorString) {
		this.errorString = errorString;
	}
}
