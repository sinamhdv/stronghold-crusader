package stronghold;

import stronghold.controller.DatabaseManager;
import stronghold.view.LoginMenu;

public class Main {
	public static void main(String[] args) {
		DatabaseManager.loadUsers();
		RunTests.run();
		LoginMenu.run();
	}
}
