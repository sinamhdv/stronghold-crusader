package stronghold.model.buildings;

import java.util.HashMap;

import stronghold.model.ResourceType;
import stronghold.model.StrongHold;

public class Inn extends Stockpile {
	int popularityBost;
	int wineUsegeRate;
	public Inn(int maxHp, int x, int y, int ownerIndex, String name, int neededWorkers, int capacity,
			HashMap<ResourceType, Integer> resources, int popularityBost, int wineUsegeRate, int turnOfBuild) {
		super(maxHp, x, y, ownerIndex, name, neededWorkers, capacity, resources, turnOfBuild);
		this.popularityBost = popularityBost;
		this.wineUsegeRate = wineUsegeRate;
	}

	public int getPopularityBost() {
		return popularityBost;
	}

	public int getWineUsegeRate() {
		return wineUsegeRate;
	}

	public void setPopularityBost(int popularityBost) {
		this.popularityBost = popularityBost;
	}

	public void setWineUsegeRate(int wineUsegeRate) {
		this.wineUsegeRate = wineUsegeRate;
	}

	public void useWine() {
		if ((StrongHold.getCurrentGame().getPassedTurns() - getTurnOfBuild()) % wineUsegeRate == 0) {
			resources.put(ResourceType.WINE, resources.get(ResourceType.WINE) - 1);
			getOwner().setPopularity(getOwner().getPopularity() + popularityBost);
			return;
		}
		return;
	}
}
