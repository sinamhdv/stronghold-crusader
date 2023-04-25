package stronghold.view;

import stronghold.controller.LoginMenuController;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

import java.util.HashMap;
import java.util.Scanner;

public class LoginMenu {

    public static void run() {
        System.out.println("[LoginMenu]");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            String[] inputTokens = CommandParser.splitTokens(line);
            HashMap<String, String> matcher;
            if ((matcher = CommandParser.getMatcher(inputTokens, Command.LOGIN)) != null) {
                System.out.println("LOGIN => Username: " + matcher.get("username") + " | Password: " + matcher.get("password") + " | --stay-logged-in: " + matcher.get("-"));
                System.out.println(LoginMenuController.login(matcher.get("username"), matcher.get("password"), matcher.get("-")));
            } else if ((matcher = CommandParser.getMatcher(inputTokens, Command.FORGOT_PASSWORD)) != null) {
                System.out.println("FORGOT => Username: " + matcher.get("username"));
                System.out.println(LoginMenuController.forgotPassword(matcher.get("username")));
            } else if ((CommandParser.getMatcher(inputTokens, Command.EXIT)) != null) {
                System.out.println("EXIT!");
                break;
            } else {
                System.out.println("Error: Invalid command");
            }
        }
        scanner.close();
    }
}
// signup -u username123 -p password123 -c password123 –e email@mail.com -n nickname123 -s slogan
// login -u username123 -p password123

