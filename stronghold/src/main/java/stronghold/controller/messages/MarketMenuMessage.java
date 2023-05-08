package stronghold.controller.messages;

public enum MarketMenuMessage {
	WRONG_ITEM("invalid item"),
	INVALID_AMOUNT("your amount should be positive"),
	NOT_HAVING_ENOUGH_MONEY("you don't have enough money"),
	NOT_HAVING_ENOUGH_CAPACITY("you don't have enough capacity to by this item"),
	SUCCESSFUL_BUY("your purchase was successful"),
	NOT_HAVING_ENOUGH_RESOURCE_TO_SELL("you don't have enough resource to sell"),
	SUCCESSFUL_SELL("the item has been successfully sold");
	private String errors;

	private MarketMenuMessage(String errors) {
		this.errors = errors;
	}

	public String getErrorString() {
		return errors;
	}
	
}
