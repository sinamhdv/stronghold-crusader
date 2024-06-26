package stronghold.model.chat;

import java.text.SimpleDateFormat;
import java.util.Date;

import stronghold.model.StrongHold;
import stronghold.utils.Miscellaneous;

public class Message {
	private int id;
	private String sender;
	private String receiver;
	private String timestamp;
	private int senderAvatarIndex;
	private boolean seen = false;
	private String content;
	private int reactionEmojiIndex = -1;

	public Message(int id, String receiver, String content) {
		this.id = id;
		this.sender = StrongHold.getCurrentUser().getUserName();
		this.receiver = receiver;
		this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		this.senderAvatarIndex = Miscellaneous.AvatarURLToIndex(StrongHold.getCurrentUser().getAvatarURL());
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
	public int getSenderAvatarIndex() {
		return senderAvatarIndex;
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
	public int getReactionEmojiIndex() {
		return reactionEmojiIndex;
	}
	public void setReactionEmojiIndex(int reactionEmojiIndex) {
		this.reactionEmojiIndex = reactionEmojiIndex;
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
