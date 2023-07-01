package stronghold.network;

import java.util.ArrayList;

import com.google.gson.Gson;

import stronghold.utils.Cryptography;

public class Packet {
	private final PacketType type;
	private final ArrayList<String> dataList = new ArrayList<>();
	private final String jwt;
	
	public Packet(PacketType type, String data, String username) {
		this.type = type;
		this.dataList.add(data);
		this.jwt = Cryptography.generateJWT(username);
	}

	public Packet(PacketType type, String username) {
		this.type = type;
		this.jwt = Cryptography.generateJWT(username);
	}

	public void addData(String data) {
		dataList.add(data);
	}

	public PacketType getType() {
		return type;
	}

	public ArrayList<String> getDataList() {
		return dataList;
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
