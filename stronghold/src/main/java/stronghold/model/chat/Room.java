package stronghold.model.chat;

import java.util.ArrayList;

public class Room {
	private String name;
	private ArrayList<String> members;

	public Room(String name, ArrayList<String> members) {
		this.name = name;
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getMembers() {
		return members;
	}

	public boolean isMember(String username) {
		return members.contains(username);
	}
}
