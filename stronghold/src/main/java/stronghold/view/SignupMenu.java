package stronghold.view;

import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stronghold.client.SendRequests;
import stronghold.controller.CentralController;
import stronghold.controller.SignupMenuController;
import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.utils.ViewUtils;

public class SignupMenu extends Application {
	@FXML
	private TextField usernameTextField;
	@FXML
	private Label usernameErrorText;
	@FXML
	private TextField passwordUnmaskedField;
	@FXML
	private PasswordField passwordMaskedField;
	@FXML
	private CheckBox showPasswordCheckBox;
	@FXML
	private Label passwordStrengthErrorText;
	@FXML
	private TextField nicknameTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private CheckBox sloganCheckBox;
	@FXML
	private TextField sloganTextField;
	@FXML
	private Button randomizeSloganButton;
	@FXML
	private Label errorText;
	@FXML
	private ComboBox<String> securityQuestionsComboBox;
	@FXML
	private TextField answerTextField;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(CaptchaMenu.class.getResource("/fxml/SignupMenu.fxml"));
		borderPane.setPrefSize(800, 600);
		Image image = new Image(getClass().getResource("/pictures/background/thumb-1920-1172588.png").toExternalForm());
		Background background = new Background(ViewUtils.setBackGround(image));
        borderPane.setBackground(background);
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	@FXML
	private void initialize() {
		LoginMenu.setupPasswordShowAndHideFeature(passwordUnmaskedField, passwordMaskedField, showPasswordCheckBox);
		setupSloganInputFields(sloganCheckBox, sloganTextField, randomizeSloganButton);
		addUsernameErrorsListener(usernameTextField, usernameErrorText);
		PasswordResetMenu.addPasswordStrengthListener(passwordStrengthErrorText, passwordMaskedField);
		setupSecurityQuestionsComboBox(securityQuestionsComboBox);
	}

	public static void setupSloganInputFields(CheckBox sloganCheckBox, TextField sloganTextField, Button randomizeButton) {
		sloganTextField.setVisible(false);
		sloganTextField.setManaged(false);
		randomizeButton.setVisible(false);
		randomizeButton.setManaged(false);
		sloganTextField.visibleProperty().bind(sloganCheckBox.selectedProperty());
		sloganTextField.managedProperty().bind(sloganCheckBox.selectedProperty());
		randomizeButton.visibleProperty().bind(sloganCheckBox.selectedProperty());
		randomizeButton.managedProperty().bind(sloganCheckBox.selectedProperty());
	}

	public static void addUsernameErrorsListener(TextField usernameTextField, Label error) {
		usernameTextField.textProperty().addListener((observable, oldText, newText) -> {
			SignupAndProfileMenuMessage message = SignupMenuController.checkUsernameRealtimeErrors(newText);
			error.setText(message == null ? "" : message.getErrorString());
		});
	}

	private static void setupSecurityQuestionsComboBox(ComboBox<String> comboBox) {
		comboBox.getItems().addAll(CentralController.SECURITY_QUESTIONS);
	}

	public void randomizeSloganButtonHandler(MouseEvent mouseEvent) throws Exception {
		sloganTextField.setText(SignupMenuController.generateRandomSlogan());
	}

	public void backButtonHandler(MouseEvent mouseEvent) throws Exception {
		new LoginMenu().start(LoginMenu.getStage());
	}

	public void signupButtonHandler(MouseEvent mouseEvent) throws Exception {
		SignupAndProfileMenuMessage message = SendRequests.requestSignup(
			usernameTextField.getText(),
			nicknameTextField.getText(),
			passwordMaskedField.getText(),
			emailTextField.getText(),
			(sloganCheckBox.isSelected() ? sloganTextField.getText() : null),
			securityQuestionsComboBox.getSelectionModel().getSelectedIndex(),
			answerTextField.getText()
		);
		if (message != SignupAndProfileMenuMessage.SUCCESS) {
			errorText.setText(message.getErrorString());
			return;
		}
		new LoginMenu().start(LoginMenu.getStage());
	}

	public void randomizePasswordButtonHandler(MouseEvent mouseEvent) {
		String password = SignupMenuController.generateRandomPassword();
		Alert dialog = new Alert(AlertType.CONFIRMATION,
			"Do you want to use '" + password + "' as your password?",
			ButtonType.OK, ButtonType.CANCEL);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK)
			passwordMaskedField.setText(password);
	}
}
