package stronghold.model;

public class Trades {
	private String resourceName;
	private int amount;
	private int price;
	private String message;
	private String id;
	private Government owner;

	public Trades(Government owner, String resourceName, int amount, int price, String message, String id) {
		this.resourceName = resourceName;
		this.amount = amount;
		this.price = price;
		this.message = message;
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
	
	public Government getOwner() {
		return owner;
	}
}
