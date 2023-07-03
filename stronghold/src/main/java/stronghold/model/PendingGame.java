package stronghold.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PendingGame implements Serializable {
	private Game game;
	private final ArrayList<User> players = new ArrayList<>();
	
	public PendingGame(Game game, User admin) {
		this.game = game;
		this.players.add(admin);
	}

	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}

	public ArrayList<User> getPlayers() {
		return players;
	}
	public void addPlayer(User user) {
		players.add(user);
	}
}
