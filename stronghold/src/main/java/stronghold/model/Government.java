package stronghold.model;

import java.util.ArrayList;

import stronghold.model.buildings.Building;
import stronghold.model.buildings.Stockpile;
import stronghold.model.map.Map;
import stronghold.model.map.MapTile;
import stronghold.model.people.Person;

public class Government {
	private final User user;
	private final ArrayList<Building> buildings = new ArrayList<>();
	private final int index;
	private int popularity = 50;
	private int fearFactor = 0;
	private int foodRate = 0;
	private int taxRate = 0;
	private int religionRate = 0;
	private int gold = 0;
	private int wineUsageCycleTurns = 0;
	private final ArrayList<Person> people = new ArrayList<>();

	public Government(User user, int index, Map map) {
		this.user = user;
		this.index = index;

		// fill buildings and people arrays according to the map
		MapTile[][] grid = map.getGrid();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].getBuilding() != null && grid[i][j].getBuilding().getOwnerIndex() == index &&
					!this.buildings.contains(grid[i][j].getBuilding()))
					this.buildings.add(grid[i][j].getBuilding());
				for (Person person : grid[i][j].getPeople())
					if (person.getOwnerIndex() == index)
						this.people.add(person);
			}
		}
	}

	public int getIndex() {
		return index;
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

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getWineUsageCycleTurns() {
		return wineUsageCycleTurns;
	}
	
	public void setWineUsageCycleTurns(int wineUsageCycleTurns) {
		this.wineUsageCycleTurns = wineUsageCycleTurns;
	}

	public void addBuilding(Building building) {
		buildings.add(building);
	}
	public void addPerson(Person person) {
		people.add(person);
	}

	public void useWine() {
		if (wineUsageCycleTurns == 0 || StrongHold.getCurrentGame().getPassedTurns() % wineUsageCycleTurns != 0)
			return;
		if (getResourceCount(ResourceType.WINE) == 0)
			return;
		decreaseResource(ResourceType.WINE, 1);
		popularity++;
	}

	public int getResourceCount(ResourceType resourceType) {
		if (resourceType == ResourceType.GOLD)
			return this.gold;
		int resourceCount = 0;
		for (Building building : this.buildings) {
			if (building instanceof Stockpile) {
				Stockpile stockpile = (Stockpile) building;
				if (stockpile.getResources().containsKey(resourceType)) {
					resourceCount += stockpile.getResources().get(resourceType);
				}
			}
		}
		return resourceCount;
	}

	public int increaseResource(ResourceType resourceType, int count) {
		if (resourceType == ResourceType.GOLD) {
			this.gold += count;
			return count;
		}
		int canIncrease = 0;
		for (Building building : this.buildings) {
			if (building instanceof Stockpile) {
				Stockpile stockpile = (Stockpile) building;
				if (stockpile.getResources().containsKey(resourceType)) {
					int resourceCount = stockpile.getResources().get(resourceType);
					while (stockpile.getCapacity() > stockpile.getSumOfResources() && canIncrease < count) {
						stockpile.getResources().put(resourceType, ++resourceCount);
						canIncrease++;
					}
				}
			}
		}
		return canIncrease;
	}

	public int decreaseResource(ResourceType resourceType, int count) {
		if (resourceType == ResourceType.GOLD) {
			if (count > this.gold) count = this.gold;
			this.gold -= count;
			return count;
		}
		int canDecrease = 0;
		for (Building building : this.buildings) {
			if (building instanceof Stockpile) {
				Stockpile stockpile = (Stockpile) building;
				if (stockpile.getResources().containsKey(resourceType)) {
					int resourceCount = stockpile.getResources().get(resourceType);
					while (resourceCount > 0 && canDecrease < count) {
						stockpile.getResources().put(resourceType, --resourceCount);
						canDecrease++;
					}
				}
			}
		}
		return canDecrease;
	}

	public int getCapacityOfResourceType(ResourceType resourceType) {
		int capacity = 0;
		for (Building building : buildings) {
			if (building instanceof Stockpile) {
				Stockpile stockpile = (Stockpile) building;
				if (stockpile.getResources().containsKey(resourceType)) {
					capacity += (stockpile.getCapacity() - stockpile.getSumOfResources());
				}
			}
		}
		return capacity;
	}

	public int getFoodVariety() {
		ResourceType[] food = new ResourceType[] { ResourceType.APPLE, ResourceType.CHEESE, ResourceType.MEAT,
				ResourceType.BREAD };
		int foodVariety = 0;
		for (int i = 0; i < 4; i++) {
			if (getResourceCount(food[i]) > 0) {
				foodVariety++;
			}
		}
		return foodVariety;
		
	}
}
