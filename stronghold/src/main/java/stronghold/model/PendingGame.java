package stronghold.model;

import java.util.ArrayList;

import stronghold.model.map.Map;

public class PendingGame {
	private Map map;
	private final ArrayList<User> players = new ArrayList<>();
	
	public PendingGame(Map map, User admin) {
		this.map = map;
		this.players.add(admin);
	}

	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}

	public ArrayList<User> getPlayers() {
		return players;
	}
	public void addPlayer(User user) {
		players.add(user);
	}
}
