package stronghold.view;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import stronghold.controller.ProfileMenuController;
import stronghold.controller.SignupMenuController;
import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.utils.ViewUtils;

public class ProfileMenu extends Application {
	@FXML
	private HBox tabButtonsBox;
	@FXML
	private Label menuLabel;
	@FXML
	private VBox centralBox;

	@FXML
	private Label errorText;

	// Profile Info
	@FXML
	private ImageView avatarImage;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label nicknameLabel;
	@FXML
	private Label passwordLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label sloganLabel;

	// Change Username
	@FXML
	private TextField newUsernameField;

	// Change Nickname
	@FXML
	private TextField newNicknameField;

	// Change Email
	@FXML
	private TextField newEmailField;

	// Change Password
	@FXML
	private PasswordField oldPasswordField;
	@FXML
	private PasswordField newPasswordField;


	// Change Slogan
	@FXML
	private CheckBox sloganCheckBox;
	@FXML
	private TextField sloganTextField;
	@FXML
	private Button randomizeSloganButton;

	// Change Avatar
	@FXML
	private HBox avatarBox1;
	@FXML
	private HBox avatarBox2;

	// Scoreboard
	@FXML
	private  TableView<User> scoreBoard;

	private VBox[] tabs = new VBox[8];

	private static User user;
	private static int currentTab;
	private ArrayList<ImageView> avatars = new ArrayList<>();

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(ProfileMenu.class.getResource("/fxml/ProfileMenu.fxml"));
		Image image = new Image(getClass().getResource("/pictures/background/thumb-1920-680255.jpg").toExternalForm());
		Background background = new Background(ViewUtils.setBackGround(image));
		borderPane.setBackground(background);
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	@FXML
	private void initialize() {
		user = StrongHold.getCurrentUser();
		initTabs();
		SignupMenu.setupSloganInputFields(sloganCheckBox, sloganTextField, randomizeSloganButton);
		fillSloganInputFields();
		SignupMenu.addUsernameErrorsListener(newUsernameField, errorText);
		for (int i = 1; i < 5; i++) {
			final int avatarIndex = i - 1; // subtract 1 to convert from 1-based to 0-based indexing
			ImageView avatar = new ImageView(getClass().getResource("/pictures/avatar/" + i + ".png").toExternalForm());
			avatar.setOnMouseClicked(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					changeAvatarHandler(avatarIndex);
				}
			});

