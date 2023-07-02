package stronghold.view;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stronghold.client.SendRequests;
import stronghold.model.StrongHold;

public class GameWaitingRoom extends Application {
	@FXML
	private Label mainLabel;

	public static String gameId;
	public static void setGameId(String gameId) {
		GameWaitingRoom.gameId = gameId;
	}

	private static BooleanProperty gameStarted = new SimpleBooleanProperty(false);
	public static void startGame() {
		gameStarted.set(true);
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(GameWaitingRoom.class.getResource("/fxml/GameWaitingRoom.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	@FXML
	private void initialize() {
		mainLabel.setText("Waiting for Game " + gameId + " to be started. You are player #" +
			StrongHold.getMyPlayerIndex());
		gameStarted.addListener((observable, oldValue, newValue) -> {
			if (newValue.booleanValue()) {
				try {
					new GameMenu().start(LoginMenu.getStage());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		new Thread(SendRequests::waitForGame).start();
	}
}
