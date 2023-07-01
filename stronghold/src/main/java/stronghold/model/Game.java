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
	private ArrayList<TradeRequest> allTradeRequests = new ArrayList<>();

	public Game(Map map, User[] players) {
		this.map = map;
		governments = new Government[players.length];
		for (int i = 0; i < players.length; i++) {
			governments[i] = new Government(players[i], i, map);
		}
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
	public void clearSelectedUnits() {
		selectedUnits.clear();
	}
	public void addSelectedUnit(Person person) {
		selectedUnits.add(person);
	}
	public void removeSelectedUnit(Person person) {
		selectedUnits.remove(person);
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

	public ArrayList<TradeRequest> getAllTrades() {
		return allTradeRequests;
	}

	public TradeRequest getTradeById(int id) {
		for (TradeRequest trade : allTradeRequests)
			if (trade.getId() == id)
				return trade;
		return null;
	}

	public void addTrade(TradeRequest trade) {
		allTradeRequests.add(trade);
	}

	public void updateGovernments() {
		for (Government government : governments)
			if (!government.hasLost())
				government.updateStats();
	}

	public int getGovernmentIndex(Government government) {
		for(int i=0; i < governments.length; i++) {
			if(governments[i] == government)
				return  i;
		}
		return 99999;
	}
}
