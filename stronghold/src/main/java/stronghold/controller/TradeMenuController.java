package stronghold.controller;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import stronghold.controller.messages.TradeMenuMessage;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.StrongHold;
import stronghold.model.TradeRequest;

public class TradeMenuController {
	public static TradeMenuMessage tradeRequest(Government owner, String resourceName, int amount, int price,
			String message,
			String id) {
		Government ownerToRequest = StrongHold.getCurrentGame().getCurrentPlayer();
		TradeMenuMessage errors = tradeRequestError(ownerToRequest, resourceName, amount, price, message, id);
		if (errors != null)
			return errors;
		else {
			TradeRequest trade = new TradeRequest(ownerToRequest, null, resourceName, amount, price, message, id);
			StrongHold.getCurrentGame().addTrade(trade);
			return TradeMenuMessage.SUCCESSFUL_REQUEST;
		}
	}

	private static TradeMenuMessage tradeRequestError(Government owner, String resourceName, int amount, int price,
			String message, String id) {
		if (resourceName.equals("") || message.equals("") || id.equals(id))
			return TradeMenuMessage.EMPTY_FIELD;
		else if (amount <= 0)
			return TradeMenuMessage.INVALID_AMOUNT;
		else if (price < 0)
			return TradeMenuMessage.INVALID_PRICE;
		else if (owner.getGold() < price * amount)
			return TradeMenuMessage.NOT_HAVING_ENOUGH_MONEY;
		else
			return null;
	}

	public static TradeMenuMessage tradeAccept(String id, String message) {
		TradeMenuMessage errors = tradeAcceptErrors(id, message);
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		Game currentGame = StrongHold.getCurrentGame();
		if (errors != null)
			return errors;
		else {
			TradeRequest trade = StrongHold.getCurrentGame().getTradeById(id);
			Government ownerOfRequest = trade.getRequestBy();
			currentPlayer.decreaseResource(trade.getResource(), trade.getAmount());
			currentPlayer.setGold( currentPlayer.getGold() + trade.getPrice() * trade.getAmount());
			ownerOfRequest.setGold(ownerOfRequest.getGold() - trade.getPrice() * trade.getPrice());
			trade.setAcceptBy(currentPlayer);
			currentGame.addTradeAccept(trade);
			currentGame.removeTradeRequest(trade);
			return TradeMenuMessage.SUCCESSFUL_ACCEPT;
		}
	}

	private static TradeMenuMessage tradeAcceptErrors(String id, String message) {
		TradeRequest trade = StrongHold.getCurrentGame().getTradeById(id);
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		if (id.equals("") || message.equals(""))
			return TradeMenuMessage.EMPTY_FIELD;
		else if (StrongHold.getCurrentGame().getTradeById(id) == null)
			return TradeMenuMessage.THERE_IS_NO_TRADE_WITH_THIS_ID;
		else if (currentPlayer.getSumOfSpecificResource(trade.getResource()) < trade.getAmount())
			return TradeMenuMessage.NOT_HAVING_ENOUGH_RESOURCE;
		else
			return null;
	}

	public static String getRandomId() {
		final Random random = new Random();
		final byte[] array = new byte[5];
		random.nextBytes(array);
		final String generated = new String(array, StandardCharsets.UTF_8);
		return generated;
	}

}
