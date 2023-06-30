package stronghold.view;

import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stronghold.controller.MarketMenuController;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.utils.ViewUtils;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class MarketMenu extends Application {

	static Scene scene;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(CaptchaMenu.class.getResource("/fxml/MainMenu.fxml"));
		borderPane.setPrefSize(800, 600);
		Image image = new Image(getClass().getResource("/pictures/background/wp6475236.jpg").toExternalForm());
		Background background = new Background(ViewUtils.setBackGround(image));
		borderPane.setBackground(background);
		scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}
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
