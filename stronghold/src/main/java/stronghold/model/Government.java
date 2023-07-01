package stronghold.model;

import java.util.ArrayList;

import stronghold.model.buildings.Barracks;
import stronghold.model.buildings.Building;
import stronghold.model.buildings.DefensiveStructure;
import stronghold.model.buildings.DefensiveStructureType;
import stronghold.model.buildings.ResourceConverterBuilding;
import stronghold.model.buildings.Stockpile;
import stronghold.model.map.Map;
import stronghold.model.map.MapTile;
import stronghold.model.people.Person;
import stronghold.utils.PopularityFormulas;
import stronghold.view.GameMenu;

public class Government {
	private static final int MAX_POPULARITY = 100;
	private static final int MIN_POPULARITY = 0;

	private final User user;
	private final ArrayList<Building> buildings = new ArrayList<>();
	private final int index;
	private int popularity = 50;
	private int fearFactor = 0;
	private int foodRate = 0;
	private int taxRate = 0;
	private int gold = 1000;
	private int wineUsageCycleTurns = 1;
	private final ArrayList<Person> people = new ArrayList<>();
	private boolean hasLost = false;

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

	public ArrayList<Person> getPeople() {
		return people;
	}

	public boolean hasLost() {
		return hasLost;
	}

	private void changePopularity(int delta) {
		popularity += delta;
		if (popularity > MAX_POPULARITY) popularity = MAX_POPULARITY;
		if (popularity < MIN_POPULARITY) popularity = MIN_POPULARITY;
	}

	public int getReligionPopularityInfluence() {
		int sum = 0;
		for (Building building : buildings)
			if (building instanceof Barracks)
				sum += ((Barracks)building).getPopularityBoost();
		return sum;
	}

	public int getFoodPopularityInfluence() {
		return PopularityFormulas.foodRate2Popularity(foodRate) + Math.max(getFoodVariety() - 1, 0);
	}

