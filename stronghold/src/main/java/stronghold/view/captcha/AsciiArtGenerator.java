package stronghold.view.captcha;

import java.util.Random;

import stronghold.controller.DatabaseManager;

public class AsciiArtGenerator {

	private static int curentcaptcha = 0;

	private static String[] getAsciiArtOfRandomNumber() {
		String[][] digitAsciiArt = DatabaseManager.loadCaptchaAsciiArt();
		Random random = new Random();
		int number = random.nextInt(9);
		curentcaptcha = curentcaptcha * 10 + number;
		return digitAsciiArt[number];
	}

	private static String[] createCaptchaAsciiArt() {
		String[][] oneDigitOfCaptcha = new String[4][6];
		String[] captcha = new String[6];
		curentcaptcha = 0;
		for (int i = 0; i < 4; i++) {
			oneDigitOfCaptcha[i] = getAsciiArtOfRandomNumber();
		}
		for (int i = 0; i < captcha.length; i++) {
			captcha[i] += oneDigitOfCaptcha[0][i] + oneDigitOfCaptcha[1][i] + oneDigitOfCaptcha[2][i]
					+ oneDigitOfCaptcha[3][i];
		}
		return captcha;
	}

	private static String[] makingNoise(String[] captcha) {
		Random random = new Random();
		for (int i = 0; i < captcha.length; i++) {
			for (int j = 4; j < captcha[i].length(); j ++) {
				if(random.nextInt(10) == 1)
				captcha[i].replace(captcha[i].charAt(j), '*');
			}
		}
		return captcha;
	}

	public static int getCurentcaptcha() {
		return curentcaptcha;
	}

	public static String[] genarateCaptcha() {
		String[] captcha = makingNoise(createCaptchaAsciiArt());
		return captcha;
	}
}
