package stronghold.controller;

import stronghold.controller.messages.TradeMenuMessage;
import stronghold.model.Government;
import stronghold.model.StrongHold;
import stronghold.model.Trades;

public class TradeMenuController {
	public TradeMenuMessage tradeRequest(Government owner, String resourceName, int amount, int price, String message,
			String id) {
		Government ownerToRequest = StrongHold.getCurrentGame().getCurrentPlayer();
		TradeMenuMessage errors = tradeRequestError(ownerToRequest, resourceName, amount, price, message, id)
		if(errors != null)
			return errors;
		else {
			Trades trade = new Trades(ownerToRequest, resourceName, amount, price, message, id);
			StrongHold.addTrade(trade);
		}
	}

	private TradeMenuMessage tradeRequestError(Government owner, String resourceName, int amount, int price,
			String message, String id) {
		if (resourceName.equals("") || message.equals("") || id.equals(id))
			return TradeMenuMessage.EMPTY_FIELD;
		else if (amount <= 0)
			return TradeMenuMessage.INVALID_AMOUNT;
		else if(price < 0)
			return TradeMenuMessage.INVALID_PRICE;
		else if (owner.getGold() < price*amount)
			return TradeMenuMessage.NOT_HAVING_ENOUGH_MONEY;
		else return null;
	}

	public TradeMenuMessage tradeAccept(String id, String message) {

	}

	private TradeMenuMessage tardeAcceptErrors(String id , String message) {
		if(id.equals("") || message.equals("")) 
			return TradeMenuMessage.EMPTY_FIELD;
		else if (StrongHold.getTradeById(id) == null)
			return TradeMenuMessage.THERE_IS_NO_TRADE_WITH_THIS_ID;
		else 
			return null;
	}


}
