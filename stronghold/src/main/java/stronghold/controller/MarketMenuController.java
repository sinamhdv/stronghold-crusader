package stronghold.controller;

import stronghold.controller.messages.MarketMenuMessage;
import stronghold.model.Game;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;

public class MarketMenuController {
	public static MarketMenuMessage buyItem(String itemName, int amount) {
		return null; 
	}
	public static MarketMenuMessage checkBuyItemErrors (String itemName, int amount) {
		return null;
	}
	public static MarketMenuMessage getBuyItemErrores(String itemName, int amount) {
		if (ResourceType.getresourceByName(itemName) == null )
			return MarketMenuMessage.WRONG_ITEM;
		else if (amount <= 0)
			return MarketMenuMessage.INVALID_AMOUNT;
		else if (StrongHold.getCurrentGame())
	}
}
