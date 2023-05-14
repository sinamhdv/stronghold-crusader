package stronghold.controller.messages;

public enum TradeMenuMessage {
	EMPTY_FIELD ("you have emoty field"),
	INVALID_AMOUNT("invalid amount"),
	INVALID_PRICE("invalid price"),
	NOT_HAVING_ENOUGH_MONEY("you don't have enough money"),
	THERE_IS_NO_TRADE_WITH_THIS_ID("there is no trade with this id"),
	SUCCESSFUL_REQUEST("your request has been create successfully"),
	NOT_HAVING_ENOUGH_RESOURCE("you don't have enough resource"),
	SUCCESSFUL_ACCEPT("you accept this trade successfully"),
	SUCCESSFUL_REJECT("you rejected this trade successfully"),
	INVALID_RESOURCE_NAME("Error: invalid resource name"),
	INVALID_RECEIVER_INDEX("Error: the receiver index is not correct"),
	REQUEST_NOT_YOURS("Error: this trade request is not yours"),
	REQUEST_ENDED("Error: this request has already been accepted/rejected"),
	NOT_ENOUGH_SENDER_GOLD("Error: the sender doesn't have enough gold"),
	NOT_ENOUGH_SENDER_RESOURCE("Error: not enough sender resource"),
	;
	private String errorMessage;

	private TradeMenuMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
