package stronghold.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stronghold.client.SendRequests;
import stronghold.controller.ChatMenuController;
import stronghold.model.chat.Message;

public class ChatMenu extends Application {
	private static ChatMenu instance;

	@FXML
	private TextField messageInput;
	@FXML
	private VBox messagesBox;
	@FXML
	private ComboBox<String> chatTypeCombo;
	@FXML
	private Label chatTitleLabel;

	public ChatMenu() {
		instance = this;
	}

	public static ChatMenu getInstance() {
		return instance;
	}

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
		chatTypeCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			refreshMessages();
		});
		SendRequests.startChat();
	}

	private void refreshMessages() {
		messagesBox.getChildren().clear();
		String chatName = chatTypeCombo.getSelectionModel().getSelectedItem();
		String chatTitle = chatName;
		if (chatName.charAt(0) != '@')
			chatTitle += " (" + (ChatMenuController.getChatData().isOnline(chatName) ? "online" : "offline") + ")";
		chatTitleLabel.setText(chatTitle);
		for (Message message : ChatMenuController.getChatData().getMessages()) {
			if ()
		}
	}

	public void refreshScreen() {
		refreshMessages();
	}

	public void sendButtonHandler(MouseEvent event) {
		messageInput.setText("");
	}

	public void backButtonHandler(MouseEvent event) throws Exception {
		SendRequests.endChat();
		new MainMenu().start(LoginMenu.getStage());
	}
}
