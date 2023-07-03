package stronghold.model.chat;

import java.util.ArrayList;

public class ChatData {
	private ArrayList<Message> messages;
	private ArrayList<Room> rooms;
	private ArrayList<String> onlineUsers;

	public ArrayList<Message> getMessages() {
		return messages;
	}
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	public ArrayList<String> getOnlineUsers() {
		return onlineUsers;
	}
	public boolean isOnline(String username) {
		return onlineUsers.contains(username);
	}
}
