package stronghold.model;

import java.util.ArrayList;
import java.util.Collections;

public class StrongHold {
	private static ArrayList<User> users ;
	private static User currentUser;
	//TODO: private static CurrentGame
	static{
		users = new ArrayList<User>();
	}
	public static User getUserByName(String userName)
	{
		for(User user : users)
		{
			if(user.getUserName().equals(userName)) return user;
		}
		return null;
	}
	public static User getUserByEmail(String email) {
		for(User user : users)
		{
			if(user.getEmail().toLowerCase().equals(email)) return user;
		}
		return null;
	}
	public static void setCurentUser(User user)
	{
		currentUser = user;
	}
	public static User getCurrentUser() {
		return currentUser;
	}
	//TODO: getCurrentGame & setCurrentGame
	public static int getRank(User aUser)
	{
		ArrayList<Integer> highScores = new ArrayList<>();
		for (User user: users) {
			if(!highScores.contains(user.getHighScore()))
				highScores.add(user.getHighScore());
		}
		Collections.sort(highScores);
		Collections.reverse(highScores);
		return highScores.indexOf(aUser.getHighScore())+1;
	}
	public static ArrayList<User> getUsers() {
		return users;
	}
	public static void addUser(User newUser){
		users.add(newUser);
	}
	public static void setUsers(ArrayList<User> users) {
		StrongHold.users = users;
	}
}