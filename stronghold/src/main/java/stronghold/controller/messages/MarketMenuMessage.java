package stronghold.controller.messages;

public enum MarketMenuMessage {
	WRONG_ITEM("invalid item"),
	INVALID_AMOUNT("your amount should be positive");
	private String errors;

	private MarketMenuMessage(String errors) {
		this.errors = errors;
	}
	
}
