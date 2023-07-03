package stronghold.model.chat;

import java.util.ArrayList;

public class ChatData {
	private ArrayList<Message> messages;
	private ArrayList<Room> rooms;

	public ChatData() {
		messages = new ArrayList<>();
		rooms = new ArrayList<>();
		rooms.add(new Room("@public", new ArrayList<>()));
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}
	public ArrayList<Room> getRooms() {
		return rooms;
	}
}
