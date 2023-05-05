package stronghold.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.model.map.Map;

public class DatabaseManager {
	
	private static final String USERS_DATABASE_FILENAME = "stronghold/src/main/database/users.json";
	private static final String STAY_LOGGED_IN_FILENAME = "stronghold/src/main/database/stay-logged-in.txt";
	private static final String MAP_FILES_PATH = "stronghold/src/main/database/maps/";
	private static final String CAPTCHA_ASCII_ART_FILENAME = "stronghold/src/main/config/captcha-ascii-art.json";

	// File Operations
	private static void writeToFile(String filename, String content) {
		File file = new File(filename);
		try {
			FileUtils.writeStringToFile(file, content, Charset.forName("UTF-8"));
		} catch (IOException e) {
			System.err.println("FATAL: writing to file " + filename + " failed");
			e.printStackTrace();
		}
	}
	private static String readAllFromFile(String filename) {
		File file = new File(filename);
		if (!file.exists()) return null;
		try {
			return FileUtils.readFileToString(file, Charset.forName("UTF-8"));
		} catch (IOException e) {
			System.err.println("FATAL: reading from file " + filename + " failed");
			e.printStackTrace();
			return null;
		}
	}

	// User Management
	public static void loadUsers() {
		String jsonData = readAllFromFile(USERS_DATABASE_FILENAME);
		Gson gson = new Gson();
		User[] usersArray = (jsonData == null ? new User[0] : gson.fromJson(jsonData, User[].class));
		StrongHold.setUsers(new ArrayList<>(Arrays.asList(usersArray)));
	}
	public static void updateUser(User user) {
		saveUsers();
	}
	private static void saveUsers() {
		Gson gson = new Gson();
		String jsonData = gson.toJson(StrongHold.getUsers());
		writeToFile(USERS_DATABASE_FILENAME, jsonData);
	}

	// Auto-login
	public static void saveStayLoggedIn(User user) {
		writeToFile(STAY_LOGGED_IN_FILENAME, user.getUserName());
	}
	public static void clearStayLoggedIn(User user) {
		writeToFile(STAY_LOGGED_IN_FILENAME, "");
	}
	public static String getAutoLoginUsername() {
		return readAllFromFile(STAY_LOGGED_IN_FILENAME);
	}

	// Map Management
	private static String getMapFilename(String mapName) {
		return MAP_FILES_PATH + mapName + ".json";
	}
	public static void saveMap(Map map) {
		Gson gson = new Gson();
		String jsonData = gson.toJson(map);
		writeToFile(getMapFilename(map.getName()), jsonData);
	}
	public static boolean mapExists(String mapName) {
		File file = new File(getMapFilename(mapName));
		return file.exists();
	}
	public static Map loadMapByName(String mapName) {
		Gson gson = new Gson();
		String jsonData = readAllFromFile(getMapFilename(mapName));
		return gson.fromJson(jsonData, Map.class);
	}
	public static void deleteMap(String mapName) {
		File file = new File(getMapFilename(mapName));
		file.delete();
	}

	public static String[][] loadCaptchaAsciiArt() {
		Gson gson = new Gson();
		String jsonData = readAllFromFile(CAPTCHA_ASCII_ART_FILENAME);
		return gson.fromJson(jsonData, String[][].class);
	}
}
