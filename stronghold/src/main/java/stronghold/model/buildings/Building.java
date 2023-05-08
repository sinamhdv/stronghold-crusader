package stronghold.model.buildings;

import java.io.Serializable;

import stronghold.model.Government;
import stronghold.model.StrongHold;

public abstract class Building implements Serializable {
	private final int maxHp;
	private int hp;
	private int x;
	private int y;
	private final int ownerIndex;
	private final String name;
	private final int neededWorkers;
	private final int turnOfBuild;

	public Building(int maxHp, int x, int y, int ownerIndex, String name, int neededWorkers, int turnOfBuild) {
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.x = x;
		this.y = y;
		this.ownerIndex = ownerIndex;
		this.name = name;
		this.neededWorkers = neededWorkers;
		this.turnOfBuild = turnOfBuild;
	}

	public int getMaxHp() {
		return maxHp;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getOwnerIndex() {
		return ownerIndex;
	}
	public Government getOwner() {
		return (ownerIndex == -1 ? null : StrongHold.getCurrentGame().getGovernments()[ownerIndex]);
	}
	public String getName() {
		return name;
	}
	public int getNeededWorkers() {
		return neededWorkers;
	}

	public int getTurnOfBuild() {
		return turnOfBuild;
	}

	
	
}
