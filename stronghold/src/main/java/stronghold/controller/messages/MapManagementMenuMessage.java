package stronghold.controller.messages;

public enum MapManagementMenuMessage {
	// new map
	CREATE_SUCCESS("New map created successfully"),
	MAP_ALREADY_EXISTS("Error: This map already exists"),
	INVALID_GOVERNMENTS_COUNT("Error: Invalid governments count: must be between 1 and 8"),
	INVALID_DIMENTIONS("Error: Invalid dimentions"),	// TODO: add dimentions limit to error message

	// edit map
	LOAD_SUCCESS("Successfully loaded the map"),
	MAP_NOT_FOUND("Error: The requested map was not found"),

	// delete map
	DELETE_SUCCESS("Successfully deleted the map"),
	;

	private String errorString;

	private MapManagementMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}
}
