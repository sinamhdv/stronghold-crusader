package stronghold.controller.messages;

public enum GameMenuMessage {
	SUCCESS("Success!"),
	INVALID_FOOD_RATE("invalid food rate"),
	INVALID_TAX_RATE("invalid tax rate"),
	NOT_ENOUGH_RESOURCES("Error: you don't have enough resources"),
	CONSTRUCTION_FAILED("Construction failed"),
	;

	private final String errorString;

	private GameMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}	
}
