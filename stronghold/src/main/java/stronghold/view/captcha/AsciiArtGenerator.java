package stronghold.view.captcha;

import stronghold.utils.ConfigManager;
import stronghold.utils.Miscellaneous;

public class AsciiArtGenerator {

	private static int currentCaptcha = 0;
	private static final int CAPTCHA_DIGITS = 4;
	private static final int CAPTCHA_DIGIT_HEIGHT = 5;

	private static String[] getAsciiArtOfRandomNumber() {
		String[][] digitAsciiArt = ConfigManager.loadCaptchaAsciiArt();
		int number = Miscellaneous.RANDOM_GENERATOR.nextInt(10);
		currentCaptcha = currentCaptcha * 10 + number;
		return digitAsciiArt[number];
	}

	private static String[] createCaptchaAsciiArt() {
		String[][] oneDigitOfCaptcha = new String[CAPTCHA_DIGITS][];
		String[] captcha = new String[CAPTCHA_DIGIT_HEIGHT];
		currentCaptcha = 0;
		for (int i = 0; i < CAPTCHA_DIGITS; i++) {
			oneDigitOfCaptcha[i] = getAsciiArtOfRandomNumber();
		}
		for (int i = 0; i < captcha.length; i++) {
			captcha[i] = "";
			for (int j = 0; j < CAPTCHA_DIGITS; j++) {
				captcha[i] += oneDigitOfCaptcha[j][i];
			}
		}
		return captcha;
	}

	private static String[] makingNoise(String[] captcha) {
		for (int i = 0; i < captcha.length; i++) {
			for (int j = 0; j < captcha[i].length(); j++) {
				if (Miscellaneous.RANDOM_GENERATOR.nextInt(10) == 1) {
					captcha[i] = captcha[i].substring(0, j) + "*" + captcha[i].substring(j + 1);
				}
			}
		}
		return captcha;
	}

	public static int getCurrentCaptcha() {
		return currentCaptcha;
	}

	public static String[] genarateCaptcha() {
		String[] captcha = makingNoise(createCaptchaAsciiArt());
		// ConvertCaptchaToPng.converter(captcha);
		return captcha;
	}
}
