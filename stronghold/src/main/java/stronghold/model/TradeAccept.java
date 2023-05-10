package stronghold.model;

public class TradeAccept {
	private Government requestBy;
	private Government acceptby;
	private String message;
	private String tradeId;
	private String resourceName;
	private int amount;
	private int price;

	public TradeAccept(Government requestBy, Government acceptby, String message, String tradeId
						, String resourceName, int amount, int price) {
		this.requestBy = requestBy;
		this.acceptby = acceptby;
		this.message = message;
		this.tradeId = tradeId;
		this.resourceName = resourceName;
		this.amount = amount;
		this.price = price;
	}

	public Government getRequestBy() {
		return requestBy;
	}

	public Government getAcceptby() {
		return acceptby;
	}

	public String getMessage() {
		return message;
	}

	public String getTradeId() {
		return tradeId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public int getAmount() {
		return amount;
	}

	public int getPrice() {
		return price;
	}
	

}
