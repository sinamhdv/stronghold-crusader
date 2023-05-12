package stronghold.controller;

import java.util.ArrayList;
import java.util.HashMap;

import stronghold.controller.messages.GameMenuMessage;
import stronghold.controller.messages.MapEditorMenuMessage;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.model.buildings.Building;
import stronghold.model.buildings.DefensiveStructure;
import stronghold.model.people.Person;
import stronghold.utils.ConfigManager;
import stronghold.utils.Miscellaneous;
import stronghold.view.GameMenu;

public class GameMenuController {
	private static Game game;
	static final int repairErrorEnemyRadius = 5;

	public static void setGame(Game game) {
		GameMenuController.game = game;
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
		game.getCurrentPlayer().addBuilding(game.getMap().getGrid()[x][y].getBuilding());
		return GameMenuMessage.SUCCESS;
	}

	private static boolean hasEnoughResourcesForObject(String objectName, Government government,
			int repairedParts, int totalParts) {
		HashMap<ResourceType, Integer> requiredResources = ConfigManager.getRequiredResources(objectName);
		for (ResourceType resourceType : requiredResources.keySet()) {
			int requiredPart = (repairedParts * requiredResources.get(resourceType) + totalParts - 1) / totalParts;
			if (government.getResourceCount(resourceType) < requiredPart)
				return false;
		}
		return true;
	}

	private static boolean hasEnoughResourcesForObject(String objectName, Government government) {
		return hasEnoughResourcesForObject(objectName, government, 1, 1);
	}

	private static void decreaseObjectsResources(String objectName, Government government,
			int repairedParts, int totalParts) {
		HashMap<ResourceType, Integer> requiredResources = ConfigManager.getRequiredResources(objectName);
		for (ResourceType resourceType : requiredResources.keySet())
			government.decreaseResource(resourceType,
					(repairedParts * requiredResources.get(resourceType) + totalParts - 1) / totalParts);
	}

	private static void decreaseObjectsResources(String objectName, Government government) {
		decreaseObjectsResources(objectName, government, 1, 1);
	}

	public static GameMenuMessage selectBuilding(int x, int y) {
		game.setSelectedBuilding(null);
		if (!Miscellaneous.checkCoordinatesOnMap(game.getMap(), x, y))
			return GameMenuMessage.INVALID_COORDINATES;
		Building building = game.getMap().getGrid()[x][y].getBuilding();
		if (building == null)
			return GameMenuMessage.NO_BUILDING_FOUND;
		if (building.getOwnerIndex() != game.getCurrentPlayerIndex())
			return GameMenuMessage.BUILDING_NOT_YOURS;
		game.setSelectedBuilding(building);
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage selectUnit(int x, int y) {
		game.clearSelectedUnits();
		if (!Miscellaneous.checkCoordinatesOnMap(game.getMap(), x, y))
			return GameMenuMessage.INVALID_COORDINATES;
		ArrayList<Person> units = game.getMap().getGrid()[x][y].getPeople();
		units = Miscellaneous.getPeopleByOwner(units, game.getCurrentPlayerIndex());
		for (Person person : units)
			game.addSelectedUnit(person);
		return GameMenuMessage.SUCCESS;
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

	public static GameMenuMessage setFearRate(int fearRate) {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		if (fearRate < -5 || fearRate > 5)
			return GameMenuMessage.INVALID_FEAR_RATE;
		currentPlayer.setFearFactor(fearRate);
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage Repair() {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		Game currentGame = StrongHold.getCurrentGame();
		Building building = currentGame.getSelectedBuilding();

		if (building == null)
			return GameMenuMessage.THERE_IS_NO_SELECTED_BUILDING;
		else if (building instanceof DefensiveStructure) {
			int lengthOfX = building.getX() + building.getHeight() - 1;
			int lengthOfY = building.getY() + building.getWidth() - 1;
			for (int i = lengthOfX; i <= lengthOfX + repairErrorEnemyRadius; i++) {
				for (int j = lengthOfY; j <= lengthOfY + repairErrorEnemyRadius; j++) {
					ArrayList<Person> peopleArrayList = currentGame.getMap().getGrid()[i][j].getPeople();
					for (Person person : peopleArrayList) {
						if (person.getOwner() != currentPlayer)
							return GameMenuMessage.THERE_ARE_ENEMY_SOLDIERS;
					}
				}
			}
			if (!hasEnoughResourcesForObject(building.getName(), currentPlayer, building.getMaxHp() - building.getHp(),
					building.getMaxHp()))
				return GameMenuMessage.NOT_ENOUGH_RESOURCES;
			else {
				decreaseObjectsResources(building.getName(), currentPlayer, building.getMaxHp() - building.getHp(),
						building.getMaxHp());
				building.setHp(building.getMaxHp());
				return GameMenuMessage.SUCCESSFULL_REPAIR;
			}
		} else
			return GameMenuMessage.CANT_REPAIR;
	}
}
