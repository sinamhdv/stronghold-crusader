package stronghold.controller.messages;

public enum GameMenuMessage {
	SUCCESS("Success!"),
	INVALID_COORDINATES("Error: Invalid coordinates"),
	END_GAME("The game has ended!"),

	// government management
	INVALID_FOOD_RATE("Invalid food rate"),
	INVALID_TAX_RATE("Invalid tax rate"),
	INVALID_FEAR_RATE("Invalid fear rate"),

	// drop building/create unit
	NOT_ENOUGH_RESOURCES("Error: you don't have enough resources"),
	CONSTRUCTION_FAILED("Construction of the required object failed"),
	INCORRECT_UNIT_NAME("Error: The unit name doesn't match this barracks"),
	NOT_ENOUGH_PEASANTS("Error: not enough peasants are available"),
	
	// repair
	THERE_IS_NO_SELECTED_BUILDING("There is no selected building"),
	CANT_REPAIR("You can't repair this building"),
	THERE_ARE_ENEMY_SOLDIERS("Repair faild: there are enemy soldiers near you"),
	NOT_HAVINT_ENOUGH_RESURCE("You don't have enough resource to repair building"),
	SUCCESSFULL_REPAIR("Ripaired this building successfully"),

	// select building
	BUILDING_NOT_YOURS("Error: the requested building is not yours"),
	NO_BUILDING_FOUND("Error: No building was found in the requested cell"),
	NOT_SELECTABLE("Error: this building is not selectable"),

	// attack
	THERE_IS_NO_ENEMY_TO_FIGHT("There is no enemy to fight"),
	INVALID_DESTINATION("Invalid destination"),
	INVALID_DIRECTION("Error: invalid direction"),
	DIG_MOAT_SUCCESSFULLY("Dig moat successfully"),
	THIS_PERSON_CANT_DIG_MOAT("Error: this person can't dig moat"),

	// unit commands
	NO_UNIT_SELECTED("Error: no selected unit"),
	INVALID_STANCE("Error: invalid stance name"),

	// building commands
	BAD_SELECTED_BUILDING("Error: please select the proper building for this action"),
	GATE_CAPTURED("Error: cannot close a captured gate"),

	// siege equipment
	INVALID_EQUIPMENT_NAME("Error: Invalid equipment name"),
	BAD_UNITS_PRESENT("Error: non-enginner or enemy units are present in the selected cell"),
	NOT_ENOUGH_ENGINEERS("Error: not enough engineers"),

	// dig tunnel
	THIS_UNIT_CANT_DIG_TUNNEL("Erorr: this unit can't dig tunnel"),
	NOTHING_FOUND("Erorr: nothing found"),
	DIG_TUNNEL_SUCCESSFULLY("Dig tunnel successfully"),
	;

	private final String errorString;

	private GameMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}	
}
