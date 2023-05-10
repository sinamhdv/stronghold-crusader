package stronghold.view;

import java.util.HashMap;

import stronghold.controller.TradeMenuController;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.StrongHold;
import stronghold.model.TradeRequest;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class TradeMenu {
	public static void showTradeListAndHistory(boolean isHistory) {
		Game currentGame = StrongHold.getCurrentGame();
		Government currentPlayer = currentGame.getCurrentPlayer();
		if (isHistory)
			System.out.println("------------------------------Your Trade Request--------------------------------");
		else
			System.out.println("===============================Trade Request===================================");
		System.out.println("User     Resourcename     Amount     Price     Id     message");
		for (TradeRequest trade : currentGame.getAllTrads()) {
			if ((isHistory && trade.getRequestBy() == currentPlayer) || !isHistory) {
				System.out.println(
						trade.getRequestBy().getUser().getUserName() + "     " + trade.getResource().getName() + "     "
								+ trade.getAmount() + "     " + trade.getPrice() + "     " + trade.getId() + "     "
								+ trade.getMessage());
			}
		}
		if (isHistory)
			System.out.println("------------------------------Your Trade Accept--------------------------------");
		else
			System.out.println("===============================Trade Accept===================================");
		System.out.println("request by     Accept by     Trade Id     ResourceName     Amount     Price");
		for (TradeRequest tradeAccept : currentGame.getAllTradeAccepts()) {
			if ((isHistory && tradeAccept.getAcceptBy() == currentPlayer) || !isHistory) {
				System.out.println(tradeAccept.getRequestBy().getUser().getNickName() + "     "
						+ tradeAccept.getAcceptBy().getUser().getNickName() + "     " + tradeAccept.getId() + "     "
						+ tradeAccept.getMessage() + "     " + tradeAccept.getResourceName() + "     "
						+ tradeAccept.getAmount() + "     " + tradeAccept.getPrice());
			}
		}
	}

	public static void run() {
		System.out.println("======[Trade Menu]======");

		while (true) {
			String line = MainMenu.getScanner().nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;

			if ((matcher = CommandParser.getMatcher(inputTokens, Command.TRADE_REQUEST)) != null)
				runTradeRequest(matcher);
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.TRADE_ACCEPT)) != null)
				runTradeAccept(matcher);
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.TRADE_HISTORY)) != null)
				showTradeListAndHistory(true);
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.TRADE_LIST)) != null)
				showTradeListAndHistory(false);
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.EXIT)) != null) {
				System.out.println("Exitting...");
				break;
			} else
				System.out.println("Error: Invalid command");
		}
	}

	public static void runTradeRequest(HashMap<String, String> matcher) {
		String id = TradeMenuController.getRandomId();
		System.out.println(
				TradeMenuController.tradeRequest(matcher.get("resourceType"), Integer.parseInt(matcher.get("amount")),
						Integer.parseInt(matcher.get("price")), matcher.get("message"), id).getErrorMessage());
	}

	public static void runTradeAccept(HashMap<String, String> matcher) {
		System.out
				.println(TradeMenuController.tradeAccept(matcher.get("id"), matcher.get("message")).getErrorMessage());
	}


}
