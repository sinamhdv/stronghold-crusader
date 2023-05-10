package stronghold;

import java.io.IOException;

import stronghold.utils.DatabaseManager;
import stronghold.view.LoginMenu;

public class Main {
	public static void main(String[] args) throws IOException {
		DatabaseManager.loadUsers();
		GenerateConfig.run();
		RunTests.run();
		LoginMenu.run();
	}
}
