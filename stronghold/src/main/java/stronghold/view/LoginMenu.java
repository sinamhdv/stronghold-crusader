package stronghold.view;

import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stronghold.GenerateConfig;
import stronghold.controller.LoginMenuController;
import stronghold.controller.messages.LoginMenuMessage;
import stronghold.model.StrongHold;
import stronghold.utils.DatabaseManager;
import stronghold.utils.ViewUtils;

public class LoginMenu extends Application {
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordUnmaskedField;
	@FXML
	private PasswordField passwordMaskedField;
	@FXML
	private CheckBox showPasswordCheckBox;
	@FXML
	private Label errorText;
	@FXML
	private CheckBox stayLoggedInCheckBox;

	private static Stage stage;
	public static Stage getStage() {
		return stage;
	}

	public static void main(String[] args) {
		DatabaseManager.loadUsers();
		GenerateConfig.run();
		launch(args);
		// MapManagementMenu.run(); Platform.exit();
	}

	@Override
	public void start(Stage stage) throws Exception {
		LoginMenu.stage = stage;
		BorderPane borderPane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/LoginMenu.fxml"));
		Scene scene = new Scene(borderPane);
		Image image = new Image(getClass().getResource("/pictures/background/680254.jpg").toExternalForm());
		Background background = new Background(ViewUtils.setBackGround(image));
        borderPane.setBackground(background);
		stage.setScene(scene);
		stage.setFullScreenExitHint("");
		stage.setFullScreen(true);
		stage.show();
		if (LoginMenuController.checkAutoLogin() == LoginMenuMessage.AUTO_LOGIN_SUCCESS)
			new MainMenu().start(stage);
	}

	@FXML
	private void initialize() {
		setupPasswordShowAndHideFeature(passwordUnmaskedField, passwordMaskedField, showPasswordCheckBox);
	}

	public static void setupPasswordShowAndHideFeature(TextField passwordUnmaskedField,
		PasswordField passwordMaskedField,
		CheckBox showPasswordCheckBox) {
			passwordUnmaskedField.setManaged(false);
			passwordUnmaskedField.setVisible(false);
			passwordUnmaskedField.visibleProperty().bind(showPasswordCheckBox.selectedProperty());
			passwordUnmaskedField.managedProperty().bind(showPasswordCheckBox.selectedProperty());
			passwordMaskedField.visibleProperty().bind(showPasswordCheckBox.selectedProperty().not());
			passwordMaskedField.managedProperty().bind(showPasswordCheckBox.selectedProperty().not());
			passwordMaskedField.textProperty().bindBidirectional(passwordUnmaskedField.textProperty());
	}

	public void loginButtonHandler(MouseEvent mouseEvent) throws Exception {
		LoginMenuMessage message = LoginMenuController.login(
			usernameTextField.getText(),
			passwordMaskedField.getText(),
			stayLoggedInCheckBox.isSelected()
		);
		if (message != LoginMenuMessage.LOGIN_SUCCESS) {
			errorText.setText(message.getErrorString());
			SignupMenu.showPopup(message.getErrorString(), AlertType.ERROR);
			return;
		}
		CaptchaMenu.setNextMenu(new MainMenu());
		new CaptchaMenu().start(stage);
	}

	public void passwordResetButtonHandler(MouseEvent mouseEvent) throws Exception {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setHeaderText("Enter your username:");
		dialog.initOwner(stage);
		stage.setFullScreen(false);
		stage.setFullScreen(true);
		Optional<String> result = dialog.showAndWait();
		if (!result.isPresent())
			errorText.setText("Please enter a username");
		else if (StrongHold.getUserByName(result.get()) == null)
			errorText.setText("Username doesn't exist");
		else {
			PasswordResetMenu.setPasswordResetUsername(result.get());
			new PasswordResetMenu().start(stage);
		}
	}

	public void signupMenuButtonHandler(MouseEvent mouseEvent) throws Exception {
		new SignupMenu().start(stage);
	}
}
