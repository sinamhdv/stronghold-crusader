package stronghold.controller;

import static java.lang.Thread.sleep;

import stronghold.controller.messages.LoginMenuControllerMessage;
import stronghold.model.StrongHold;
import stronghold.model.User;

public class LoginMenuController {
    //TODO: 5 second punishments need to be implemented.
    static int timer = 1;

    public static void LoginPunishment() throws InterruptedException {
        timer++;
        System.out.println("You have to wait for " + timer * 5 + " seconds before you can login again!");
        sleep(5000L * timer);
        System.out.println("You're free!");
    }

    public static LoginMenuControllerMessage login(String username, String password, String s) {
        User user = StrongHold.getUserByName(username);
        if (user == null) {
            return LoginMenuControllerMessage.USERNAME_ERROR;
        } else if (!password.equals(user.getPassword())) {
            return LoginMenuControllerMessage.PASSWORD_ERROR;
        } else {
            StrongHold.setCurentUser(user);
            return LoginMenuControllerMessage.SUCCESSFUL;
//            MainMenu.run();
        }
    }

    // public static LoginMenuControllerMessage forgotPassword(String username) {
    //     if (StrongHold.getUserByName(username) == null) {
    //         System.out.println(LoginMenuControllerMessage.USERNAME_NOT_FOUND);
    //     } else {
    //         User user = StrongHold.getUserByName(username);


    //     }
    // }
}