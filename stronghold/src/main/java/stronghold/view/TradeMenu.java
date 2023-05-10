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
	public static void showTradeList() {
		Game currentGame = StrongHold.getCurrentGame();
		System.out.println("===============================Trade Request===================================");
		System.out.println("User     Resourcename     Amount     Price     Id     message");
		for (TradeRequest trade : currentGame.getAllTrads()) {
			System.out.println(
					trade.getRequestBy().getUser().getUserName() + "     " + trade.getResource().getName() + "     "
							+ trade.getAmount() + "     " + trade.getPrice() + "     " + trade.getId() + "     "
							+ trade.getMessage());
		}
		System.out.println("===============================Trade Accept===================================");
		System.out.println("request by     Accept by     Trade Id     ResourceName     Amount     Price");
		for (TradeRequest tradeAccept : currentGame.getAllTradeAccepts()) {
			System.out.println(tradeAccept.getRequestBy().getUser().getNickName() + "     "
					+ tradeAccept.getAcceptBy().getUser().getNickName() + "     " + tradeAccept.getId() + "     "
					+ tradeAccept.getMessage() + "     " + tradeAccept.getResourceName() + "     "
					+ tradeAccept.getAmount() + "     " + tradeAccept.getPrice());
		}
	}

	public static void showTradeHistory() {
		Game currentGame = StrongHold.getCurrentGame();
		Government currentPlayer = currentGame.getCurrentPlayer();
		System.out.println("------------------------------Your Trade Request--------------------------------");
		System.out.println("User     Resourcename     Amount     Price     Id     message");
		for (TradeRequest trade : currentGame.getAllTrads()) {
			if (trade.getRequestBy() == currentPlayer) {
				System.out.println(
						trade.getRequestBy().getUser().getUserName() + "     " + trade.getResource().getName() + "     "
								+ trade.getAmount() + "     " + trade.getPrice() + "     " + trade.getId() + "     "
								+ trade.getMessage());
			}
		}
		System.out.println("------------------------------Your Trade Accept--------------------------------");
		System.out.println("request by     Accept by     Trade Id     message      ResourceName     Amount     Price");
		for (TradeRequest tradeAccept : currentGame.getAllTradeAccepts()) {
			if (tradeAccept.getAcceptBy() == currentPlayer || tradeAccept.getRequestBy() == currentPlayer) {
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
				showTradeHistory();
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.TRADE_LIST)) != null)
				showTradeList();
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.EXIT)) != null) {
				System.out.println("Exitting...");
				break;
			}
			else
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
