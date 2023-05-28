package stronghold.utils;

import java.io.File;

public class Captcha {
	public static void generateCaptcha() throws Exception {
		String number = getRandom4DigitNumber();
		String[] command = {
			"python3",
			"main.py",
			number
		};
		File captchaDirectory = new File(Captcha.class.getResource("/captcha").toURI());
		Runtime.getRuntime().exec(command, null, captchaDirectory).waitFor();
	}

	private static String getRandom4DigitNumber() {
		String result = Integer.valueOf(Miscellaneous.RANDOM_GENERATOR.nextInt(10000)).toString();
		while (result.length() < 4) result = "0" + result;
		return result;
	}
}
