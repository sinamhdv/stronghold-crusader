package stronghold.model.buildings;

import java.util.HashMap;

import stronghold.model.ResourceType;

public class Stockpile extends Building {
	private HashMap<ResourceType, Integer> resources;
	private int capacity;

	public Stockpile(int maxHp, String name, int neededWorkers, int width, int height, int verticalHeight,
			boolean isSelectable, int x, int y, int residentsCapacity, int ownerIndex,
			HashMap<ResourceType, Integer> resources, int capacity) {
		super(maxHp, name, neededWorkers, width, height, verticalHeight, isSelectable, x, y, residentsCapacity,
				ownerIndex);
		this.resources = resources;
		this.capacity = capacity;
	}

	private Stockpile(Stockpile model, int x, int y, int ownerIndex) {
		super(model, x, y, ownerIndex);
		this.resources = model.resources;
		this.capacity = model.capacity;
	}

	@Override
	public Building generateCopy(int x, int y, int ownerIndex) {
		return new Stockpile(this, x, y, ownerIndex);
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

	public int getSumOfResources() {
		int sumOfResources = 0;
		for(ResourceType resource : resources.keySet()) {
				sumOfResources += resources.get(resource);
		}
		return sumOfResources;
	}
}
