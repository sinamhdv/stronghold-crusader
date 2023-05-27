package stronghold.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CaptchaMenu extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(CaptchaMenu.class.getResource("/fxml/CaptchaMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}
}
