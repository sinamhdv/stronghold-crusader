package stronghold.controller.messages;

public enum MarketMenuMessage {
	WRONG_ITEM("invalid item"),
	INVALID_AMOUNT("your amount should be positive"),
	NOT_HAVING_ENOUGH_MONEY("you don't have enough money"),
	NOT_HAVING_ENOUGH_CAPACITY("you don't have enough capacity to by this item"),
	private String errors;

	private MarketMenuMessage(String errors) {
		this.errors = errors;
	}
	
}
