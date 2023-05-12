package stronghold.model.buildings;

import stronghold.model.people.Person;

public class OilSmelter extends Building {
	private Person worker = null;
	
	public OilSmelter(int maxHp, String name, int neededWorkers, int width, int height, int verticalHeight,
			boolean isSelectable, int x, int y, int residentsCapacity, int ownerIndex) {
		super(maxHp, name, neededWorkers, width, height, verticalHeight, isSelectable, x, y, residentsCapacity,
				ownerIndex);
	}

	public OilSmelter(OilSmelter model, int x, int y, int ownerIndex) {
		super(model, x, y, ownerIndex);
	}

	@Override
	public Building generateCopy(int x, int y, int ownerIndex) {
		return new OilSmelter(this, x, y, ownerIndex);
	}

	public Person getWorker() {
		return worker;
	}
	public void setWorker(Person worker) {
		this.worker = worker;
	}
}
