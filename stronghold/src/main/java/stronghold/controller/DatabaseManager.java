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
import stronghold.model.map.MapTile;

public class DatabaseManager {
	
	private static final String USERS_DATABASE_FILENAME = "stronghold/src/main/database/users.json";
	private static final String STAY_LOGGED_IN_FILENAME = "stronghold/src/main/database/stay-logged-in.txt";

	private static void writeToFile(String filename, String content) {
		File file = new File(filename);
		try {
			FileUtils.writeStringToFile(file, content, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String readAllFromFile(String filename) {
		File file = new File(filename);
		if (!file.exists()) return null;
		try {
			return FileUtils.readFileToString(file, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

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

	public static void saveStayLoggedIn(User user) {

	}

	public static void saveMap(MapTile[][] map) {
		// TODO: implement this
	}

	public static MapTile[][] loadMapByName(String mapName) {
		// TODO: implement this
		return null;
	}
}
