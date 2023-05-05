package stronghold.model;

import java.util.ArrayList;
import java.util.Collections;

import stronghold.controller.DatabaseManager;

public class StrongHold {
	private static ArrayList<User> users = new ArrayList<>();
	private static User currentUser;
	private static Game currentGame;

	public static User getUserByName(String userName)
	{
		for(User user : users)
		{
			if(user.getUserName().equals(userName)) return user;
		}
		return null;
	}
	public static User getUserByEmail(String email) {
		for(User user : users) {
			if(user.getEmail().toLowerCase().equals(email.toLowerCase())) return user;
		}
		return null;
	}
	public static void setCurrentUser(User currentUser) {
		StrongHold.currentUser = currentUser;
	}
	public static User getCurrentUser() {
		return currentUser;
	}
	public static Game getCurrentGame() {
		return currentGame;
	}
	public static void setCurrentGame(Game currentGame) {
		StrongHold.currentGame = currentGame;
	}
	public static int getRank(User aUser)
	{
		ArrayList<Integer> highScores = new ArrayList<>();
		for (User user: users)
				highScores.add(user.getHighScore());
		Collections.sort(highScores);
		Collections.reverse(highScores);
		return highScores.indexOf(aUser.getHighScore())+1;
	}
	public static ArrayList<User> getUsers() {
		return users;
	}
	public static void addUser(User newUser){
		users.add(newUser);
		DatabaseManager.updateUser(newUser);
	}
	public static void setUsers(ArrayList<User> users) {
		StrongHold.users = users;
	}
}