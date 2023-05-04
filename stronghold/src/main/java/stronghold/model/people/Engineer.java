package stronghold.model.people;

import stronghold.model.Government;
import stronghold.model.buildings.Building;

public class Engineer extends Building {
	private boolean hasBurningOil;

	public Engineer(int maxHp, int x, int y, Government owner, String name, int neededWorkers, int terunOfBuild,
			boolean hasBurningOil) {
		super(maxHp, x, y, owner, name, neededWorkers, terunOfBuild);
		this.hasBurningOil = hasBurningOil;
	}

	public boolean isHasBurningOil() {
		return hasBurningOil;
	}
	
	
}