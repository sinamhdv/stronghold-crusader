package stronghold.controller;

import java.io.File;
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

	public static void loadUsers() {
		File file = new File(USERS_DATABASE_FILENAME);
		if (!file.exists()) {
			StrongHold.setUsers(new ArrayList<>());
			return;
		}
		try {
			String jsonData = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
			Gson gson = new Gson();
			User[] usersArray = gson.fromJson(jsonData, User[].class);
			StrongHold.setUsers(new ArrayList<>(Arrays.asList(usersArray)));
		}
		catch (Exception e) {
			StrongHold.setUsers(new ArrayList<>());
		}
	}

	public static void updateUser(User user) {
		saveUsers();
	}

	private static void saveUsers() {
		Gson gson = new Gson();
		String jsonData = gson.toJson(StrongHold.getUsers());
		File file = new File(USERS_DATABASE_FILENAME);
		try {
			FileUtils.writeStringToFile(file, jsonData, Charset.forName("UTF-8"));
		}
		catch (Exception e) {
			System.out.println("Error while saving the database");
		}
	}

	public static void saveMap(MapTile[][] map) {
		// TODO: implement this
	}

	public static MapTile[][] loadMapByName(String mapName) {
		// TODO: implement this
		return null;
	}
}
