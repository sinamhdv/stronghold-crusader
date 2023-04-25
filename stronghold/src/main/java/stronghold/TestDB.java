package stronghold;

import stronghold.controller.DatabaseManager;
import stronghold.model.StrongHold;
import stronghold.model.User;

public class TestDB {
	public static void run() {
		System.out.println(StrongHold.getUsers());
		User user = new User("sina", "pwd", "\\mmd\"", "haha", "abcd@exmaple.com", 10, 1, "hello");
		StrongHold.addUser(user);
		DatabaseManager.updateUser(user);
		DatabaseManager.loadUsers();
		user = StrongHold.getUsers().get(0);
		System.out.println(user.getEmail());
		System.out.println(user.getNickName());
	}
}
