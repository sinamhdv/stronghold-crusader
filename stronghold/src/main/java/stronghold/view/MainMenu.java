package stronghold.view;

import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stronghold.controller.MainMenuController;
import stronghold.controller.messages.MainMenuMessage;

public class MainMenu extends Application {

	@FXML
	private Label errorText;
	
	static Scene scene;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(CaptchaMenu.class.getResource("/fxml/MainMenu.fxml"));
		scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	public void startGameButtonHandler(MouseEvent mouseEvent) throws Exception {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setHeaderText("Enter your mapname:");
		dialog.initOwner(LoginMenu.getStage());
		LoginMenu.getStage().setFullScreen(false);
		LoginMenu.getStage().setFullScreen(true);
		Optional<String> mapName = dialog.showAndWait();
		if (!mapName.isPresent())
			errorText.setText("Please enter a mapname");
		else {
			MainMenuMessage result = MainMenuController.startGame(mapName.get());
			if(result.getErrorString().equals("Success!")) {
				//TODO: GameMenu.start
				System.out.println("game started");
			}	
			else 
				errorText.setText(result.getErrorString());
		}
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
		for (int i = 0; i < count; i++) {
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setHeaderText("Enter your username " + (i+1)+" :");
			dialog.initOwner(LoginMenu.getStage());
			LoginMenu.getStage().setFullScreen(false);
			LoginMenu.getStage().setFullScreen(true);
			Optional<String> userName = dialog.showAndWait();
			if (!userName.isPresent())
				usernames[i] = "";
			else
				usernames[i] = userName.get();
		}
		return usernames;
	}
}
