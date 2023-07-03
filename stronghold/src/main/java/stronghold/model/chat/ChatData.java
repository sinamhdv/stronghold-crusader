package stronghold.model.chat;

import java.util.ArrayList;

import stronghold.model.StrongHold;
import stronghold.model.User;

public class ChatData {
	private ArrayList<Message> messages;
	private ArrayList<Room> rooms;
	private ArrayList<String> users;

	public ChatData() {
		messages = new ArrayList<>();
		users = new ArrayList<>();
		for (User user : StrongHold.getUsers())
			users.add(user.getUserName());
		rooms = new ArrayList<>();
		rooms.add(new Room("@public", new ArrayList<>()));
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public ArrayList<String> getUsers() {
		return users;
	}

	public Room getRoomByName(String name) {
		for (Room room : rooms)
			if (room.getName().equals(name))
				return room;
		return null;
	}

	public boolean isOnline(String username) {
		return getRoomByName("@public").isMember(username);
	}

	public Message getMessageById(int id) {
		for (Message message : messages)
			if (message.getId() == id)
				return message;
		return null;
	}
}
