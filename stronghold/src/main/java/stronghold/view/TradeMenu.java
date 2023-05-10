package stronghold.view;

import stronghold.model.Game;
import stronghold.model.StrongHold;
import stronghold.model.TradeAccept;
import stronghold.model.TradeRequest;

public class TradeMenu {
	public static void showTradeList() {
		Game currentGame = StrongHold.getCurrentGame();
		System.out.println("===============================Trade Request===================================");
		System.out.println("User     Resourcename     Amount     Price     Id     message");
		for (TradeRequest trade : currentGame.getAllTrads()) {
			System.out.println(
					trade.getOwner().getUser().getUserName() + "     " + trade.getResource().getName() + "     "
							+ trade.getAmount() + "     " + trade.getPrice() + "     " + trade.getId() + "     "
							+ trade.getMessage());
		}
		System.out.println("===============================Trade Accept===================================");
		System.out.println("request by     Accept by     Trade Id     message");
		for (TradeAccept tradeAccept : currentGame.getAllTradeAccepts()) {
			System.out.println(tradeAccept.getRequestBy().getUser().getNickName() + "     "
					+ tradeAccept.getAcceptby().getUser().getNickName() + "     " + tradeAccept.getTradeId() + "     "
					+ tradeAccept.getMessage());
		}
	}

}
