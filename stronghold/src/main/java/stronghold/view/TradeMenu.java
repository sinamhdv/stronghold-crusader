package stronghold.view;

import stronghold.model.Game;
import stronghold.model.StrongHold;
import stronghold.model.TradeRequest;

public class TradeMenu {
	public static void showTradeList() {
		Game currentGame = StrongHold.getCurrentGame();
		System.out.println("User     Resourcename     Amount     Price     Id     message");
		for (TradeRequest trade : currentGame.getAllTrads()) {
			System.out.println(
					trade.getOwner().getUser().getUserName() + "     " + trade.getResource().getName() + "     "
							+ trade.getAmount() + "     " + trade.getPrice() + "     " + trade.getId() + "     "
							+ trade.getMessage());
		}
	}

	
}
