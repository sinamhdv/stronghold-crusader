package stronghold.model.buildings;

import java.util.HashMap;

import stronghold.model.ResourceType;

public class Stockpile extends Building {
	HashMap<ResourceType, Integer> resources;
	int capacity;

	public Stockpile(int maxHp, int x, int y, int ownerIndex, String name, int neededWorkers, int capacity,
			HashMap<ResourceType, Integer> resources, int terunOfBuild) {
		super(maxHp, x, y, ownerIndex, name, neededWorkers, terunOfBuild);
		this.resources = resources;
		this.capacity = capacity;
	}

	public HashMap<ResourceType, Integer> getResources() {
		return resources;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setResources(HashMap<ResourceType, Integer> resources) {
		this.resources = resources;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}
