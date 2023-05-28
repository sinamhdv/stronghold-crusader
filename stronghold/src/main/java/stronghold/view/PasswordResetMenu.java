package stronghold.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import stronghold.controller.CentralController;
import stronghold.controller.LoginMenuController;
import stronghold.controller.messages.LoginMenuMessage;
import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.utils.FormatValidation;

public class PasswordResetMenu extends Application {
	@FXML
	private Label securityQuestionText;
	@FXML
	private TextField answerTextField;
	@FXML
	private PasswordField passwordMaskedField;
	@FXML
	private TextField passwordUnmaskedField;
	@FXML
	private CheckBox showPasswordCheckBox;
	@FXML
	private Label errorText;
	@FXML
	private Label passwordStrengthError;

	private static String passwordResetUsername;
	public static void setPasswordResetUsername(String passwordResetUsername) {
		PasswordResetMenu.passwordResetUsername = passwordResetUsername;
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(PasswordResetMenu.class.getResource("/fxml/PasswordResetMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	@FXML
	private void initialize() {
		LoginMenu.setupPasswordShowAndHideFeature(passwordUnmaskedField, passwordMaskedField, showPasswordCheckBox);
		initSecurityQuestionText();
		addPasswordStrengthListener(passwordStrengthError, passwordMaskedField);
	}

	public static void addPasswordStrengthListener(Label error, PasswordField passwordField) {
		passwordField.textProperty().addListener((observable, oldText, newText) -> {
			SignupAndProfileMenuMessage message = FormatValidation.checkPasswordStrength(newText);
			error.setText(message.getErrorString());
			error.setTextFill((message == SignupAndProfileMenuMessage.PASSWORD_IS_STRONG ? Color.GREEN : Color.RED));
		});
	}

	private void initSecurityQuestionText() {
		User user = StrongHold.getUserByName(passwordResetUsername);
		securityQuestionText.setText(CentralController.SECURITY_QUESTIONS[user.getSecurityQuestionNumber() - 1]);
	}

	public void submitButtonHandler(MouseEvent mouseEvent) throws Exception {
		LoginMenuMessage message = LoginMenuController.forgotPassword(
			passwordResetUsername,
			answerTextField.getText(),
			passwordMaskedField.getText()
		);
		if (message != LoginMenuMessage.PASSWORD_RESET_SUCCESS) {
			errorText.setText(message.getErrorString());
			return;
		}
		Alert alert = new Alert(AlertType.INFORMATION, "Success");
		alert.initOwner(LoginMenu.getStage());
		alert.showAndWait();
		new LoginMenu().start(LoginMenu.getStage());
	}
}
