package stronghold.view;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stronghold.controller.TradeMenuController;
import stronghold.controller.messages.TradeMenuMessage;
import stronghold.model.*;
import stronghold.utils.FormatValidation;
import stronghold.utils.ViewUtils;

public class TradeMenu extends Application {
	private ArrayList<Node> nodes = new ArrayList<>();

	@FXML
	Label creating;

	@FXML
	Label view;

	@FXML
	Pane mainPane;

	public void start(Stage stage) throws IOException {
		Pane pane = FXMLLoader.load(getClass().getResource("/fxml/TradeMenu.fxml"));
		pane.setPrefSize(800, 600);
		// Image image = new Image(getClass().getResource("/pictures/background/EV0qurmWkAIvqfu.jpeg").toExternalForm());
		// Background background = new Background(ViewUtils.setBackGround(image));
		// pane.setBackground(background);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	@FXML
	public void initialize() {
		creating.setOnMouseClicked(event -> {
			handleCreatingMenu();
		});
		view.setOnMouseClicked(event -> {
			handleViewMenu();
		});

	}
	
	private void handleViewMenu() {
		cleanScreen();
		VBox vBox = new VBox();
		nodes.add(vBox);
		Button label1 = new Button("Submitted request");
		label1.setOnMouseClicked(event -> {
			handleSubmittedRequest();});
		Button label2 = new Button("Received Request");
		label2.setOnMouseClicked(event -> {
			handleReceivedRequest();});
		vBox.getChildren().add(label2);
		vBox.getChildren().add(label1);
		mainPane.getChildren().add(vBox);
	}

	private void handleReceivedRequest() {
		cleanScreen();
		VBox vBox = new VBox();
		vBox.setSpacing(40);
		nodes.add(vBox);
		Game currentGame = StrongHold.getCurrentGame();
		for(TradeRequest trade: currentGame.getAllTrades()) {
			if(trade.getReceiverIndex() == StrongHold.getCurrentGame().getCurrentPlayerIndex()) {
				HBox hBox = new HBox();
				hBox.setSpacing(40);
				Text info = new Text(trade.toString());
				hBox.getChildren().add(info);
				if (trade.getState() == TradeRequestState.PENDING) {
					Button accept = new Button("Accept");
					accept.setOnMouseClicked(event -> {
						acceptHandler(trade);
					});
					Button reject = new Button("Reject");
					reject.setOnMouseClicked(event -> {
						handleReject(trade);
					});
					hBox.getChildren().add(accept);
					hBox.getChildren().add(reject);
				}
				vBox.getChildren().add(hBox);
			}
		}
		Button backButton = new Button("Back");
		backButton.setOnMouseClicked(event -> backToFirstScreen());
		vBox.getChildren().add(backButton);
		mainPane.getChildren().add(vBox);
	}

	private void showPopup(String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(alertType.name());
		alert.setHeaderText(alertType.name());
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void handleReject(TradeRequest trade) {
		TradeMenuMessage error = TradeMenuController.tradeReject(trade.getId());
		if (error == TradeMenuMessage.SUCCESSFUL_REJECT)
			showPopup(error.getErrorMessage(), AlertType.INFORMATION);
		else
			showPopup(error.getErrorMessage(), AlertType.ERROR);
	}

	private void acceptHandler(TradeRequest trade) {
		TradeMenuMessage error = TradeMenuController.tradeAccept(trade.getId());
		if (error == TradeMenuMessage.SUCCESSFUL_ACCEPT)
			showPopup(error.getErrorMessage(), AlertType.INFORMATION);
		else
			showPopup(error.getErrorMessage(), AlertType.ERROR);
	}


	private void handlePrsonalTrade(Government governmentTotrade) {
		cleanScreen();
		HBox hBox = new HBox();
		hBox.setSpacing(40);
		hBox.setAlignment(Pos.BASELINE_CENTER);
		nodes.add(hBox);
		for (ResourceType resourceType : ResourceType.values()) {
			if (resourceType.isTradable()) {
					ImageView resourceImage = new ImageView(resourceType.getImage());
					resourceImage.setFitHeight(70);
					resourceImage.setFitWidth(50);
					resourceImage.setOnMouseClicked(event -> {
						handleNumberPage(resourceType, governmentTotrade);
					});
					hBox.getChildren().add(resourceImage);
				
			}
		}
		mainPane.getChildren().add(hBox);
	}

	private void handleNumberPage(ResourceType resourceType, Government reciver) {
		cleanScreen();
		final int[] amount = { 0 };
		VBox vBox = new VBox();
		vBox.setSpacing(40);
		nodes.add(vBox);
		HBox hBox = new HBox();
		hBox.setSpacing(30);
		// nodes.add(hBox);
		ImageView minusIcon = new ImageView(getClass().getResource("/pictures/trade/blackCross.png").toExternalForm());
		minusIcon.setFitWidth(30);
		minusIcon.setFitHeight(30);
		Text number = new Text(amount[0] + "");
		minusIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (amount[0] == 0) return;
				amount[0]--;
				number.setText(Integer.toString(amount[0]));
			}
		});
		ImageView plusIcon = new ImageView(getClass().getResource("/pictures/trade/plus.png").toExternalForm());
		plusIcon.setFitHeight(30);
		plusIcon.setFitWidth(30);
		plusIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				amount[0]++;
				number.setText(Integer.toString(amount[0]));
			}
		});
		hBox.getChildren().add(plusIcon);
		hBox.getChildren().add(number);
		hBox.getChildren().add(minusIcon);
		vBox.getChildren().add(hBox);
		Button donate = new Button("Donate");
		donate.setOnMouseClicked(event -> {
			handleFinallPage(resourceType, amount[0], reciver, true);
		});
		Button request = new Button("Request");
		request.setOnMouseClicked(event -> {
			handleFinallPage(resourceType, amount[0], reciver, false);
		});
		vBox.getChildren().add(donate);
		vBox.getChildren().add(request);
		mainPane.getChildren().add(vBox);
	}

	private void handleFinallPage(ResourceType resourceType, int amount, Government reciver, boolean isDonate) {
		cleanScreen();
		VBox vBox = new VBox();
		TextField textField = new TextField("Trade Message");
		TextField price = new TextField("price");
		Button submit = new Button("Submit");
		submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!isDonate && !FormatValidation.isNumber(price.getText())) {
					showPopup("Invalid price", AlertType.ERROR);
					return;
				}
				TradeMenuMessage error = TradeMenuController.tradeRequest(
					resourceType.getName(),
					amount,
					(isDonate ? 0 : Integer.parseInt(price.getText())),
					textField.getText(),
					StrongHold.getCurrentGame().getGovernmentIndex(reciver)
				);
				if (error == TradeMenuMessage.SUCCESSFUL_REQUEST)
					showPopup(error.getErrorMessage(), AlertType.INFORMATION);
				else
					showPopup(error.getErrorMessage(), AlertType.ERROR);
			}
		});
		Button button = new Button("Back to first");
		button.setOnMouseClicked(event -> backToFirstScreen());
		vBox.getChildren().add(button);
		vBox.getChildren().add(textField);
		if (!isDonate) vBox.getChildren().add(price);
		vBox.getChildren().add(submit);
		nodes.add(vBox);
		mainPane.getChildren().add(vBox);
	}

	private void handleSubmittedRequest() {
		cleanScreen();
		VBox vBox = new VBox();
		vBox.setSpacing(40);
		nodes.add(vBox);
		Game currentGame = StrongHold.getCurrentGame();
		for(TradeRequest trade: currentGame.getAllTrades()) {
			if(trade.getSenderIndex() == StrongHold.getCurrentGame().getCurrentPlayerIndex()) {
				HBox hBox = new HBox();
				hBox.setSpacing(40);
				Text info = new Text(trade.toString());
				hBox.getChildren().add(info);
				vBox.getChildren().add(hBox);
			}
		}
		Button backButton = new Button("Back");
		backButton.setOnMouseClicked(event -> backToFirstScreen());
		vBox.getChildren().add(backButton);
		mainPane.getChildren().add(vBox);
	}

	private void handleCreatingMenu() {
		cleanScreen();
		Game currentGame = StrongHold.getCurrentGame();
		Government currentPlayer = currentGame.getCurrentPlayer();
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.BASELINE_CENTER);
		nodes.add(vBox);
		for (Government government : currentGame.getGovernments()) {
			if (government != currentPlayer) {
				HBox hBox = new HBox();
				hBox.setSpacing(40);
				Text userName = new Text(government.getUser().getUserName());
				userName.setOnMouseClicked(event -> {
					handlePrsonalTrade(government);
				});
				Text popularity = new Text(government.getPopularity() + "");
				Text gold = new Text(government.getGold() + "");
				hBox.getChildren().add(userName);
				hBox.getChildren().add(popularity);
				hBox.getChildren().add(gold);
				// nodes.add(hBox);
				vBox.getChildren().add(hBox);
			}
		}
		mainPane.getChildren().add(vBox);
	}

	private void cleanScreen() {
		creating.setVisible(false);
		creating.setManaged(false);
		view.setVisible(false);
		view.setManaged(false);
		for (Node node : nodes)
			mainPane.getChildren().remove(node);
		nodes.clear();
	}

	private void backToFirstScreen() {
		cleanScreen();
		creating.setVisible(true);
		creating.setManaged(true);
		view.setVisible(true);
		view.setManaged(true);
	}
}
