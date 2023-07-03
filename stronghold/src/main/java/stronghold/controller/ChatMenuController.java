package stronghold.controller;

import stronghold.model.chat.ChatData;

public class ChatMenuController {
	private static ChatData chatData;

	public static ChatData getChatData() {
		return chatData;
	}

	public static void setChatData(ChatData chatData) {
		ChatMenuController.chatData = chatData;
	}
}
