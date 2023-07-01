package stronghold.network;

public enum PacketType {
	SIGNUP,
	LOGIN,
	LOGOUT,
	GET_USER,
	UPDATE_USER,

	CREATE_GAME,
	JOIN_GAME,
	SYNC_GET_MAP,
	SYNC_PUT_MAP,

	RESPONSE,
	ERROR,
}
