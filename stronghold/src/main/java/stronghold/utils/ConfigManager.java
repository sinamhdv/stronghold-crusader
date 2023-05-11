package stronghold.utils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import stronghold.model.ResourceType;
import stronghold.model.buildings.Building;
import stronghold.model.people.Person;

public class ConfigManager {
	private static final String CAPTCHA_ASCII_ART_FILENAME = "stronghold/src/main/config/captcha-ascii-art.json";
	private static final String PEOPLE_CONFIG_FILENAME = "stronghold/src/main/config/people.json";
	private static final String RESOURCES_CONFIG_FILENAME = "stronghold/src/main/config/required-resources.json";
	private static final String BUILDINGS_CONFIG_PATH = "stronghold/src/main/config/buildings/";

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

	// Buildings
	public static String[] getBuildingFilenames(String buildingName) {
		return new String[] {
			BUILDINGS_CONFIG_PATH + buildingName + ".json",
			BUILDINGS_CONFIG_PATH + buildingName + ".data",
		};
	}
	public static Building getBuildingFromConfig(String buildingName) {
		String[] filenames = getBuildingFilenames(buildingName);
		File file = new File(filenames[0]);
		if (!file.exists()) return null;
		Building classSpecifier = (Building)DatabaseManager.deserializeObject(filenames[1]);
		String jsonData = DatabaseManager.readAllFromFile(filenames[0]);
		return new Gson().fromJson(jsonData, classSpecifier.getClass());
	}
	public static void saveBuildingConfig(Building building) {
		String[] filenames = getBuildingFilenames(building.getName());
		String jsonData = new Gson().toJson(building);
		DatabaseManager.writeToFile(filenames[0], jsonData);
		DatabaseManager.serializeObject(filenames[1], building);
	}

	// Resources
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
