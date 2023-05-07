package stronghold.model;

import java.util.ArrayList;

import stronghold.model.buildings.Building;
import stronghold.model.buildings.Stockpile;
import stronghold.model.people.Person;

public class Government {
	private final User user;
	private final ArrayList<Building> buildings = new ArrayList<>();
	private int popularity = 50;
	private int fearFactor = 0;
	private int foodRate = 0;
	private int taxRate = 0;
	private int religionRate = 0;
	private final ArrayList<Person> people = new ArrayList<>();

	public Government(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public ArrayList<Building> getBuildings() {
		return buildings;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	public int getFearFactor() {
		return fearFactor;
	}

	public void setFearFactor(int fearFactor) {
		this.fearFactor = fearFactor;
	}

	public int getFoodRate() {
		return foodRate;
	}

	public void setFoodRate(int foodRate) {
		this.foodRate = foodRate;
	}

	public int getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}

	public int getReligionRate() {
		return religionRate;
	}

	public void setReligionRate(int religionRate) {
		this.religionRate = religionRate;
	}

	public ArrayList<Person> getPeople() {
		return people;
	}

	public void updatePopularity() {
		popularity += fearFactor + foodRate + taxRate + religionRate;
	}

	public int getResourceCount(ResourceType resourceType) {
		// TODO: to be implemented
		return -1;
	}

	public int increaseResource(ResourceType resourceType, int count) {
		// TODO: to be implemented
		return -1;
	}

	public int decreaseResource(ResourceType resourceType, int count) {
		// TODO: to be implemented
		return -1;
	}

	public boolean isThereStockpileToResource(ResourceType resourceType) {
		for(Building building : buildings) {
			if(building instanceof Stockpile) {
				if(((Stockpile)building).getResources().containsKey(resourceType))
					return true;
			}
		}
		return false;
	}

	public int getCapacityOfResourceType(ResourceType resourceType) {
		int capacity = 0;
		for (Building building : buildings) {
			if (building instanceof Stockpile) {
				Stockpile stockpile = (Stockpile) building;
				if (stockpile.getResources().containsKey(resourceType)) {
					capacity += (stockpile.getCapacity() - stockpile.getSumOfResource());
				}
			}
		}
		return capacity;
	}
}
