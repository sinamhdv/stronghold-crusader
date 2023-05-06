package stronghold.view.captcha;

import java.util.Random;

import stronghold.controller.DatabaseManager;

public class AsciiArtGenerator {
	private static String[] getAsciiArtOfRandomNumber() {
		String[][] digitAsciiArt = DatabaseManager.loadCaptchaAsciiArt();
		Random random = new Random();
		int number = random.nextInt(9);
		return digitAsciiArt[number];
	}

	public static String[] createCaptchaAsciiArt() {
		String[][] oneDigitOfCaptcha = new String[4][6];
		String[] captcha = new String[6];
		for (int i = 0; i < 4; i++) {
			oneDigitOfCaptcha[i] = getAsciiArtOfRandomNumber();
		}
		for (int i = 0; i < 5; i++) {
			captcha[i] += oneDigitOfCaptcha[0][i] + oneDigitOfCaptcha[1][i] + oneDigitOfCaptcha[2][i]
					+ oneDigitOfCaptcha[3][i];
		}
		return captcha;
	}

}
