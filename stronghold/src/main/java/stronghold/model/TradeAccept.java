package stronghold.model;

public class TradeAccept {
	private Government requestBy;
	private Government acceptby;
	private String message;
	private String tradeId;
	public TradeAccept(Government requestBy, Government acceptby, String message, String tradeId) {
		this.requestBy = requestBy;
		this.acceptby = acceptby;
		this.message = message;
		this.tradeId = tradeId;
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
	
}
