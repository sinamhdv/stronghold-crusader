package stronghold.model;

import java.util.ArrayList;

import stronghold.model.buildings.Building;
import stronghold.model.map.Map;
import stronghold.model.people.Person;

public class Game {
	private final Map map;
	private final Government[] governments;
	private int currentPlayerIndex = 0;
	private int passedTurns = 0;
	private final ArrayList<Person> selectedUnits = new ArrayList<>();
	private Building selectedBuilding = null;

	public Game(Map map, Government[] governments) {
		this.governments = governments;
		this.map = map;
	}

	public Map getMap() {
		return map;
	}
	public Government[] getGovernments() {
		return governments;
	}
	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}
	public int getPassedTurns() {
		return passedTurns;
	}
	public ArrayList<Person> getSelectedUnits() {
		return selectedUnits;
	}
	public Building getSelectedBuilding() {
		return selectedBuilding;
	}
	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}
	public void setPassedTurns(int passedTurns) {
		this.passedTurns = passedTurns;
	}
	public void setSelectedBuilding(Building selectedBuilding) {
		this.selectedBuilding = selectedBuilding;
	}
	public Government getCurrentPlayer() {
		return governments[currentPlayerIndex];
	}
}
