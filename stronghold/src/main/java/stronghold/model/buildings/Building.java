package stronghold.model.buildings;

import java.io.Serializable;

import stronghold.model.Government;

public abstract class Building implements Serializable {
	private final int maxHp;
	private int hp;
	private int x;
	private int y;
	private final Government owner;
	private final String name;
	private final int neededWorkers;
	private final int turnOfBuild;

	public Building(int maxHp, int x, int y, Government owner, String name, int neededWorkers, int terunOfBuild) {
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.x = x;
		this.y = y;
		this.owner = owner;
		this.name = name;
		this.neededWorkers = neededWorkers;
		this.turnOfBuild = terunOfBuild;
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
	public Government getOwner() {
		return owner;
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
