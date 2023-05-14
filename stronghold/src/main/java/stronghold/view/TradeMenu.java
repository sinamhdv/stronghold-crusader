package stronghold.view;

import java.util.HashMap;

import stronghold.controller.TradeMenuController;
import stronghold.model.StrongHold;
import stronghold.model.TradeRequest;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class TradeMenu {
	private static void printMenuPrompt() {
		TerminalColor.setColor(TerminalColor.BLACK, TerminalColor.PURPLE);
		System.out.print("trade> ");
		TerminalColor.resetColor();
	}

	public static void run() {

		printNotifications();

		while (true) {
			printMenuPrompt();
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
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.BACK)) != null) {
				break;
			} else
				System.out.println("Error: Invalid command");
		}
	}

	public static void runTradeRequest(HashMap<String, String> matcher) {
		System.out.println(TradeMenuController.tradeRequest(
			matcher.get("resourceType"),
			Integer.parseInt(matcher.get("amount")),
			Integer.parseInt(matcher.get("price")),
			matcher.get("message"),
			Integer.parseInt(matcher.get("government"))
		).getErrorMessage());
	}

	public static void runTradeAccept(HashMap<String, String> matcher) {
		System.out.println(TradeMenuController.tradeAccept(
			Integer.parseInt(matcher.get("id"))
		).getErrorMessage());
	}

	private static void printNotifications() {
		System.out.println("======[Trade Menu]======");
		System.out.println("New trade requests to you: ");
		for (TradeRequest request : StrongHold.getCurrentGame().getAllTrades()) {
			if (request.getReceiverIndex() == StrongHold.getCurrentGame().getCurrentPlayerIndex() &&
				!request.isSeen()) {
				System.out.println(request);
				request.setSeen(true);
			}
		}
	}

	private static void showTradeHistory() {
		System.out.println("====== Requests Sent By You ======");
		int myIndex = StrongHold.getCurrentGame().getCurrentPlayerIndex();
		for (TradeRequest request : StrongHold.getCurrentGame().getAllTrades())
			if (request.getSenderIndex() == myIndex)
				System.out.println(request);
		System.out.println();
		System.out.println("====== Accepted Requests To You ======");
		for (TradeRequest request : StrongHold.getCurrentGame().getAllTrades())
			if (request.isAccepted() && request.getReceiverIndex() == myIndex)
				System.out.println(request);
		System.out.println();
	}

	private static void showTradeList() {
		System.out.println("====== Active Trade Requests ======");
		int myIndex = StrongHold.getCurrentGame().getCurrentPlayerIndex();
		for (TradeRequest request : StrongHold.getCurrentGame().getAllTrades())
			if (request.getReceiverIndex() == myIndex && !request.isAccepted())
				System.out.println(request);
		System.out.println();
	}
}
