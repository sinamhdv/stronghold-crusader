package stronghold.model;

public class Trads {
	private String resourceName;
	private int amount;
	private int price;
	private String message;

	public Trads(String resourceName, int amount, int price, String message) {
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
	
}
