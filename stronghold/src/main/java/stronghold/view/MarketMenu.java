package stronghold.view;

import java.util.HashMap;

import stronghold.controller.MarketMenuController;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class MarketMenu {
	private static void printMenuPrompt() {
		TerminalColor.setColor(TerminalColor.BLACK, TerminalColor.YELLOW);
		System.out.print("market> ");
		TerminalColor.resetColor();
	}

	public static void run() {
		while (true) {
			printMenuPrompt();
			String line = MainMenu.getScanner().nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;

			if ((matcher = CommandParser.getMatcher(inputTokens, Command.SELL)) != null)
				runSellItem(matcher);
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.BUY)) != null)
				runBuyItem(matcher);
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.SHOW_PRICE_LIST)) != null)
				showPriceList();
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.BACK)) != null) {
				break;
			} else
				System.out.println("Error: Invalid command");
		}
	}

	public static void showPriceList() {
		Government currnetPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		System.out.println("ITEM\t\t\tBUYPRICE\t\tSELLPRICE\t\tYOURASSET");
		for (ResourceType resource : ResourceType.values()) {
			if (!resource.isTradable()) continue;
			int asset = currnetPlayer.getResourceCount(resource);
			System.out.println(resource.getName() + "\t\t\t" + resource.getBuyPrice() + "\t\t" + resource.getSellprice()
					+ "\t\t" + asset);
		}
	}

	public static void runBuyItem(HashMap<String, String> matcher) {
		System.out.println(
				MarketMenuController.buyItem(matcher.get("itemName"), Integer.parseInt(matcher.get("amount")))
						.getErrorString());
	}

	public static void runSellItem(HashMap<String, String> matcher) {
		System.out.println(MarketMenuController
				.sellItem(matcher.get("itemName"), Integer.parseInt(matcher.get("amount"))).getErrorString());
	}

}
