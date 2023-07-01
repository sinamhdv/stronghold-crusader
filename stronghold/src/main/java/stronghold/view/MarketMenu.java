package stronghold.view;

import java.util.HashMap;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stronghold.controller.MarketMenuController;
import stronghold.controller.messages.MarketMenuMessage;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
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
		for(ResourceType resourceType: ResourceType.values()) {
			HBox hBox = new HBox();
			hBox.setSpacing(40);
			ImageView resourceImage = new ImageView(resourceType.getImage());
			resourceImage.setFitHeight(70);
			resourceImage.setFitWidth(50);
			hBox.getChildren().add(resourceImage);
			resourceImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					Text priceText = new Text("Buy Price : "+ resourceType.getBuyPrice()+"\n"+"Sell Price : " + resourceType.getSellprice());
					hBox.getChildren().add(priceText);
					priceText.setY(resourceImage.getY());
				}
			});
			Text yourStock = new Text(currentPlayer.getResourceCount(resourceType)+"");
			hBox.getChildren().add(yourStock);
			TextField number = new TextField();
			hBox.getChildren().add(number);
			number.setLayoutY(resourceImage.getY());
			yourStock.setY(resourceImage.getY());
			ImageView buyIcon = new ImageView(getClass().getResource("/pictures/market/Buy.png").toExternalForm());
			buyIcon.setFitWidth(38);
			buyIcon.setFitHeight(35);
			hBox.getChildren().add(buyIcon);
			buyIcon.setY(resourceImage.getY());
			buyIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					MarketMenuMessage error = MarketMenuController.buyItem(resourceType.getName(), Integer.parseInt(number.getText()));
					if(error == MarketMenuMessage.SUCCESSFUL_BUY) {
						try {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Success");
							alert.setContentText(error.getErrorString());
							alert.showAndWait();
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
					else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("buy error");
						alert.setContentText(error.getErrorString());
						alert.showAndWait();
					}
				}
			});
		ImageView sellIcon = new ImageView(getClass().getResource("/pictures/market/cartpng.parspng.com-3.png").toExternalForm());
		sellIcon.setFitWidth(38);
		sellIcon.setFitHeight(35);
		hBox.getChildren().add(sellIcon);
		sellIcon.setY(resourceImage.getY());
		sellIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				MarketMenuMessage error = MarketMenuController.sellItem(resourceType.getName(), Integer.parseInt(number.getText()));
				if(error == MarketMenuMessage.SUCCESSFUL_SELL) {
					try {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Success");
						alert.setContentText(error.getErrorString());
						alert.showAndWait();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
				else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("sell error");
					alert.setContentText(error.getErrorString());
					alert.showAndWait();
				}
			}
		});
			vBox.getChildren().add(hBox);
		}

		//

	}
	private static void printMenuPrompt() {
		TerminalColor.setColor(TerminalColor.BLACK, TerminalColor.YELLOW);
		System.out.print("market> ");
		TerminalColor.resetColor();
	}


	public static void showPriceList() {
		Government currnetPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		System.out.println("ITEM\t\t\tBUYPRICE\t\tSELLPRICE\t\tYOURASSET");
		for (ResourceType resource : ResourceType.values()) {
			if (!resource.isTradable()) continue;
			int asset = currnetPlayer.getResourceCount(resource);
			System.out.println(resource.getName() + "\t\t\t" + resource.getBuyPrice() + "\t\t" + resource.getSellprice()
					+ "\t\t" + asset);
		}
	}

	public static void runBuyItem(HashMap<String, String> matcher) {
		System.out.println(
				MarketMenuController.buyItem(matcher.get("itemName"), Integer.parseInt(matcher.get("amount")))
						.getErrorString());
	}

	public static void runSellItem(HashMap<String, String> matcher) {
		System.out.println(MarketMenuController
				.sellItem(matcher.get("itemName"), Integer.parseInt(matcher.get("amount"))).getErrorString());
	}


}
