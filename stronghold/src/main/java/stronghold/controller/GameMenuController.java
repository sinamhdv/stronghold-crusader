package stronghold.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import stronghold.controller.messages.GameMenuMessage;
import stronghold.controller.messages.MapEditorMenuMessage;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.model.buildings.Barracks;
import stronghold.model.buildings.Building;
import stronghold.model.buildings.DefensiveStructure;
import stronghold.model.buildings.DefensiveStructureType;
import stronghold.model.map.Pathfinding;
import stronghold.model.people.Person;
import stronghold.model.people.PersonType;
import stronghold.model.people.StanceType;
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

	public static GameMenuMessage createSingleUnit(String type) {
		if (!(game.getSelectedBuilding() instanceof Barracks))
			return GameMenuMessage.BAD_SELECTED_BUILDING;
		if (!((Barracks) game.getSelectedBuilding()).canBuildTroop(type))
			return GameMenuMessage.INCORRECT_UNIT_NAME;
		if (!hasEnoughResourcesForObject(type, game.getCurrentPlayer()))
			return GameMenuMessage.NOT_ENOUGH_RESOURCES;
		if (game.getCurrentPlayer().getPopulation() - game.getCurrentPlayer().getWorkersCount() < 1)
			return GameMenuMessage.NOT_ENOUGH_PEASANTS;
		int[] keep = game.getCurrentPlayer().findKeep();
		MapEditorMenuController.setMap(game.getMap());
		MapEditorMenuController.setSelectedGovernment(game.getCurrentPlayerIndex());
		MapEditorMenuMessage message = MapEditorMenuController.dropUnit(keep[0], keep[1], type, 1);
		if (message != MapEditorMenuMessage.SUCCESS) {
			GameMenu.showMapEditorError(message);
			return GameMenuMessage.CONSTRUCTION_FAILED;
		}
		decreaseObjectsResources(type, game.getCurrentPlayer());
		ArrayList<Person> tilePeople = game.getMap().getGrid()[keep[0]][keep[1]].getPeople();
		game.getCurrentPlayer().addPerson(tilePeople.get(tilePeople.size() - 1));
		game.getCurrentPlayer().decreasePopulation(1);
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage createUnit(String type, int count) {
		for (int i = 0; i < count; i++) {
			GameMenuMessage message = createSingleUnit(type);
			if (message != GameMenuMessage.SUCCESS) return message;
		}
		return GameMenuMessage.SUCCESS;
	}

	private static boolean hasEnoughResourcesForObject(String objectName, Government government,
			int repairedParts, int totalParts) {
		HashMap<ResourceType, Integer> requiredResources = ConfigManager.getRequiredResources(objectName);
		if (requiredResources == null)
			return true;
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
		if (requiredResources == null)
			return;
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
		if (!building.isSelectable())
			return GameMenuMessage.NOT_SELECTABLE;
		game.setSelectedBuilding(building);
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage changeGateState(boolean close) {
		if (!(game.getSelectedBuilding() instanceof DefensiveStructure))
			return GameMenuMessage.BAD_SELECTED_BUILDING;
		DefensiveStructure building = (DefensiveStructure) game.getSelectedBuilding();
		if (building.getType() != DefensiveStructureType.GATE)
			return GameMenuMessage.BAD_SELECTED_BUILDING;
		if (close) {
			if (building.isCaptured())
				return GameMenuMessage.GATE_CAPTURED;
			building.closeGate();
		}
		else
			building.openGate();
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage buildSiegeEquipment(String name) {
		HashMap<ResourceType, Integer> resources = ConfigManager.getRequiredResources(name);
		if (resources == null)
			return GameMenuMessage.INVALID_EQUIPMENT_NAME;
		int enginnersCount = resources.get(ResourceType.POPULATION);
		if (game.getSelectedUnits().isEmpty())
			return GameMenuMessage.NO_UNIT_SELECTED;
		int tileX = game.getSelectedUnits().get(0).getX();
		int tileY = game.getSelectedUnits().get(0).getY();
		ArrayList<Person> people = new ArrayList<>(game.getMap().getGrid()[tileX][tileY].getPeople());
		for (Person person : people)
			if (person.getType() != PersonType.ENGINEER || person.getOwnerIndex() != game.getCurrentPlayerIndex())
				return GameMenuMessage.BAD_UNITS_PRESENT;
		if (people.size() < enginnersCount)
			return GameMenuMessage.NOT_ENOUGH_ENGINEERS;
		for (Person person : people)
			person.die();
		// TODO: create a siege tent
		// TODO: create the siege equipment after 3 turns
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
		if (game.getSelectedUnits().isEmpty())
			return GameMenuMessage.NO_UNIT_SELECTED;
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage moveUnit(int x, int y) {
		for (Person unit : game.getSelectedUnits())
			unit.stop();
		if (!Miscellaneous.checkCoordinatesOnMap(game.getMap(), x, y))
			return GameMenuMessage.INVALID_COORDINATES;
		if (game.getSelectedUnits().isEmpty())
			return GameMenuMessage.NO_UNIT_SELECTED;
		for (Person unit : game.getSelectedUnits())
			unit.setMoveDestination(x, y);
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage stopUnit() {
		if (game.getSelectedUnits().isEmpty())
			return GameMenuMessage.NO_UNIT_SELECTED;
		for (Person unit : game.getSelectedUnits())
			unit.stop();
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage patrolUnit(int x, int y) {
		for (Person unit : game.getSelectedUnits())
			unit.stop();
		if (!Miscellaneous.checkCoordinatesOnMap(game.getMap(), x, y))
			return GameMenuMessage.INVALID_COORDINATES;
		if (game.getSelectedUnits().isEmpty())
			return GameMenuMessage.NO_UNIT_SELECTED;
		for (Person unit : game.getSelectedUnits())
			unit.setPatrol(x, y);
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage nextTurn() {
		// actions that must be done after each player switch
		Pathfinding.clearCache();
		game.setSelectedBuilding(null);
		game.clearSelectedUnits();
		handleTroopNextTurnUpdate();
		if (game.getCurrentPlayerIndex() == game.getMap().getGovernmentsCount() - 1) {
			// actions that must be done after a full turn
			game.updateGovernments();
			handleTroopFullTurnUpdate();
			game.setPassedTurns(game.getPassedTurns() + 1);
		}
		if (checkGameEnding()) return GameMenuMessage.END_GAME;
		game.setCurrentPlayerIndex((game.getCurrentPlayerIndex() + 1) % game.getMap().getGovernmentsCount());
		return GameMenuMessage.SUCCESS;
	}

	private static boolean checkGameEnding() {
		int lostCount = 0;
		Government winner = null;
		for (Government government : game.getGovernments()) {
			if (government.hasLost())
				lostCount++;
			else
				winner = government;
		}
		if (lostCount >= game.getGovernments().length - 1) {
			handleWinner(winner);
			return true;
		}
		return false;
	}

	private static void handleWinner(Government winner) {
		GameMenu.showWinner(winner);
		if (winner == null) return;
		User user = winner.getUser();
		user.setHighScore(user.getHighScore() + game.getGovernments().length - 1);
	}

	private static void handleTroopNextTurnUpdate() {
		ArrayList<Person> peopleClone = new ArrayList<>(game.getCurrentPlayer().getPeople());
		for (Person person : peopleClone)
			person.nextTurnUpdate();
	}

	private static void handleTroopFullTurnUpdate() {
		ArrayList<Person> allPeople = new ArrayList<>();
		for (int i = 0; i < game.getGovernments().length; i++)
			allPeople.addAll(game.getGovernments()[i].getPeople());
		for (Person person : allPeople)
			person.fullTurnUpdate();
	}

	public static GameMenuMessage setStance(String stanceName) {
		StanceType stance;
		try {
			stance = StanceType.valueOf(stanceName);
		} catch (IllegalArgumentException ex) {
			return GameMenuMessage.INVALID_STANCE;
		}
		if (game.getSelectedUnits().isEmpty())
			return GameMenuMessage.NO_UNIT_SELECTED;
		for (Person person : game.getSelectedUnits())
			person.setStance(stance);
		return GameMenuMessage.SUCCESS;
	}

	public static GameMenuMessage attack(int targetX, int targetY) {
		for (Person unit : game.getSelectedUnits())
			unit.stop();
		if (!Miscellaneous.checkCoordinatesOnMap(game.getMap(), targetX, targetY))
			return GameMenuMessage.INVALID_COORDINATES;
		if (game.getSelectedUnits().isEmpty())
			return GameMenuMessage.NO_UNIT_SELECTED;
		for (Person person : game.getSelectedUnits())
			person.setAttackTarget(targetX, targetY);
		return GameMenuMessage.SUCCESS;
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

	public static GameMenuMessage repair() {
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
					ArrayList<Person> peopleArrayList = new ArrayList<>(
							currentGame.getMap().getGrid()[i][j].getPeople());
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

	public static Government getWinnerGovernment() {
		ArrayList<Government> players = new ArrayList<>(Arrays.asList(game.getGovernments()));
		for (Government player : players) {
			ArrayList<Person> peopleOfGovernment = player.getPeople();
			boolean isLordDie = true;
			for(Person person : peopleOfGovernment) {
				if (person.getType() == PersonType.LORD) {
					isLordDie = false;
					break;
				}
			}
			if (player.findKeep() == null || isLordDie) 
				players.remove(player);
		}
		if (players.size() == 1)
			return players.get(0);
		return null;
	}

}
