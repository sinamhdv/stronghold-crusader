package stronghold.model.buildings;

import java.util.HashMap;

import stronghold.model.ResourceType;

public class Stockpile {
	HashMap <ResourceType, Integer> resources;
	int capacity;
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
