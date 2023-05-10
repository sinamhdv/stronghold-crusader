package stronghold.utils;

import com.google.gson.Gson;

import stronghold.model.people.Person;

public class ConfigManager {
	private static final String CAPTCHA_ASCII_ART_FILENAME = "stronghold/src/main/config/captcha-ascii-art.json";
	private static final String PEOPLE_CONFIG_FILENAME = "stronghold/src/main/config/people.json";

	// Captcha
	public static String[][] loadCaptchaAsciiArt() {
		String jsonData = DatabaseManager.readAllFromFile(CAPTCHA_ASCII_ART_FILENAME);
		return new Gson().fromJson(jsonData, String[][].class);
	}

	// People
	public static Person[] getPeopleConfig() {
		String jsonData = DatabaseManager.readAllFromFile(PEOPLE_CONFIG_FILENAME);
		return new Gson().fromJson(jsonData, Person[].class);
	}
	public static void savePeopleConfig(Person[] people) {
		String jsonData = new Gson().toJson(people);
		DatabaseManager.writeToFile(PEOPLE_CONFIG_FILENAME, jsonData);
	}
}
