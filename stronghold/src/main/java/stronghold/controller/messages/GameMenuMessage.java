package stronghold.controller.messages;

public enum GameMenuMessage {
	SUCCESS("Success!"),
	INVALID_FOOD_RATE("Invalid food rate"),
	INVALID_TAX_RATE("Invalid tax rate"),
	INVALID_FEAR_RATE("Invalid fear rate"),
	NOT_ENOUGH_RESOURCES("Error: you don't have enough resources"),
	CONSTRUCTION_FAILED("Construction failed"),
	THERE_IS_NO_SELECTED_BUILDING("There is no selected building"),
	CANT_REPAIR("you can't repair this building"),
	;

	private final String errorString;

	private GameMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}	
}
