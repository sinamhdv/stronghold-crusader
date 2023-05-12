package stronghold.controller.messages;

public enum GameMenuMessage {
	SUCCESS("Success!"),
	INVALID_COORDINATES("Error: Invalid coordinates"),

	INVALID_FOOD_RATE("Invalid food rate"),
	INVALID_TAX_RATE("Invalid tax rate"),
	INVALID_FEAR_RATE("Invalid fear rate"),

	// drop building/create unit
	NOT_ENOUGH_RESOURCES("Error: you don't have enough resources"),
	CONSTRUCTION_FAILED("Construction failed"),
	
	// repair
	THERE_IS_NO_SELECTED_BUILDING("There is no selected building"),
	CANT_REPAIR("you can't repair this building"),

	// select building
	BUILDING_NOT_YOURS("Error: the requested building is not yours"),
	NO_BUILDING_FOUND("Error: No building was found in the requested cell"),
	;

	private final String errorString;

	private GameMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}	
}
