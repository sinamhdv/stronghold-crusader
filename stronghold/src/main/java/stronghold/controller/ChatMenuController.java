package stronghold.controller;

import java.util.ArrayList;
import java.util.Arrays;

import stronghold.client.SendRequests;
import stronghold.controller.messages.ChatMenuMessage;
import stronghold.model.chat.ChatData;
import stronghold.model.chat.Message;
import stronghold.model.chat.Room;

public class ChatMenuController {
	private static ChatData chatData;

	public static ChatData getChatData() {
		return chatData;
	}

	public static void setChatData(ChatData chatData) {
		ChatMenuController.chatData = chatData;
	}

	private static int getNextMessageId() {
		int result = 0;
		for (Message message : chatData.getMessages())
			result = Math.max(result, message.getId());
		return result + 1;
	}

	public static void sendMessage(String receiver, String content) {
		Message message = new Message(getNextMessageId(), receiver, content);
		chatData.getMessages().add(message);
		SendRequests.sendChatData(chatData);
	}

	public static void reactMessage(int id, int emojiIndex) {
		Message message = chatData.getMessageById(id);
		message.setReactionEmojiIndex(emojiIndex);
		SendRequests.sendChatData(chatData);
	}

	public static void deleteMessage(int id) {
		Message message = chatData.getMessageById(id);
		chatData.getMessages().remove(message);
		SendRequests.sendChatData(chatData);
	}

	public static void editMessage(int id, String content) {
		Message message = chatData.getMessageById(id);
		message.setContent(content);
		SendRequests.sendChatData(chatData);
	}

	public static ChatMenuMessage createRoom(String name, String[] members) {
		if (name.length() == 0)
			return ChatMenuMessage.EMPTY_NAME;
		name = "@" + name;
		if (chatData.getRoomByName(name) != null)
			return ChatMenuMessage.ROOM_ALREADY_EXISTS;
		for (String member : members)
			if (!chatData.getUsers().contains(member))
				return ChatMenuMessage.USERNAME_NOT_FOUND;
		chatData.getRooms().add(new Room(name, new ArrayList<>(Arrays.asList(members))));
		SendRequests.sendChatData(chatData);
		return ChatMenuMessage.SUCCESS;
	}

	public static void setOnline(String username) {
		chatData.getRoomByName("@public").getMembers().add(username);
		if (!chatData.getUsers().contains(username))
			chatData.getUsers().add(username);
	}

	public static void setOffline(String username) {
		chatData.getRoomByName("@public").getMembers().remove(username);
	}
}
