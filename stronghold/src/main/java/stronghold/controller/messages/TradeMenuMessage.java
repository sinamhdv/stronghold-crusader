package stronghold.controller.messages;

public enum TradeMenuMessage {
	EMPTY_FIELD ("you have emoty field"),
	INVALID_AMOUNT("invalid amount"),
	INVALID_PRICE("invalid price"),
	NOT_HAVING_ENOUGH_MONEY("you don't have enough money"),
	;
	private String errorMessage;

	private TradeMenuMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
