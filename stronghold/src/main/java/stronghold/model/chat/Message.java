package stronghold.model.chat;

public class Message {
	private int id;
	private String sender;
	private String receiver;
	private String timestamp;
	private String senderAvatarURL;
	private boolean seen = false;
	private String content;
	private String reactionEmojiURL;

}
