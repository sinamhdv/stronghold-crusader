package stronghold.model.map;

import java.util.ArrayList;

import stronghold.model.buildings.Building;
import stronghold.model.people.Person;

public class MapTile {
	private GroundType groundType;
	private ArrayList<Person> people = new ArrayList<>();
	private Building building = null;

	public MapTile(GroundType groundType) {
		this.groundType = groundType;
	}

	public GroundType getGroundType() {
		return groundType;
	}
	public Building getBuilding() {
		return building;
	}
	public ArrayList<Person> getPeople() {
		return people;
	}
	public void setBuilding(Building building) {
		this.building = building;
	}
	public void setGroundType(GroundType groundType) {
		this.groundType = groundType;
	}	
}
