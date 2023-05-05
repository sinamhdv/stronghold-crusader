package stronghold.controller.messages;

public enum MapEditorMenuMessage {
	SUCCESS("Success!"),
	;

	private String errorString;

	public String getErrorString() {
		return errorString;
	}

	private MapEditorMenuMessage(String errorString) {
		this.errorString = errorString;
	}
}
