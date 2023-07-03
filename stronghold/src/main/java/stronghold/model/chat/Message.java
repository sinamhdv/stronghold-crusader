package stronghold.model.chat;

import java.text.SimpleDateFormat;
import java.util.Date;

import stronghold.model.StrongHold;

public class Message {
	private int id;
	private String sender;
	private String receiver;
	private String timestamp;
	private String senderAvatarURL;
	private boolean seen = false;
	private String content;
	private String reactionEmojiURL;

	public Message(int id, String receiver, String content) {
		this.id = id;
		this.sender = StrongHold.getCurrentUser().getUserName();
		this.receiver = receiver;
		this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		this.senderAvatarURL = StrongHold.getCurrentUser().getAvatarURL();
		this.content = content;
	}

	public int getId() {
		return id;
	}
	public String getSender() {
		return sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public String getSenderAvatarURL() {
		return senderAvatarURL;
	}
	public boolean isSeen() {
		return seen;
	}
	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReactionEmojiURL() {
		return reactionEmojiURL;
	}
	public void setReactionEmojiURL(String reactionEmojiURL) {
		this.reactionEmojiURL = reactionEmojiURL;
	}

	// public boolean isMine() {
	// 	if (receiver.charAt(0) == '@' &&
	// 		ChatMenuController.getChatData().getRoomByName(receiver).isMember(StrongHold.getCurrentUser().getUserName()))
	// 			return true;
	// 	return (receiver.equals(StrongHold.getCurrentUser().getUserName()) ||
	// 			sender.equals(StrongHold.getCurrentUser().getUserName()));
	// }

	public boolean isInChat(String chatName, String username) {
		if (chatName.charAt(0) == '@')
			return receiver.equals(chatName);
		return (receiver.equals(username) && sender.equals(chatName)) ||
				(receiver.equals(chatName) && sender.equals(username));
	}
}
