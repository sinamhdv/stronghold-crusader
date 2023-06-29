package stronghold.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import stronghold.model.map.Map;

public class GameToolBar {
	static Image getMinimapImage(Map map) {
		WritableImage image = new WritableImage(map.getWidth(), map.getHeight());
		PixelWriter writer = image.getPixelWriter();
		for (int j = 0; j < image.getWidth(); j++) {
			for (int i = 0; i < image.getHeight(); i++) {
				writer.setColor(j, i, map.getGrid()[i][j].getColor());
			}
		}
		return image;
	}

	static void setMinimapMouseHandler(ImageView minimap, ScrollPane scrollPane) {
		minimap.setOnMouseClicked(event -> {
			double x = event.getX() / minimap.getFitWidth();
			double y = event.getY() / minimap.getFitHeight();
			scrollPane.setHvalue(x);
			scrollPane.setVvalue(y);
		});
	}

	static void updateGovernmentReport() {
		
	}
}
