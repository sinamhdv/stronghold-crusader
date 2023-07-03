package stronghold.controller;

import stronghold.client.SendRequests;
import stronghold.model.chat.ChatData;
import stronghold.model.chat.Message;

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
}
