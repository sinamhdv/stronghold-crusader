package stronghold.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stronghold.client.SendRequests;
import stronghold.controller.ChatMenuController;
import stronghold.model.StrongHold;
import stronghold.model.chat.Message;
import stronghold.model.chat.Room;

public class ChatMenu extends Application {
	private static ChatMenu instance;

	private static final Image[] REACTION_EMOJIES = {
		new Image(ChatMenu.class.getResource("/images/ui/sad.png").toExternalForm()),
		new Image(ChatMenu.class.getResource("/images/ui/poker.png").toExternalForm()),
		new Image(ChatMenu.class.getResource("/images/ui/smile.png").toExternalForm())
	};

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
		if (chatName == null) return;
		String chatTitle = chatName;
		if (chatName.charAt(0) != '@')
			chatTitle += " (" + (ChatMenuController.getChatData().isOnline(chatName) ? "online" : "offline") + ")";
		chatTitleLabel.setText(chatTitle);
		for (Message message : ChatMenuController.getChatData().getMessages())
			if (message.isInChat(chatName, StrongHold.getCurrentUser().getUserName()))
				addMessage(message);
	}

	private void addMessage(Message message) {
		Label text = new Label(message.getSender() + "[" + message.getTimestamp() + "]" + message.getContent());
		ImageView avatar;
		try {
			avatar = new ImageView(ChatMenu.class.getResource("/pictures/avatar/" +
				message.getSenderAvatarIndex() + ".png").toExternalForm());
		} catch (Exception ex) {
			avatar = new ImageView();
		}
		avatar.setFitHeight(20);
		avatar.setFitWidth(20);
		ImageView reaction;
		try {
			reaction = new ImageView(REACTION_EMOJIES[message.getReactionEmojiIndex()]);
		} catch (Exception ex) {
			reaction = new ImageView();
		}
		reaction.setFitHeight(20);
		reaction.setFitWidth(20);
		Button deleteButton = new Button("Del");
		Button editButton = new Button("Edit");
		HBox box = new HBox(5, avatar, text, reaction, deleteButton, editButton);
		box.setAlignment(Pos.CENTER_LEFT);
		for (Image emojiImage : REACTION_EMOJIES) {
			ImageView emoji = new ImageView(emojiImage);
			emoji.setFitHeight(15);
			emoji.setFitWidth(15);
			Button reactionButton = new Button();
			reactionButton.setGraphic(emoji);
			box.getChildren().add(reactionButton);
		}
		if (message.getSender().equals(StrongHold.getCurrentUser().getUserName()))
			box.setStyle("-fx-background-color: lightblue;");
		else
			box.setStyle("-fx-background-color: white;");
		messagesBox.getChildren().add(box);
	}

	private void refreshChatsList() {
		for (Room room : ChatMenuController.getChatData().getRooms())
			if (!chatTypeCombo.getItems().contains(room.getName()))
				chatTypeCombo.getItems().add(room.getName());
		for (String username : ChatMenuController.getChatData().getUsers())
			if (!chatTypeCombo.getItems().contains(username))
				chatTypeCombo.getItems().add(username);
	}

	public void refreshScreen() {
		refreshMessages();
		refreshChatsList();
	}

	public void sendButtonHandler(MouseEvent event) {
		if (messageInput.getText().length() == 0) return;
		ChatMenuController.sendMessage(chatTypeCombo.getSelectionModel().getSelectedItem(), messageInput.getText());
		messageInput.setText("");
	}

	public void backButtonHandler(MouseEvent event) throws Exception {
		SendRequests.endChat();
		new MainMenu().start(LoginMenu.getStage());
	}
}
