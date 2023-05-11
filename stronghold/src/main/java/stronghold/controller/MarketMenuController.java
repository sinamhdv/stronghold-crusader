package stronghold.controller;

import stronghold.controller.messages.MarketMenuMessage;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;

public class MarketMenuController {
	public static MarketMenuMessage buyItem(String itemName, int amount) {
		MarketMenuMessage errors = getBuyItemErrores(itemName, amount);
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		ResourceType resource = ResourceType.getResourceByName(itemName);
		if (errors != null)
			return errors;
		else {
			currentPlayer.increaseResource(resource, amount);
			currentPlayer.setGold(currentPlayer.getGold() - (amount * resource.getBuyPrice()));
			return MarketMenuMessage.SUCCESSFUL_BUY;
		}
	}

	public static MarketMenuMessage sellItem(String itemName, int amount) {
		MarketMenuMessage errors = getSellItemErrores(itemName, amount);
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		ResourceType resource = ResourceType.getResourceByName(itemName);
		if(errors != null)
			return errors;
		else {
			currentPlayer.decreaseResource(resource, amount);
			currentPlayer.setGold(currentPlayer.getGold() + resource.getSellprice()*amount);
			return MarketMenuMessage.SUCCESSFUL_SELL;
		}
	}

	private static MarketMenuMessage getBuyItemErrores(String itemName, int amount) {
		ResourceType resource = ResourceType.getResourceByName(itemName);
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		if (ResourceType.getResourceByName(itemName) == null)
			return MarketMenuMessage.WRONG_ITEM;
		else if (amount <= 0)
			return MarketMenuMessage.INVALID_AMOUNT;
		else if (currentPlayer.getGold() < ((resource).getBuyPrice() * amount))
			return MarketMenuMessage.NOT_HAVING_ENOUGH_MONEY;
		else if (currentPlayer.getCapacityOfResourceType(resource) < amount)
			return MarketMenuMessage.NOT_HAVING_ENOUGH_CAPACITY;
		else
			return null;
	}

	private static MarketMenuMessage getSellItemErrores(String itemName, int amount) {
		ResourceType resource = ResourceType.getResourceByName(itemName);
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		if (ResourceType.getResourceByName(itemName) == null )
			return MarketMenuMessage.WRONG_ITEM;
		else if (amount <= 0)
			return MarketMenuMessage.INVALID_AMOUNT;
		else if (currentPlayer.getResourceCount(resource) < amount) 
			return MarketMenuMessage.NOT_HAVING_ENOUGH_RESOURCE_TO_SELL;
		else 
			return null;
	}
}
