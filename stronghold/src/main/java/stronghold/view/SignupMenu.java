package stronghold.view;

import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stronghold.controller.CentralController;
import stronghold.controller.SignupMenuController;
import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.utils.FormatValidation;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

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

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(CaptchaMenu.class.getResource("/fxml/SignupMenu.fxml"));
		borderPane.setPrefSize(800, 600);
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
	}

	private static void setupSloganInputFields(CheckBox sloganCheckBox, TextField sloganTextField, Button randomizeButton) {
		sloganTextField.setVisible(false);
		sloganTextField.setManaged(false);
		randomizeButton.setVisible(false);
		randomizeButton.setManaged(false);
		sloganTextField.visibleProperty().bind(sloganCheckBox.selectedProperty());
		sloganTextField.managedProperty().bind(sloganCheckBox.selectedProperty());
		randomizeButton.visibleProperty().bind(sloganCheckBox.selectedProperty());
		randomizeButton.managedProperty().bind(sloganCheckBox.selectedProperty());
	}

	private static void addUsernameErrorsListener(TextField usernameTextField, Label error) {
		usernameTextField.textProperty().addListener((observable, oldText, newText) -> {
			if (!FormatValidation.checkUserName(newText))
				error.setText(SignupAndProfileMenuMessage.INVALID_USERNAME.getErrorString());
			else
				error.setText("");
		});
	}

	public void randomizeSloganButtonHandler(MouseEvent mouseEvent) throws Exception {
		sloganTextField.setText(SignupMenuController.generateRandomSlogan());
	}

	public void backButtonHandler(MouseEvent mouseEvent) throws Exception {
		new LoginMenu().start(LoginMenu.getStage());
	}

	public static void run() {
		System.out.println("======[Signup Menu]======");
		
		while (true) {
			String line = MainMenu.getScanner().nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;
			if ((matcher = CommandParser.getMatcher(inputTokens, Command.BACK)) != null)
				return;
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.SIGNUP)) != null)
				System.out.println(SignupMenuController.signup(matcher.get("username"), matcher.get("nickname"),
				matcher.get("password"), matcher.get("email"),
				matcher.get("slogan")).getErrorString());
			else
				System.out.println("invalid command");
		}
	}
	
	public static String[] securityQuestionLoop() {
		while (true) {
			System.out.println("Pick your security question:");
			for (int i = 0; i < CentralController.SECURITY_QUESTIONS.length; i++)
				System.out.println((i + 1) + ". " + CentralController.SECURITY_QUESTIONS[i]);
			String line = MainMenu.getScanner().nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;
			if ((matcher = CommandParser.getMatcher(inputTokens, Command.QUESTION_PICK)) != null) {
				if (matcher.get("answer").equals("") || matcher.get("answerConfirm").equals(""))
					System.out.println("Error: Empty field");
				else if (!matcher.get("answerConfirm").equals(matcher.get("answer")))
					System.out.println("Error: answer confirm must match answer");
				else if (Integer.parseInt(matcher.get("questionNumber")) < 1 ||
					Integer.parseInt(matcher.get("questionNumber")) > CentralController.SECURITY_QUESTIONS.length)
					System.out.println("Error: Incorrect question number");
				else {
					String[] result = new String[2];
					result[0] = matcher.get("questionNumber");
					result[1] = matcher.get("answer");
					return result;
				}
			}
			else {
				System.out.println("Invalid command: please pick a security question");
			}
		}
	}

	public static boolean continueWithRandomUsername(String newUsername) {
		System.out.println("The entered username already exists");
		System.out.println("Do you want to continue with this suggested username?: " + newUsername);
		System.out.println("Enter your choice (y/N): ");
		String answer = MainMenu.getScanner().nextLine().toLowerCase();
		return answer.equals("y");
	}
}
