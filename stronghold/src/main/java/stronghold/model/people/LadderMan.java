package stronghold.model.people;

import stronghold.model.Government;
import stronghold.model.buildings.Building;

public class LadderMan extends Building {

	public LadderMan(int maxHp, int x, int y, Government owner, String name, int neededWorkers, int terunOfBuild) {
		super(maxHp, x, y, owner, name, neededWorkers, terunOfBuild);
	}
	
	public void setLadderMan (int x, int y) {
		//TODO:
	}
}