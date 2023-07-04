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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import stronghold.Main;
import stronghold.client.SendRequests;
import stronghold.controller.ChatMenuController;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.model.chat.ChatData;
import stronghold.model.map.Map;

public class DatabaseManager {
	
	private static final String USERS_DATABASE_FILENAME = "stronghold/src/main/database/users.json";
	private static final String CHAT_DATA_FILENAME = "stronghold/src/main/database/chatdata.json";
	private static final String STAY_LOGGED_IN_FILENAME = "stronghold/src/main/database/stay-logged-in.txt";
	private static final String MAP_FILES_PATH = "stronghold/src/main/database/maps/";
	private static final String SQLITE_DATABASE_PATH = "stronghold/src/main/database/users.db";

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
	static void serializeObject(String filename, Object object) {
		try {
			OutputStream file = new FileOutputStream(filename);
			ObjectOutputStream serializer = new ObjectOutputStream(file);
			serializer.writeObject(object);
			serializer.close();
			file.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("FATAL: Exception while serializing");
			System.exit(1);
		}
	}
	static Object deserializeObject(String filename) {
		try {
			InputStream file = new FileInputStream(filename);
			ObjectInputStream deserializer = new ObjectInputStream(file);
			Object object = deserializer.readObject();
			deserializer.close();
			file.close();
			return object;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("FATAL: Exception while deserializing");
			System.exit(1);
		}
		return null;
	}

	// User Management
	private static Connection databaseConnection;
	private static void setupDatabase() {
		try {
			databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + SQLITE_DATABASE_PATH);
			Statement statement = databaseConnection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS users(username text PRIMARY KEY, json text);");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	public static void loadUsers() {
		try {
			setupDatabase();
			Statement statement = databaseConnection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM users");
			ArrayList<User> users = new ArrayList<>();
			while (result.next())
				users.add(new Gson().fromJson(result.getString("json"), User.class));
			StrongHold.setUsers(users);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// String jsonData = readAllFromFile(USERS_DATABASE_FILENAME);
		// User[] usersArray = (jsonData == null ? new User[0] : new Gson().fromJson(jsonData, User[].class));
		// StrongHold.setUsers(new ArrayList<>(Arrays.asList(usersArray)));
	}
	public static synchronized void updateUser(User user) {
		if (Main.isServerMode()) {
			// saveUsers();
			try {
				PreparedStatement statement = databaseConnection.prepareStatement(
					"SELECT * FROM users WHERE username = ?");
				statement.setString(1, user.getUserName());
				if (!statement.executeQuery().next()) {
					PreparedStatement insertStatement = databaseConnection.prepareStatement(
						"INSERT INTO users(username, json) VALUES(?, ?)");
					insertStatement.setString(1, user.getUserName());
					insertStatement.setString(2, new Gson().toJson(user));
					insertStatement.executeUpdate();
				}
				else {
					PreparedStatement updateStatement = databaseConnection.prepareStatement(
						"UPDATE users SET json = ? WHERE username = ?");
					updateStatement.setString(1, new Gson().toJson(user));
					updateStatement.setString(2, user.getUserName());
					updateStatement.executeUpdate();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		else
			SendRequests.requestUpdateUser();
	}
	private static synchronized void saveUsers() {
		// String jsonData = new Gson().toJson(StrongHold.getUsers());
		// writeToFile(USERS_DATABASE_FILENAME, jsonData);
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
		serializeObject(getMapFilename(map.getName()), map);
	}
	public static boolean mapExists(String mapName) {
		File file = new File(getMapFilename(mapName));
		return file.exists();
	}
	public static Map loadMapByName(String mapName) {
		return (Map)deserializeObject(getMapFilename(mapName));
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

	// Chat Data
	public static void loadChatData() {
		String json = readAllFromFile(CHAT_DATA_FILENAME);
		ChatData chatData = (json != null ? new Gson().fromJson(json, ChatData.class) : new ChatData());
		ChatMenuController.setChatData(chatData);
	}
	public static synchronized void saveChatData() {
		String json = new Gson().toJson(ChatMenuController.getChatData());
		writeToFile(CHAT_DATA_FILENAME, json);
	}
}
