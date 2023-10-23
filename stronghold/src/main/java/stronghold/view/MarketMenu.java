package stronghold.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stronghold.controller.MarketMenuController;
import stronghold.controller.messages.MarketMenuMessage;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.utils.FormatValidation;
import stronghold.utils.ViewUtils;


public class MarketMenu extends Application {

	static Scene scene;

	@FXML
	private ScrollPane scroll;

	@FXML
	private VBox vBox;
	

	@Override
	public void start(Stage stage) throws Exception {
		Pane pane = FXMLLoader.load(CaptchaMenu.class.getResource("/fxml/MarketMenu.fxml"));
		pane.setPrefSize(800, 600);
		Image image = new Image(getClass().getResource("/pictures/background/wp6475236.jpg").toExternalForm());
		Background background = new Background(ViewUtils.setBackGround(image));
		pane.setBackground(background);
		scene = new Scene(pane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	@FXML
	public  void  initialize() {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		vBox.setSpacing(10);
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		for(ResourceType resourceType: ResourceType.values()) {
			if (!resourceType.isTradable()) continue;
			HBox hBox = new HBox();
			hBox.setSpacing(30);
			hBox.setAlignment(Pos.CENTER_LEFT);
			ImageView resourceImage = new ImageView(resourceType.getImage());
			resourceImage.setFitHeight(70);
			resourceImage.setFitWidth(50);
			hBox.getChildren().add(resourceImage);
			Text yourStock = new Text(currentPlayer.getResourceCount(resourceType)+"");
			hBox.getChildren().add(yourStock);
			TextField number = new TextField();
			hBox.getChildren().add(number);
			ImageView buyIcon = new ImageView(getClass().getResource("/pictures/market/Buy.png").toExternalForm());
			buyIcon.setFitWidth(38);
			buyIcon.setFitHeight(35);
			hBox.getChildren().add(buyIcon);
			buyIcon.setOnMouseClicked(event -> { handleBuy(resourceType, number, yourStock); });
			ImageView sellIcon = new ImageView(getClass().getResource("/pictures/market/cartpng.parspng.com-3.png").toExternalForm());
			sellIcon.setFitWidth(38);
			sellIcon.setFitHeight(35);
			hBox.getChildren().add(sellIcon);
			sellIcon.setOnMouseClicked(event -> { handleSell(resourceType, number, yourStock); });
			Text priceText = new Text("Buy Price: "+ resourceType.getBuyPrice() + "\nSell Price: " + resourceType.getSellprice());
			hBox.getChildren().add(priceText);
			vBox.getChildren().add(hBox);
		}
	}

	private void errorPopup(String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(alertType == AlertType.ERROR ? "Error" : "Success");
		alert.setHeaderText(alertType == AlertType.ERROR ? "Error" : "Success");
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void handleBuy(ResourceType resourceType, TextField number, Text yourStock) {
		if (!FormatValidation.isNumber(number.getText())) {
			errorPopup("Invalid number", AlertType.ERROR);
			return;
		}
		MarketMenuMessage error = MarketMenuController.buyItem(resourceType.getName(), Integer.parseInt(number.getText()));
		if(error == MarketMenuMessage.SUCCESSFUL_BUY) {
			errorPopup(error.getErrorString(), AlertType.INFORMATION);
			refreshStock(yourStock, resourceType);
		}
		else
			errorPopup(error.getErrorString(), AlertType.ERROR);
	}

	public void handleSell(ResourceType resourceType, TextField number, Text yourStock) {
		if (!FormatValidation.isNumber(number.getText())) {
			errorPopup("Invalid number", AlertType.ERROR);
			return;
		}
		MarketMenuMessage error = MarketMenuController.sellItem(resourceType.getName(), Integer.parseInt(number.getText()));
		if(error == MarketMenuMessage.SUCCESSFUL_SELL) {
			errorPopup(error.getErrorString(), AlertType.INFORMATION);
			refreshStock(yourStock, resourceType);
		}
		else
			errorPopup(error.getErrorString(), AlertType.ERROR);
	}

	private static void refreshStock(Text yourStock, ResourceType resourceType) {
		yourStock.setText(Integer.toString(
			StrongHold.getCurrentGame().getCurrentPlayer().getResourceCount(resourceType)));
	}

	public void backHandler(MouseEvent event) throws Exception {
		GameMenu.getInstance().showSavedScene();
	}
}
