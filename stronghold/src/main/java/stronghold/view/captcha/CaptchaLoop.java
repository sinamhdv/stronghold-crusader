package stronghold.view.captcha;

import stronghold.utils.FormatValidation;
import stronghold.view.MainMenu;

public class CaptchaLoop {
	public static boolean captchaManager() {
		while (true) {
			String[] captcha = AsciiArtGenerator.genarateCaptcha();
			for (int i = 0; i < captcha.length; i++) {
				System.out.println(captcha[i]);
			}
			String input = MainMenu.getScanner().nextLine();
			if (!FormatValidation.isNumber(input)) {
				System.out.println("Error: please enter a number");
				continue;
			}
			int number = Integer.parseInt(input);
			if (number == AsciiArtGenerator.getCurentcaptcha())
				return true;
		}
	}
}
