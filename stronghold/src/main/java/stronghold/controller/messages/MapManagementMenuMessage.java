package stronghold.controller.messages;

public enum MapManagementMenuMessage {
	// new map

	// edit map
	LOAD_SUCCESS("Successfully loaded the map"),

	// delete map
	;

	private String errorString;

	private MapManagementMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}
}
