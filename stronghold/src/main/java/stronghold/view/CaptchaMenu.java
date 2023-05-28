package stronghold.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import stronghold.utils.Captcha;

public class CaptchaMenu extends Application {
	@FXML
	private ImageView captchaImage;
	@FXML
	private TextField answerField;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(CaptchaMenu.class.getResource("/fxml/CaptchaMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	@FXML
	private void initialize() throws Exception {
		renewCaptcha();
	}

	private void renewCaptcha() throws Exception {
		Captcha.generateCaptcha();
		captchaImage.setImage(new Image(CaptchaMenu.class.getResource("/captcha/captcha.png").toExternalForm()));
	}

	public void regenerateButtonHandler(MouseEvent mouseEvent) throws Exception {
		renewCaptcha();
	}

	public void submitButtonHandler(MouseEvent mouseEvent) throws Exception {
		
	}
}
