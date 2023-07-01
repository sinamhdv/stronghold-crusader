package stronghold.network;

import com.google.gson.Gson;

import stronghold.utils.Cryptography;

public class Packet {
	private final PacketType type;
	private final String data;
	private final String jwt;
	
	public Packet(PacketType type, String data, String username) {
		this.type = type;
		this.data = data;
		this.jwt = Cryptography.generateJWT(username);
	}

	public PacketType getType() {
		return type;
	}

	public String getData() {
		return data;
	}

	public String getJwt() {
		return jwt;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

	public static Packet fromJson(String json) {
		return new Gson().fromJson(json, Packet.class);
	}
}
