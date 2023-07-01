package stronghold.model;

public enum TradeRequestState {
	PENDING("pending"),
	ACCEPTED("Accepted"),
	REJECTED("Rejected");

	private String stateString;

	private TradeRequestState(String string) {
		this.stateString = string;
	}

	public String getStateString() {
		return stateString;
	}

	
}
