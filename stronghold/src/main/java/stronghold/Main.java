package stronghold;

import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import stronghold.client.ClientMain;
import stronghold.server.ServerMain;
import stronghold.view.MapManagementMenu;

public class Main extends Application {

	private static boolean serverMode = false;

	public static boolean isServerMode() {
		return serverMode;
	}

	public static void main(String[] args) {
		GenerateConfig.run();
		launch(args);
		// MapManagementMenu.run(); Platform.exit();	// uncomment to enable CLI for map designer
	}

	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("1)server / 2)client");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.nextLine();
		if (choice.equals("1")) {
			serverMode = true;
			ServerMain.run();
		}
		else
			new ClientMain().start(stage);
		scanner.close();
	}
}
