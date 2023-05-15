package stronghold.model;

public class TradeRequest {
	private static int lastTradeId = 0;

	private final ResourceType resourceType;
	private final int amount;
	private final int price;
	private final String message;
	private final int id;
	private final int senderIndex;
	private final int receiverIndex;

	private TradeRequestState state = TradeRequestState.PENDING;
	private boolean isSeen = false;

	public TradeRequest(int senderIndex, int receiverIndex, ResourceType resourceType, int amount, int price, String message) {
		this.resourceType = resourceType;
		this.amount = amount;
		this.price = price;
		this.message = message;
		this.senderIndex = senderIndex;
		this.receiverIndex = receiverIndex;
		this.id = ++lastTradeId;
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
	
	public int getId() {
		return id;
	}
	
	public int getReceiverIndex() {
		return receiverIndex;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public int getSenderIndex() {
		return senderIndex;
	}

	public boolean isSeen() {
		return isSeen;
	}

	public void setSeen(boolean isSeen) {
		this.isSeen = isSeen;
	}

	public TradeRequestState getState() {
		return state;
	}

	public void setState(TradeRequestState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		String result = id + ") [" + senderIndex + " -> " + receiverIndex + "]: ";
		result += "resource=" + resourceType.getName() + ", amount=" + amount + ", price=" + price;
		result += ", status=" + state + ", message=" + message;
		return result;
	}
}
