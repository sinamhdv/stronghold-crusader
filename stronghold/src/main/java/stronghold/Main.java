package stronghold;

import stronghold.utils.DatabaseManager;
import stronghold.view.LoginMenu;

public class Main {
	public static void main(String[] args) {
		DatabaseManager.loadUsers();
		GenerateConfig.run();
		RunTests.run();
		LoginMenu.run();
	}
}
