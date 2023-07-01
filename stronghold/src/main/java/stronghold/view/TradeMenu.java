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
		Image image = new Image(getClass().getResource("/pictures/background/EV0qurmWkAIvqfu.jpeg").toExternalForm());
		Background background = new Background(ViewUtils.setBackGround(image));
		pane.setBackground(background);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	@FXML
	public void initialize() {
		nodes.add(creating);
		nodes.add(view);
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
		Label label1 = new Label("Submitted request");
		label1.setOnMouseClicked(event -> {
			handleSubmittedRequest();});
		Label label2 = new Label("Received Request");
		label2.setOnMouseClicked(event -> {
			handleReceivedRequest();});
		vBox.getChildren().add(label2);
		vBox.getChildren().add(label1);
		mainPane.getChildren().add(vBox);
	}

	private void handleReceivedRequest() {
		VBox vBox = new VBox();
		vBox.setSpacing(40);
		nodes.add(vBox);
		Game currentGame = StrongHold.getCurrentGame();
		Government currentGovernment = currentGame.getCurrentPlayer();
		for(TradeRequest trade: currentGame.getAllTrades()) {
			if(trade.getReceiverIndex() == currentGame.getGovernmentIndex(currentGovernment)) {
				HBox hBox = new HBox();
				hBox.setSpacing(40);
				Text state = new Text(trade.getState().getStateString());
				Text reciver = new Text(currentGame.getGovernments()[trade.getReceiverIndex()].getUser().getUserName());
				Text resourceType = new Text(trade.getResourceType().getName());
				Text amount = new Text(trade.getAmount() +"");
				Text messege = new Text(trade.getMessage());
				Text id = new Text(trade.getId() +"");
				hBox.getChildren().add(state);
				hBox.getChildren().add(reciver);
				hBox.getChildren().add(resourceType);
				hBox.getChildren().add(amount);
				hBox.getChildren().add(messege);
				hBox.getChildren().add(id);
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
			}
		}
	}

	private void handleReject(TradeRequest trade) {
	}

	private void acceptHandler(TradeRequest trade) {
	}

	private void handleSubmittedRequest() {

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
		nodes.add(hBox);
		ImageView minecIcon = new ImageView(getClass().getResource("/pictures/trade/blackCross.png").toExternalForm());
		minecIcon.setFitWidth(30);
		minecIcon.setFitHeight(30);
		Text Number = new Text(amount[0] + "");
		minecIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				amount[0]--;
				Number.setText(Integer.toString(amount[0]));
			}
		});
		ImageView plusIcon = new ImageView(getClass().getResource("/pictures/trade/plus.png").toExternalForm());
		plusIcon.setFitHeight(30);
		plusIcon.setFitWidth(30);
		plusIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				amount[0]++;
				Number.setText(Integer.toString(amount[0]));
			}
		});
		hBox.getChildren().add(plusIcon);
		hBox.getChildren().add(Number);
		hBox.getChildren().add(minecIcon);
		vBox.getChildren().add(hBox);
		Button donate = new Button("Donate");
		donate.setOnMouseClicked(event -> {
			handleFinallPage(resourceType, amount[0], reciver);
		});
		Button request = new Button("Request");
		request.setOnMouseClicked(event -> {
			handleFinallPage(resourceType, amount[0], reciver);
		});
		vBox.getChildren().add(donate);
		vBox.getChildren().add(request);
		mainPane.getChildren().add(vBox);
	}

	private void handleFinallPage(ResourceType resourceType, int amount, Government reciver) {
		cleanScreen();
		VBox vBox = new VBox();
		TextField textField = new TextField("Trade Message");
		Button submit = new Button("Submit");
		submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
			TradeMenuMessage error = TradeMenuController.tradeRequest(resourceType.getName(), amount,0, textField.getText(), StrongHold.getCurrentGame().getGovernmentIndex(reciver));
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("submit error");
				alert.setContentText(error.getErrorMessage());
				alert.showAndWait();
			}
		});
		vBox.getChildren().add(textField);
		vBox.getChildren().add(submit);
		nodes.add(vBox);
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
				nodes.add(hBox);
				vBox.getChildren().add(hBox);
			}
		}
		mainPane.getChildren().add(vBox);
	}

	public void cleanScreen() {
		for (Node node : nodes) {
			node.setVisible(false);
			node.setManaged(false);
		}
	}


	private static void printNotifications() {
		System.out.println("======[Trade Menu]======");
		System.out.println("New trade requests to you: ");
		for (TradeRequest request : StrongHold.getCurrentGame().getAllTrades()) {
			if (request.getReceiverIndex() == StrongHold.getCurrentGame().getCurrentPlayerIndex() &&
					!request.isSeen()) {
				System.out.println(request);
				request.setSeen(true);
			}
		}
	}

	private static void showTradeHistory() {
		System.out.println("====== Requests Sent By You ======");
		int myIndex = StrongHold.getCurrentGame().getCurrentPlayerIndex();
		for (TradeRequest request : StrongHold.getCurrentGame().getAllTrades())
			if (request.getSenderIndex() == myIndex)
				System.out.println(request);
		System.out.println();
		System.out.println("====== Requests You've Accepted or Rejected ======");
		for (TradeRequest request : StrongHold.getCurrentGame().getAllTrades())
			if (request.getState() != TradeRequestState.PENDING && request.getReceiverIndex() == myIndex)
				System.out.println(request);
		System.out.println();
	}

	private static void showTradeList() {
		System.out.println("====== Active Trade Requests ======");
		int myIndex = StrongHold.getCurrentGame().getCurrentPlayerIndex();
		for (TradeRequest request : StrongHold.getCurrentGame().getAllTrades())
			if (request.getReceiverIndex() == myIndex && request.getState() == TradeRequestState.PENDING)
				System.out.println(request);
		System.out.println();
	}

	

}
