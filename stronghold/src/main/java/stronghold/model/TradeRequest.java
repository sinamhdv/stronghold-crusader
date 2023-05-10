package stronghold.model;

public class TradeRequest {
	private String resourceName;
	private int amount;
	private int price;
	private String message;
	private String id;
	private Government requestBy;
	private Government acceptBy;
	public TradeRequest(Government requestBy, Government acceptBy, String resourceName, int amount, int price, String message, String id) {
		this.resourceName = resourceName;
		this.amount = amount;
		this.price = price;
		this.message = message;
		this.requestBy = requestBy;
		this.acceptBy = acceptBy;
	}

	public int getAmount() {
		return amount;
	}

	public int getPrice() {
		return price;
	}

	public String getMessage() {
		return message;
	}

	public ResourceType getResource() {
		return ResourceType.getresourceByName(resourceName);
	}
	
	public String getId() {
		return id;
	}
	
	public Government getRequestBy() {
		return requestBy;
	}

	public Government getAcceptBy() {
		return acceptBy;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setAcceptBy(Government acceptBy) {
		this.acceptBy = acceptBy;
	}
}
