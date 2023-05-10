package stronghold.model.buildings;

import stronghold.model.people.Person;

public class OilSmelter extends Building {
	private Person worker;

	public OilSmelter(int maxHp, int x, int y, int ownerIndex, String name, int neededWorkers, int terunOfBuild,
			Person worker) {
		super(maxHp, x, y, ownerIndex, name, neededWorkers, terunOfBuild);
		this.worker = worker;
	}

	public Person getWorker() {
		return worker;
	}
	
}
