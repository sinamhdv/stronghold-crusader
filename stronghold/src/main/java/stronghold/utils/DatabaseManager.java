package stronghold.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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

	// File Operations
	static void writeToFile(String filename, String content) {
		File file = new File(filename);
		try {
			FileUtils.writeStringToFile(file, content, Charset.forName("UTF-8"));
		} catch (IOException e) {
			System.err.println("FATAL: writing to file " + filename + " failed");
			e.printStackTrace();
		}
	}
	static String readAllFromFile(String filename) {
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
		User[] usersArray = (jsonData == null ? new User[0] : new Gson().fromJson(jsonData, User[].class));
		StrongHold.setUsers(new ArrayList<>(Arrays.asList(usersArray)));
	}
	public static void updateUser(User user) {
		saveUsers();
	}
	private static void saveUsers() {
		String jsonData = new Gson().toJson(StrongHold.getUsers());
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
		return MAP_FILES_PATH + mapName + ".data";
	}
	private static String convertFilenameToMapName(String filename) {
		return filename.substring(0, filename.length() - 5);
	}
	public static void saveMap(Map map) {
		try {
			OutputStream file = new FileOutputStream(getMapFilename(map.getName()));
			ObjectOutputStream serializer = new ObjectOutputStream(file);
			serializer.writeObject(map);
			serializer.close();
			file.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("FATAL: Exception while saving map");
			System.exit(1);
		}
	}
	public static boolean mapExists(String mapName) {
		File file = new File(getMapFilename(mapName));
		return file.exists();
	}
	public static Map loadMapByName(String mapName) {
		try {
			InputStream file = new FileInputStream(getMapFilename(mapName));
			ObjectInputStream deserializer = new ObjectInputStream(file);
			Map map = (Map)deserializer.readObject();
			deserializer.close();
			file.close();
			return map;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("FATAL: Exception while loading map");
			System.exit(1);
		}
		return null;
	}
	public static void deleteMap(String mapName) {
		File file = new File(getMapFilename(mapName));
		file.delete();
	}
	public static String[] getAllMapNames() {
		File mapsDirectory = new File(MAP_FILES_PATH);
		File[] files = mapsDirectory.listFiles();
		String[] result = new String[files.length];
		for (int i = 0; i < files.length; i++)
			result[i] = convertFilenameToMapName(files[i].getName());
		return result;
	}
}
