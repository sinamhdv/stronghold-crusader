package stronghold;

import stronghold.controller.DatabaseManager;
import stronghold.view.LoginMenu;

public class Main {
	public static void main(String[] args) {
		DatabaseManager.loadUsers();
		LoginMenu.run();
	}
}

// signup -u username123 -p password123 -c password123 –e email@mail.com -n nickname123 -s slogan
// signup -u username123 -p password123 -c password123 -e email@mail.com -n nickname123 -s slogan
