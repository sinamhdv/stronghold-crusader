package stronghold.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
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

	static void clearMainPane() {	// the main pane shouldn't have other random HBox children
		for (Node node : GameMenu.getInstance().getMainPane().getChildren()) {
			if (!(node instanceof HBox)) continue;
			node.setVisible(false);
			node.setManaged(false);
		}
	}

	static HBox getPopularityFactorLine(String text, int value) {
		ImageView face = new ImageView(GameMenu.popularityFaceEmojies[value < 0 ? 3 : (value == 0 ? 1 : 4)]);
		face.setFitHeight(20);
		face.setFitWidth(20);
		Label label = new Label(text + value);
		return new HBox(10, face, label);
	}
}
