package stronghold.model.buildings;

import stronghold.model.Government;
import stronghold.model.people.Engineer;

public class OilSmelter extends Building {
	private Engineer worker;

	public OilSmelter(int maxHp, int x, int y, Government owner, String name, int neededWorkers, int terunOfBuild,
			Engineer worker) {
		super(maxHp, x, y, owner, name, neededWorkers, terunOfBuild);
		this.worker = worker;
	}

	public Engineer getWorker() {
		return worker;
	}
	
}
