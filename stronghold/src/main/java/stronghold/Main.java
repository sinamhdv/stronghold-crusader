package stronghold;

import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import stronghold.client.ClientMain;
import stronghold.server.ServerMain;
import stronghold.utils.DatabaseManager;
import stronghold.view.MapManagementMenu;

public class Main extends Application {

	public static void main(String[] args) {
		DatabaseManager.loadUsers();
		GenerateConfig.run();
		launch(args);
		// MapManagementMenu.run(); Platform.exit();
	}

	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("1)server / 2)client");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.nextLine();
		if (choice.equals("1"))
			ServerMain.run();
		else
			new ClientMain().start(stage);
		scanner.close();
	}
}
