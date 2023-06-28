package stronghold.utils;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Screen;

public class ViewUtils {
	public static BackgroundImage setBackGround(Image image) {
		BackgroundImage backgroundImage = new BackgroundImage(image,
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				BackgroundSize.DEFAULT);
		return backgroundImage;
	}

	public static int getScreenHeight() {
		return (int)(Screen.getPrimary().getBounds().getHeight() + 0.5);
	}

	public static int getScreenWidth() {
		return (int)(Screen.getPrimary().getBounds().getWidth() + 0.5);
	}
}
