package stronghold.model.map;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import stronghold.model.buildings.Building;
import stronghold.model.environment.EnvironmentItem;
import stronghold.model.people.Person;

public class MapTile implements Serializable {
	private GroundType groundType = GroundType.NORMAL;
	private final ArrayList<Person> people = new ArrayList<>();
	private Building building = null;
	private EnvironmentItem environmentItem = null;

	public MapTile(GroundType groundType) {
		this.groundType = groundType;
	}
	public MapTile() {
		
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
	public void addPerson(Person person) {
		people.add(person);
	}
	public Color getColor() {
		if ((building == null || !building.isVisible()) && environmentItem == null) return groundType.getColor();
		return Color.BLACK;
	}

	public boolean hasPeople() {
		return !people.isEmpty();
	}
	public boolean hasObstacle() {
		return (building != null || environmentItem != null);
	}

	public boolean isPassable() {
		return (
			this.environmentItem == null &&
			(this.building == null || this.building.getVerticalHeight() <= 5) &&
			this.groundType.isPassable()
		);
	}

	public int getHeight() {
		return (building == null ? 0 : building.getVerticalHeight());
	}

	public boolean hasVisiblePeople() {
		for (Person person : people)
			if (person.isVisible())
				return true;
		return false;
	}
}
