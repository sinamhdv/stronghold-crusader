package stronghold.network;

public enum PacketType {
	SIGNUP,
	LOGIN,
	LOGOUT,
	GET_USER,
	UPDATE_USER,

	CREATE_GAME,
	JOIN_GAME,
	SYNC_MAP,

	START_CHAT,
	END_CHAT,
	UPDATE_CHAT_DATA,

	CONTENT_LENGTH,
	RESPONSE,
	ERROR,
}
