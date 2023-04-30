package stronghold.model.map;

import java.util.ArrayList;

import stronghold.model.buildings.Building;
import stronghold.model.environment.EnvironmentItem;
import stronghold.model.people.Person;

public class MapTile {
	private GroundType groundType;
	private final ArrayList<Person> people = new ArrayList<>();
	private Building building = null;
	private EnvironmentItem environmentItem;

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
	public EnvironmentItem getEnvironmentItem() {
		return environmentItem;
	}
	public void setEnvironmentItem(EnvironmentItem environmentItem) {
		this.environmentItem = environmentItem;
	}
}