	private void updatePopularity() {
		changePopularity(
			getFoodPopularityInfluence() +
			PopularityFormulas.taxRate2Popularity(taxRate) +
			getReligionPopularityInfluence() +
			fearFactor
		);
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
		if (this == StrongHold.getCurrentGame().getCurrentPlayer())
			GameMenu.getInstance().updateToolBarReport();
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

	public void lose() {
		ArrayList<Person> peopleClone = new ArrayList<>(people);
		ArrayList<Building> buildingsClone = new ArrayList<>(buildings);
		for (Person person : peopleClone) person.die();
		for (Building building : buildingsClone) building.destroy();
		hasLost = true;
	}

	private void useWine() {
		if (wineUsageCycleTurns == 0 || StrongHold.getCurrentGame().getPassedTurns() % wineUsageCycleTurns != 0)
			return;
		boolean haveActiveInn = false;
		for (Building building : buildings)
			if ((building instanceof Stockpile) &&
				((Stockpile)building).getResources().containsKey(ResourceType.WINE) &&
				building.hasWorkers())
				haveActiveInn = true;
		if (!haveActiveInn) return;
		if (decreaseResource(ResourceType.WINE, 1) > 0)
			changePopularity(1);
	}

	public int getResourceCount(ResourceType resourceType) {
		if (resourceType == ResourceType.GOLD)
			return this.gold;
		if (resourceType == ResourceType.POPULATION)
			return getPopulation();
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
			setGold(getGold() + count);
			return count;
		}
		if (resourceType == ResourceType.POPULATION)
			return increasePopulation(count);
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
			setGold(getGold() - count);
			return count;
		}
		if (resourceType == ResourceType.POPULATION)
			return decreasePopulation(count);
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
		ResourceType[] foodTypes = ResourceType.foodTypes;
		int foodVariety = 0;
		for (int i = 0; i < foodTypes.length; i++) {
			if (getResourceCount(foodTypes[i]) > 0) {
				foodVariety++;
			}
		}
		return foodVariety;
	}

	public int[] findKeep() {
		for (Building building : buildings)
			if ((building instanceof DefensiveStructure) &&
				((DefensiveStructure)building).getType() == DefensiveStructureType.KEEP)
				return new int[] {building.getX(), building.getY()};
		return null;
	}

	public void updateStats() {
		useWine();
		updateBuildings();
		updatePopulation();
		updateFood();
		updateTax();
		updatePopularity();
		updateWorkers();
	}

	private void updateWorkers() {
		if (getWorkersCount() > getPopulation()) {	// disable some buildings
			int initialDifference = getWorkersCount() - getPopulation();
			for (Building building : buildings) {
				if (building.hasWorkers() && building.getNeededWorkers() > 0) {
					initialDifference -= building.getNeededWorkers();
					building.setHasWorkers(false);
					if (initialDifference <= 0) break;
				}
			}
		}
		else {	// enable buildings if possible
			for (Building building : buildings)
				if (!building.hasWorkers() && getPopulation() - getWorkersCount() >= building.getNeededWorkers())
					building.setHasWorkers(true);
		}
	}

	private void updateBuildings() {
		for (Building building : buildings) {
			if (building instanceof ResourceConverterBuilding)
				((ResourceConverterBuilding)building).performConversion();
		}
	}

	private void decreaseFood(int amount) {
		ResourceType[] foodTypes = ResourceType.foodTypes;
		for (int i = 0; i < foodTypes.length; i++) {
			int currentAmount = getResourceCount(foodTypes[i]);
			decreaseResource(foodTypes[i], Math.min(currentAmount, amount));
			amount = Math.max(0, amount - currentAmount);
		}
	}

	private int getFoodCount() {
		ResourceType[] foodTypes = ResourceType.foodTypes;
		int sum = 0;
		for (int i = 0; i < foodTypes.length; i++)
			sum += getResourceCount(foodTypes[i]);
		return sum;
	}

	private void updateFood() {
		if (getFoodCount() == 0) setFoodRate(-2);
		int amount = PopularityFormulas.foodRate2FoodAmountx2(foodRate) * getPopulation() / 2;
		decreaseFood(amount);
	}

	private void updateTax() {
		if (getGold() == 0 && taxRate < 0) setTaxRate(0);
		int money = PopularityFormulas.taxRate2Moneyx5(taxRate) * getPopulation() / 5;
		setGold(Math.max(0, getGold() + money));
	}

	private int getPopulationGrowthRate() {
		if (popularity < 10) return -2;
		if (popularity < 30) return -1;
		if (popularity < 50) return 1;
		if (popularity < 80) return 2;
		return 3;
	}

	private void updatePopulation() {
		int growthRate = getPopulationGrowthRate();
		if (growthRate > 0) increasePopulation(growthRate);
		else if (growthRate < 0) decreasePopulation(-growthRate);
	}

	public int getPopulation() {
		int sum = 0;
		for (Building building : buildings)
			sum += building.getResidents();
		return sum;
	}

	public int increasePopulation(int amount) {
		int canAdd = 0;
		for (Building building : buildings) {
			while (building.getResidents() < building.getResidentsCapacity() && canAdd < amount) {
				building.setResidents(building.getResidents() + 1);
				canAdd++;
			}
		}
		if (this == StrongHold.getCurrentGame().getCurrentPlayer())
			GameMenu.getInstance().updateToolBarReport();
		return canAdd;
	}

	public int decreasePopulation(int amount) {
		int canDecrease = 0;
		for (Building building : buildings) {
			while (building.getResidents() > 0 && canDecrease < amount) {
				building.setResidents(building.getResidents() - 1);
				canDecrease++;
			}
		}
		if (this == StrongHold.getCurrentGame().getCurrentPlayer())
			GameMenu.getInstance().updateToolBarReport();
		return canDecrease;
	}

	public int getMaxPopulation() {
		int result = 0;
		for (Building building : buildings)
			result += building.getResidentsCapacity();
		return result;
	}

	public int getWorkersCount() {
		int sum = 0;
		for (Building building : buildings)
			if (building.hasWorkers())
				sum += building.getNeededWorkers();
		return sum;
	}


}
