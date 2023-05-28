package stronghold.view;

import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stronghold.controller.MainMenuController;

public class MainMenu extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(CaptchaMenu.class.getResource("/fxml/MainMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}

	public void startGameButtonHandler(MouseEvent mouseEvent) throws Exception {
		System.out.println("start game");
	}

	public void profileButtonHandler(MouseEvent mouseEvent) throws Exception {
		new ProfileMenu().start(LoginMenu.getStage());
	}

	public void mapManagementButtonHandler(MouseEvent mouseEvent) throws Exception {
		System.out.println("map management");
	}

	public void logoutButtonHandler(MouseEvent mouseEvent) throws Exception {
		MainMenuController.logout();
		new LoginMenu().start(LoginMenu.getStage());
	}

	public void exitButtonHandler(MouseEvent mouseEvent) throws Exception {
		Platform.exit();
	}

	private static final Scanner scanner = new Scanner(System.in);
	public static Scanner getScanner() {
		return scanner;
	}

	// public static void run() {
	// 	System.out.println("======[Main Menu]======");

	// 	while (true) {
	// 		String[] input = CommandParser.splitTokens(MainMenu.getScanner().nextLine());
	// 		HashMap<String, String> matcher;

	// 		if ((matcher = CommandParser.getMatcher(input, Command.PROFILE_MENU)) != null)
	// 			ProfileMenu.run();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.MAP_MANAGEMENT_MENU)) != null)
	// 			MapManagementMenu.run();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.START_GAME)) != null)
	// 			runStartGame(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.LOGOUT)) != null) {
	// 			MainMenuController.logout();
	// 			System.out.println("logged out");
	// 			return;
	// 		}
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.EXIT)) != null) {
	// 			System.out.println("Exitting...");
	// 			MainMenuController.exit();
	// 		}
	// 		else
	// 			System.out.println("Invalid command");
	// 	}
	// }

	// private static void runStartGame(HashMap<String, String> matcher) {
	// 	MainMenuMessage message = MainMenuController.startGame(matcher.get("map"));
	// 	System.out.println(message.getErrorString());
	// 	if (message == MainMenuMessage.SUCCESS) {
	// 		GameMenu.run();
	// 	}
	// }

	public static String[] askPlayersNames(int count) {
		String[] usernames = new String[count];
		System.out.println("The selected map needs " + count + " players");
		for (int i = 0; i < count; i++) {
			System.out.print("- Enter the username of player #" + i + ": ");
			usernames[i] = MainMenu.getScanner().nextLine();
		}
		return usernames;
	}
}
