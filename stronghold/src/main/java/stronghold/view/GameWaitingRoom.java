package stronghold.view;

import javafx.application.Application;
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
		SendRequests.waitForGame();
	}
}
