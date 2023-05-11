package stronghold.utils;

import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import stronghold.model.ResourceType;
import stronghold.model.people.Person;

public class ConfigManager {
	private static final String CAPTCHA_ASCII_ART_FILENAME = "stronghold/src/main/config/captcha-ascii-art.json";
	private static final String PEOPLE_CONFIG_FILENAME = "stronghold/src/main/config/people.json";
	private static final String RESOURCES_CONFIG_FILENAME = "stronghold/src/main/config/required-resources.json";

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

	public static HashMap<ResourceType, Integer> getRequiredResources(String objectName) {
		String jsonData = DatabaseManager.readAllFromFile(RESOURCES_CONFIG_FILENAME);
		Type genericType = new TypeToken<HashMap<String, HashMap<String, Integer>>>(){}.getType();
		HashMap<String, HashMap<String, Integer>> allData = new Gson().fromJson(jsonData, genericType);
		HashMap<String, Integer> name2amount = allData.get(objectName);
		if (name2amount == null) return null;
		HashMap<ResourceType, Integer> result = new HashMap<>();
		for (String resourceName : name2amount.keySet())
			result.put(ResourceType.valueOf(resourceName), name2amount.get(resourceName));
		return result;
	}
}
