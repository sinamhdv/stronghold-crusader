package stronghold.controller;

import stronghold.controller.messages.TradeMenuMessage;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.model.TradeRequest;
import stronghold.model.TradeRequestState;

public class TradeMenuController {
	public static TradeMenuMessage tradeRequest(String resourceName, int amount, int price,
			String message,
			int receiverIndex) {
		int senderIndex = StrongHold.getCurrentGame().getCurrentPlayerIndex();
		TradeMenuMessage errors = tradeRequestError(resourceName, amount, price, message, receiverIndex);
		if (errors != null)
			return errors;
		else {
			ResourceType resourceType = ResourceType.getResourceByName(resourceName);
			TradeRequest trade = new TradeRequest(senderIndex, receiverIndex, resourceType, amount, price, message);
			StrongHold.getCurrentGame().addTrade(trade);
			return TradeMenuMessage.SUCCESSFUL_REQUEST;
		}
	}

	private static TradeMenuMessage tradeRequestError(String resourceName, int amount, int price,
			String message, int receiverIndex) {
		ResourceType resourceType = ResourceType.getResourceByName(resourceName);
		Government owner = StrongHold.getCurrentGame().getCurrentPlayer();
		if (resourceName.equals("") || message.equals(""))
			return TradeMenuMessage.EMPTY_FIELD;
		else if (amount <= 0)
			return TradeMenuMessage.INVALID_AMOUNT;
		else if (price < 0)
			return TradeMenuMessage.INVALID_PRICE;
		else if (owner.getGold() < price)
			return TradeMenuMessage.NOT_HAVING_ENOUGH_MONEY;
		else if (resourceType == null || !resourceType.isTradable())
			return TradeMenuMessage.INVALID_RESOURCE_NAME;
		else if (price == 0 && owner.getResourceCount(resourceType) < amount)
			return TradeMenuMessage.NOT_HAVING_ENOUGH_RESOURCE;
		else if (receiverIndex < 0 || receiverIndex >= StrongHold.getCurrentGame().getGovernments().length ||
			receiverIndex == owner.getIndex())
			return TradeMenuMessage.INVALID_RECEIVER_INDEX;
		else
			return null;
	}

	public static TradeMenuMessage tradeAccept(int id) {
		TradeMenuMessage errors = tradeAcceptErrors(id);
		Government receiver = StrongHold.getCurrentGame().getCurrentPlayer();
		Game game = StrongHold.getCurrentGame();
		if (errors != null)
			return errors;
		else {
			TradeRequest trade = StrongHold.getCurrentGame().getTradeById(id);
			Government sender = game.getGovernments()[trade.getSenderIndex()];
			if(trade.getPrice() > 0) {
				receiver.decreaseResource(trade.getResourceType(), trade.getAmount());
				sender.increaseResource(trade.getResourceType(), trade.getAmount());
				receiver.setGold(receiver.getGold() + trade.getPrice());
				sender.setGold(sender.getGold() - trade.getPrice());
			}
			else {
				receiver.increaseResource(trade.getResourceType(), trade.getAmount());
				sender.decreaseResource(trade.getResourceType(), trade.getAmount());
			}
			trade.setState(TradeRequestState.ACCEPTED);
			return TradeMenuMessage.SUCCESSFUL_ACCEPT;
		}
	}

	private static TradeMenuMessage tradeAcceptErrors(int id) {
		TradeRequest trade = StrongHold.getCurrentGame().getTradeById(id);
		if (trade == null)
			return TradeMenuMessage.THERE_IS_NO_TRADE_WITH_THIS_ID;
		Game game = StrongHold.getCurrentGame();
		Government sender = game.getGovernments()[trade.getSenderIndex()];
		if (trade.getReceiverIndex() != game.getCurrentPlayerIndex())
			return TradeMenuMessage.REQUEST_NOT_YOURS;
		else if (trade.getState() != TradeRequestState.PENDING)
			return TradeMenuMessage.REQUEST_ENDED;
		else if (trade.getPrice() > 0 &&
			game.getCurrentPlayer().getResourceCount(trade.getResourceType()) < trade.getAmount())
			return TradeMenuMessage.NOT_HAVING_ENOUGH_RESOURCE;
		else if (sender.getGold() < trade.getPrice())
			return TradeMenuMessage.NOT_ENOUGH_SENDER_GOLD;
		else if (trade.getPrice() == 0 && sender.getResourceCount(trade.getResourceType()) < trade.getAmount())
			return TradeMenuMessage.NOT_ENOUGH_SENDER_RESOURCE;
		else
			return null;
	}

	public static TradeMenuMessage tradeReject(int id) {
		TradeMenuMessage errors = tradeRejectErrors(id);
		if (errors != null) return errors;
		TradeRequest request = StrongHold.getCurrentGame().getTradeById(id);
		request.setState(TradeRequestState.REJECTED);
		return TradeMenuMessage.SUCCESSFUL_REJECT;
	}

	private static TradeMenuMessage tradeRejectErrors(int id) {
		TradeRequest trade = StrongHold.getCurrentGame().getTradeById(id);
		if (trade == null)
			return TradeMenuMessage.THERE_IS_NO_TRADE_WITH_THIS_ID;
		if (trade.getReceiverIndex() != StrongHold.getCurrentGame().getCurrentPlayerIndex())
			return TradeMenuMessage.REQUEST_NOT_YOURS;
		else if (trade.getState() != TradeRequestState.PENDING)
			return TradeMenuMessage.REQUEST_ENDED;
		return null;
	}
}
