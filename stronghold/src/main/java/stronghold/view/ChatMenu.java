package stronghold.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatMenu extends Application {
	@FXML
	private TextField messageInput;
	@FXML
	private VBox messagesBox;
	@FXML
	private ComboBox<String> chatTypeCombo;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(ChatMenu.class.getResource("/fxml/ChatMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	@FXML
	private void initialize() {
		setupChatTypeCombo();
	}

	private void setupChatTypeCombo() {
		chatTypeCombo.getItems().addAll("Public", "Private");
		chatTypeCombo.getSelectionModel().select(0);
		chatTypeCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			refreshScreen();
		});
	}

	private void refreshScreen() {
		messagesBox.getChildren().clear();
	}

	public void sendButtonHandler(MouseEvent event) {
		
		messageInput.setText("");
	}
}
