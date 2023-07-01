package stronghold.network;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Packet {
	private final PacketType type;
	private final ArrayList<String> dataList = new ArrayList<>();
	private final String jwt;

	public Packet(PacketType type, String jwt) {
		this.type = type;
		this.jwt = jwt;
	}

	public Packet(PacketType type, String jwt, String data) {
		this(type, jwt);
		this.dataList.add(data);
	}

	public Packet(PacketType type, String jwt, List<String> dataList) {
		this(type, jwt);
		this.dataList.addAll(dataList);
	}

	public Packet(PacketType type, String jwt, String[] dataList) {
		this(type, jwt);
		for (String data : dataList)
			this.dataList.add(data);
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
