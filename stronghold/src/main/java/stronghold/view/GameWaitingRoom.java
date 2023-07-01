package stronghold.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stronghold.client.SendRequests;

public class GameWaitingRoom extends Application {
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
		
	}
}
