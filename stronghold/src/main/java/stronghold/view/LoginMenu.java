package stronghold.view;

import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
// import stronghold.GenerateConfig;
import stronghold.controller.LoginMenuController;
import stronghold.controller.messages.LoginMenuMessage;
import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.utils.DatabaseManager;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

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

	private static Stage stage;
	public static Stage getStage() {
		return stage;
	}

	public static void main(String[] args) {
		DatabaseManager.loadUsers();
		// GenerateConfig.run();
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/LoginMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void initialize() {
		setupPasswordShowAndHideFeature(passwordUnmaskedField, passwordMaskedField, showPasswordCheckBox);
	}

	private void setupPasswordShowAndHideFeature(TextField passwordUnmaskedField,
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

	public void loginButtonHandler(MouseEvent mouseEvent) {

	}

	public void passwordResetButtonHandler(MouseEvent mouseEvent) {

	}

	public void signupMenuButtonHandler(MouseEvent mouseEvent) {
		
	}

	public static void run() {
		System.out.println("======[Login Menu]======");
		runCheckAutoLogin();

		while (true) {
			String line = MainMenu.getScanner().nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;

			if ((matcher = CommandParser.getMatcher(inputTokens, Command.LOGIN)) != null)
				runLogin(matcher);
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.FORGOT_PASSWORD)) != null)
				runForgotPassword(matcher);
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.SIGNUP_MENU)) != null)
				SignupMenu.run();
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.EXIT)) != null) {
				System.out.println("Exitting...");
				break;
			}
			else
				System.out.println("Error: Invalid command");
		}
	}

	public static void runLogin(HashMap<String, String> matcher) {
		LoginMenuMessage message = LoginMenuController.login(
			matcher.get("username"),
			matcher.get("password"),
			matcher.get("--stay-logged-in")
		);
		System.out.println(message.getErrorString());
		if (message == LoginMenuMessage.LOGIN_SUCCESS)
			MainMenu.run();
	}

	public static void runForgotPassword(HashMap<String, String> matcher) {
		System.out.println(LoginMenuController.forgotPassword(
			matcher.get("username")
		).getErrorString());
	}

	public static void runCheckAutoLogin() {
		System.out.println("Checking for auto-login...");
		LoginMenuMessage message = LoginMenuController.checkAutoLogin();
		System.out.println(message.getErrorString());
		if (message == LoginMenuMessage.AUTO_LOGIN_SUCCESS)
			MainMenu.run();
	}

	public static String askSecurityQuestion(String question) {
		System.out.println("Please answer this security question:");
		System.out.println(question);
		return MainMenu.getScanner().nextLine();
	}

	public static String[] getNewPassword() {
		String[] password = new String[2];
		System.out.print("Enter your new password: ");
		password[0] = MainMenu.getScanner().nextLine();
		System.out.print("Enter the password again: ");
		password[1] = MainMenu.getScanner().nextLine();
		return password;
	}

	public static void alertWeakPassword(SignupAndProfileMenuMessage message) {
		System.out.println(message.getErrorString());
	}
}
