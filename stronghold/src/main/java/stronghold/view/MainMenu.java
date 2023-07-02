package stronghold.view;

import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stronghold.client.SendRequests;
import stronghold.controller.MainMenuController;
import stronghold.controller.messages.MainMenuMessage;
import stronghold.model.map.Map;
import stronghold.utils.ViewUtils;

public class MainMenu extends Application {

	@FXML
	private Label errorText;
	
	static Scene scene;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(CaptchaMenu.class.getResource("/fxml/MainMenu.fxml"));
		borderPane.setPrefSize(800, 600);
		Image image = new Image(getClass().getResource("/pictures/background/wp5298309.jpg").toExternalForm());
		Background background = new Background(ViewUtils.setBackGround(image));
		borderPane.setBackground(background);
		scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	public void startGameButtonHandler(MouseEvent mouseEvent) throws Exception {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setHeaderText("Enter your mapname:");
		dialog.initOwner(LoginMenu.getStage());
		// LoginMenu.getStage().setFullScreen(false);
		// LoginMenu.getStage().setFullScreen(true);
		Optional<String> mapName = dialog.showAndWait();
		if (!mapName.isPresent())
			errorText.setText("Please enter a mapname");
		else {
			MainMenuMessage result = SendRequests.createGame(mapName.get());
			if(result == MainMenuMessage.SUCCESS) {
				showPopup(GameWaitingRoom.gameId);
				Map map = SendRequests.receiveGameMap();
				MainMenuController.clientStartGame(map);
				new GameMenu().start(LoginMenu.getStage());
				new Thread(SendRequests::waitForGame).start();
			}
			else
				errorText.setText(result.getErrorString());
		}
	}

	public void joinGameButtonHandler(MouseEvent event) throws Exception {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setHeaderText("Enter the game ID:");
		dialog.initOwner(LoginMenu.getStage());
		// LoginMenu.getStage().setFullScreen(false);
		// LoginMenu.getStage().setFullScreen(true);
		Optional<String> gameId = dialog.showAndWait();
		if (!gameId.isPresent())
			errorText.setText("Please enter a game ID");
		else {
			MainMenuMessage result = SendRequests.joinGame(gameId.get());
			if (result == MainMenuMessage.SUCCESS) {
				Map map = SendRequests.receiveGameMap();
				MainMenuController.clientStartGame(map);
				new GameMenu().start(LoginMenu.getStage());
				new Thread(SendRequests::waitForGame).start();
			}
			else
				errorText.setText(result.getErrorString());
		}
	}

	private void showPopup(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game ID");
		alert.setHeaderText("Game ID");
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void chatButtonHandler(MouseEvent mouseEvent) throws Exception {
		System.out.println("chat");
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
		MainMenuController.logout();
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
