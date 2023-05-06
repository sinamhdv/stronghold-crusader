package stronghold.model.buildings;

import stronghold.model.people.Engineer;

public class OilSmelter extends Building {
	private Engineer worker;

	public OilSmelter(int maxHp, int x, int y, int ownerIndex, String name, int neededWorkers, int terunOfBuild,
			Engineer worker) {
		super(maxHp, x, y, ownerIndex, name, neededWorkers, terunOfBuild);
		this.worker = worker;
	}

	public Engineer getWorker() {
		return worker;
	}
	
}