			avatars.add(avatar);
		}
		for (int j = 0; j <= 3; j++) {
			avatarBox1.getChildren().add(avatars.get(j));
		}
		activateTab(0);
		initProfileInfo();
		showScoreBoard();

	}

	private void fillSloganInputFields() {
		sloganCheckBox.setSelected(user.getSlogan() != null);
		if (user.getSlogan() != null)
			sloganTextField.setText(user.getSlogan());
	}

	private void initTabs() {
		for (int i = 0; i < tabButtonsBox.getChildren().size(); i++) {
			Button button = (Button) tabButtonsBox.getChildren().get(i);
			int tempIndex = i;
			button.setOnMouseClicked(event -> {
				activateTab(tempIndex);
			});
		}
		int index = 0;
		for (Node node : centralBox.getChildren()) {
			if (node instanceof VBox) {
				tabs[index++] = (VBox) node;
			}
		}
	}

	private void initProfileInfo() {
		int avatarIndex = StrongHold.getCurrentUser().getIndexOfOvatar();
		Image avatar = avatars.get(avatarIndex).getImage();
		avatarImage.setImage(avatar);
		usernameLabel.setText("username: " + user.getUserName());
		passwordLabel.setText("password(SHA256): " + user.getPassword());
		nicknameLabel.setText("nickname: " + user.getNickName());
		emailLabel.setText("email: " + user.getEmail());
		if (user.getSlogan() != null && user.getSlogan().length() > 0)
			sloganLabel.setText("slogan: " + user.getSlogan());
		else
			sloganLabel.setText("");
	}

	public void backButtonHandler() throws Exception {
		new MainMenu().start(LoginMenu.getStage());
	}

	private void activateTab(int index) {
		currentTab = index;
		errorText.setText("");
		menuLabel.setText(((Button) tabButtonsBox.getChildren().get(index)).getText());
		for (int i = 0; i < tabs.length; i++) {
			tabs[i].setVisible(false);
			tabs[i].setManaged(false);
		}
		tabs[index].setVisible(true);
		tabs[index].setManaged(true);
		if (index == 0) { // update profile info
			initProfileInfo();
		}
	}

	public void randomizeSloganButtonHandler(MouseEvent mouseEvent) {
		sloganTextField.setText(SignupMenuController.generateRandomSlogan());
	}

	public void changeUsernameButtonHandler(MouseEvent mouseEvent) {
		updateErrors(ProfileMenuController.changeUserName(newUsernameField.getText()));
	}

	public void changePasswordButtonHandler(MouseEvent mouseEvent) {
		updateErrors(ProfileMenuController.changePassword(newPasswordField.getText(), oldPasswordField.getText()));
	}

	public void changeNicknameButtonHandler(MouseEvent mouseEvent) {
		updateErrors(ProfileMenuController.changeNickName(newNicknameField.getText()));
	}

	public void changeEmailButtonHandler(MouseEvent mouseEvent) {
		updateErrors(ProfileMenuController.changeEmail(newEmailField.getText()));
	}

	public void changeSloganButtonHandler(MouseEvent mouseEvent) {
		updateErrors(ProfileMenuController.changeSlogan(
				sloganCheckBox.isSelected() ? sloganTextField.getText() : null));
	}

	public void updateErrors(SignupAndProfileMenuMessage message) {
		if (message != SignupAndProfileMenuMessage.SUCCESS) {
			errorText.setText(message.getErrorString());
			return;
		}
		errorText.setText("");
		Alert alert = new Alert(AlertType.INFORMATION, "Success");
		alert.initOwner(LoginMenu.getStage());
		alert.showAndWait();
	}

	// public static void run() {
	// System.out.println("======[Profile Menu]======");

	// while (true) {
	// String line = MainMenu.getScanner().nextLine();
	// String[] inputTokens = CommandParser.splitTokens(line);
	// HashMap<String, String> matcher;
	// if ((matcher = CommandParser.getMatcher(inputTokens, Command.BACK)) != null)
	// return;
	// else if ((matcher = CommandParser.getMatcher(inputTokens,
	// Command.CHANGE_USERNAME)) != null)
	// System.out.println(ProfileMenuController.changeUserName(matcher.get("username")).getErrorString());
	// else if ((matcher = CommandParser.getMatcher(inputTokens,
	// Command.CHANGE_NICKNAME)) != null)
	// System.out.println(ProfileMenuController.changeNickName(matcher.get("nickname")).getErrorString());
	// else if ((matcher = CommandParser.getMatcher(inputTokens,
	// Command.CHANGE_PASSWORD)) != null)
	// System.out.println(
	// ProfileMenuController.changePassword(matcher.get("newPassword"),
	// matcher.get("oldPassword")).getErrorString());
	// else if ((matcher = CommandParser.getMatcher(inputTokens,
	// Command.CHANGE_EMAIL)) != null)
	// System.out.println(ProfileMenuController.changeEmail(matcher.get("email")).getErrorString());
	// else if ((matcher = CommandParser.getMatcher(inputTokens,
	// Command.CHANGE_SLOGAN)) != null)
	// System.out.println(ProfileMenuController.changeSlogan(matcher.get("slogan")).getErrorString());
	// else if ((matcher = CommandParser.getMatcher(inputTokens,
	// Command.REMOVE_SLOGAN)) != null)
	// System.out.println(ProfileMenuController.removeSlogan().getErrorString());
	// else if ((matcher = CommandParser.getMatcher(inputTokens,
	// Command.DISPLAY_HIGHSCORE)) != null)
	// displayHighscore();
	// else if ((matcher = CommandParser.getMatcher(inputTokens,
	// Command.DISPLAY_RANK)) != null)
	// displayRank();
	// else if ((matcher = CommandParser.getMatcher(inputTokens,
	// Command.DISPLAY_SLOGAN)) != null)
	// displaySlogan();
	// else if ((matcher = CommandParser.getMatcher(inputTokens,
	// Command.PROFILE_DISPLAY)) != null)
	// displayInfo();
	// else
	// System.out.println("Error: Invalid command");
	// }
	// }

	// private static void displayHighscore() {
	// System.out.println("you're highscore is: " +
	// StrongHold.getCurrentUser().getHighScore());
	// }

	// private static void displayRank() {
	// System.out.println("you're rank is: " +
	// StrongHold.getRank(StrongHold.getCurrentUser()));
	// }

	// private static void displaySlogan() {
	// if (StrongHold.getCurrentUser().getSlogan() == null)
	// System.out.println("you dont have any slogans");
	// else
	// System.out.println("you're slogan is: " +
	// StrongHold.getCurrentUser().getSlogan());
	// }

	// private static void displayInfo() {
	// User user = StrongHold.getCurrentUser();
	// System.out.println("Username: " + user.getUserName());
	// System.out.println("Nickname: " + user.getNickName());
	// System.out.println("Email: " + user.getEmail());
	// System.out.println("Highscore: " + user.getHighScore());
	// System.out.println("Rank: " + StrongHold.getRank(user));
	// System.out.println((user.getSlogan() == null ? "No slogan" : "Slogan: " +
	// user.getSlogan()));
	// }

	public static String askNewPasswordConfirmation() {
		System.out.print("Please enter the new password again: ");
		return MainMenu.getScanner().nextLine();
	}

	public static void changeAvatarHandler(int indexOfPicture) {
		ProfileMenuController.changeAvatar(indexOfPicture);
		;
	}

	public  void showScoreBoard() {
		final int[] startIndex = {0};
		final int[] endIndex = {0};
		ObservableList<User> users = FXCollections.observableArrayList(StrongHold.sortPerson());
		TableColumn<User, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
		TableColumn<User, Integer> rankColumn = new TableColumn<>("Rank");
		rankColumn.setCellValueFactory(cell ->
			new SimpleIntegerProperty(StrongHold.getRank(cell.getValue())).asObject());
		TableColumn<User, Integer> scoreColumn = new TableColumn<>("High Score");
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("highScore"));
		scoreBoard.getColumns().addAll(nameColumn, scoreColumn, rankColumn);
		scoreBoard.setOnScroll(event -> {
			if (event.getDeltaY() > 0 && endIndex[0] < users.size()) {
				startIndex[0] = endIndex[0];
				endIndex[0] = Math.min(endIndex[0] + 10, users.size());
				scoreBoard.getItems().addAll(users.subList(startIndex[0], endIndex[0]));
			}
			else if (event.getDeltaY() < 0 && startIndex[0] > 0) {
				endIndex[0] = startIndex[0];
				startIndex[0] = Math.max(startIndex[0] - 10, 0);
				scoreBoard.getItems().addAll(0, users.subList(startIndex[0], endIndex[0]));
			}
		});
	}

}
