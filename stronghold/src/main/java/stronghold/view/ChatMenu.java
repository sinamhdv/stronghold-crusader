package stronghold.view;

import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import stronghold.client.SendRequests;
import stronghold.controller.ChatMenuController;
import stronghold.controller.messages.ChatMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.chat.Message;
import stronghold.model.chat.Room;
import stronghold.utils.FormatValidation;

public class ChatMenu extends Application {
	private static ChatMenu instance;

	private static final Image[] REACTION_EMOJIES = {
		new Image(ChatMenu.class.getResource("/images/ui/sad.png").toExternalForm()),
		new Image(ChatMenu.class.getResource("/images/ui/poker.png").toExternalForm()),
		new Image(ChatMenu.class.getResource("/images/ui/smile.png").toExternalForm())
	};
	private static final Image CHECKMARK_IMAGE = new Image(ChatMenu.class.getResource("/images/ui/check.png").toExternalForm());

	@FXML
	private TextField messageInput;
	@FXML
	private VBox messagesBox;
	@FXML
	private ComboBox<String> chatTypeCombo;
	@FXML
	private Label chatTitleLabel;
	@FXML
	private VBox createRoomInputsBox;
	@FXML
	private Button createRoomButton;
	@FXML
	private TextField roomNameInput;
	@FXML
	private TextField membersCountInput;

	private ArrayList<TextField> createRoomInputs = new ArrayList<>();

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
		Label text = new Label(message.getSender() + "[" + message.getTimestamp() + "]: " + message.getContent());
		text.setTextFill(Color.BLACK);
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
		HBox box = new HBox(5, avatar, text, reaction);
		box.setAlignment(Pos.CENTER_LEFT);
		if (message.getSender().equals(StrongHold.getCurrentUser().getUserName())) {
			box.setStyle("-fx-background-color: lightblue;");
			Button deleteButton = new Button("Del");
			deleteButton.setOnMouseClicked(event -> runDelete(message.getId()));
			Button editButton = new Button("Edit");
			editButton.setOnMouseClicked(event -> runEdit(message.getId()));
			ImageView checkMark = new ImageView(CHECKMARK_IMAGE);
			checkMark.setFitHeight(10);
			checkMark.setFitWidth(10);
			box.getChildren().addAll(checkMark, deleteButton, editButton);
		}
		else
			box.setStyle("-fx-background-color: white;");
		int emojiIndex = 0;
		for (Image emojiImage : REACTION_EMOJIES) {
			ImageView emoji = new ImageView(emojiImage);
			emoji.setFitHeight(15);
			emoji.setFitWidth(15);
			Button reactionButton = new Button();
			int tempEmojiIndex = emojiIndex;
			reactionButton.setOnMouseClicked(event -> runReact(message.getId(), tempEmojiIndex));
			reactionButton.setGraphic(emoji);
			box.getChildren().add(reactionButton);
			emojiIndex++;
		}
		messagesBox.getChildren().add(box);
	}

	private void refreshChatsList() {
		for (Room room : ChatMenuController.getChatData().getRooms())
			if (!chatTypeCombo.getItems().contains(room.getName()) &&
				room.isMember(StrongHold.getCurrentUser().getUserName()))
				chatTypeCombo.getItems().add(room.getName());
		for (String username : ChatMenuController.getChatData().getUsers())
			if (!chatTypeCombo.getItems().contains(username))
				chatTypeCombo.getItems().add(username);
	}

	public void refreshScreen() {
		refreshMessages();
		refreshChatsList();
	}

	private void runDelete(int id) {
		ChatMenuController.deleteMessage(id);
	}

	private void runEdit(int id) {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setHeaderText("Enter new content:");
		dialog.initOwner(LoginMenu.getStage());
		Optional<String> content = dialog.showAndWait();
		if (!content.isPresent()) return;
		ChatMenuController.editMessage(id, content.get());
	}

	private void runReact(int id, int emojiIndex) {
		ChatMenuController.reactMessage(id, emojiIndex);
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

	private void showPopup(String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(alertType == AlertType.ERROR ? "Error" : "Success");
		alert.setHeaderText(alertType == AlertType.ERROR ? "Error" : "Success");
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void openCreateRoomInputs(MouseEvent event) {
		if (!FormatValidation.isNumber(membersCountInput.getText()) ||
			Integer.parseInt(membersCountInput.getText()) <= 0) {
			showPopup("invalid members count", AlertType.ERROR);
			return;
		}
		int membersCount = Integer.parseInt(membersCountInput.getText());
		createRoomInputs.clear();
		createRoomInputsBox.getChildren().clear();
		for (int i = 0; i < membersCount; i++) {
			TextField textField = new TextField();
			textField.setPromptText("username #" + (i + 1));
			createRoomInputs.add(textField);
			createRoomInputsBox.getChildren().add(textField);
		}
		createRoomButton.setVisible(true);
	}

	public void createRoomButtonHandler(MouseEvent event) {
		String[] members = new String[createRoomInputs.size()];
		for (int i = 0; i < members.length; i++)
			members[i] = createRoomInputs.get(i).getText();
		ChatMenuMessage message = ChatMenuController.createRoom(roomNameInput.getText(), members);
		showPopup(message.getErrorString(), message == ChatMenuMessage.SUCCESS ? AlertType.INFORMATION : AlertType.ERROR);
		createRoomButton.setVisible(false);
		createRoomInputs.clear();
		createRoomInputsBox.getChildren().clear();
		roomNameInput.setText("");
		membersCountInput.setText("");
	}
}
