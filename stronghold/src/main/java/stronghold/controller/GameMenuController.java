package stronghold.controller;

import stronghold.controller.messages.GameMenuMessage;
import stronghold.controller.messages.MapEditorMenuMessage;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.model.environment.Wall;
import stronghold.view.GameMenu;

public class GameMenuController {
	private static Game game;

	public static void setGame(Game game) {
		GameMenuController.game = game;
	}

	public static GameMenuMessage dropWall(int x1, int y1, int x2, int y2) {
		int neededStone = Wall.REQUIRED_STONE * (x2 - x1 + 1) * (y2 - y1 + 1);
		if (neededStone > game.getCurrentPlayer().getResourceCount(ResourceType.ROCK))
			return GameMenuMessage.NOT_ENOUGH_RESOURCES;
		MapEditorMenuController.setMap(game.getMap());
		MapEditorMenuController.setSelectedGovernment(game.getCurrentPlayerIndex());
		MapEditorMenuMessage message = MapEditorMenuController.dropWall(x1, y1, x2, y2);
		if (message != MapEditorMenuMessage.SUCCESS) {
			GameMenu.showMapEditorError(message);
			return GameMenuMessage.CONSTRUCTION_FAILED;
		}
		game.getCurrentPlayer().decreaseResource(ResourceType.ROCK, neededStone);
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage dropBuilding(int x, int y, String type) {
		if (!hasEnoughResourcesForObject(type, game.getCurrentPlayer()))
			return GameMenuMessage.NOT_ENOUGH_RESOURCES;
		MapEditorMenuController.setMap(game.getMap());
		MapEditorMenuController.setSelectedGovernment(game.getCurrentPlayerIndex());
		MapEditorMenuMessage message = MapEditorMenuController.dropBuilding(x, y, type);
		if (message != MapEditorMenuMessage.SUCCESS) {
			GameMenu.showMapEditorError(message);
			return GameMenuMessage.CONSTRUCTION_FAILED;
		}
		decreaseObjectsResources(type, game.getCurrentPlayer());
		return GameMenuMessage.SUCCESS;
	}

	private static boolean hasEnoughResourcesForObject(String objectName, Government government) {

	}

	private static boolean decreaseObjectsResources(String objectName, Government government) {

	}

	public static int getPopularityInfluencingFood(int foodRate) {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		int influencingFood = foodRate * 2;
		if (currentPlayer.getFoodVariety() - 1 > 0) {
			influencingFood += currentPlayer.getFoodVariety() - 1;
		}
		return influencingFood;
	}

	public static int getTaxPopularityInfluencing(int taxRate) {
		if (taxRate > -4 && taxRate < 1)
			return (-2) * taxRate + 1;
		else if (taxRate > 0 && taxRate < 5)
			return taxRate * (-2);
		else if (taxRate > 4 && taxRate < 9)
			return (-4) * taxRate + 8;
		else
			return 9999;
	}

	public static GameMenuMessage setFoodRate(int foodRate) {
		Government currentPleyer = StrongHold.getCurrentGame().getCurrentPlayer();
		if (foodRate < -2 || foodRate > 2)
			return GameMenuMessage.INVALID_FOOD_RATE;
		currentPleyer.setFoodRate(foodRate);
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage setTaxRate(int taxRate) {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		if (taxRate < -3 || taxRate > 8)
			return GameMenuMessage.INVALID_TAX_RATE;
		currentPlayer.setTaxRate(taxRate);
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage setFearRate (int fearRate) {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		if (fearRate < -5 || fearRate > 5) 
			return GameMenuMessage.INVALID_FEAR_RATE;
		currentPlayer.setFearFactor(fearRate);
		return GameMenuMessage.SUCCESS;
	}
}
