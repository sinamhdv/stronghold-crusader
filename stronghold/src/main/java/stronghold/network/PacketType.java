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

	CONTENT_LENGTH,
	ACK,

	RESPONSE,
	ERROR,
}
