package stronghold.model.people;

import java.io.Serializable;

import stronghold.model.Government;
import stronghold.model.StrongHold;

public class Person implements Serializable {
	private final String name;
	private int speed;
	private int hp;
	private int damage;
	private final int visibilityRange;
	private final int attackRate;
	private int attackRange;
	private StanceType stance = StanceType.NORMAL;
	private final boolean canClimbLadder;
	private final boolean canClimbWalls;
	private final boolean canDigMoats;
	private final boolean hasBurningOil;

	private int ownerIndex;
	private int x;
	private int y;

	public Person(String name, int speed, int hp, int damage, int visibilityRange, int attackRate, int attackRange,
			boolean canClimbLadder, boolean canClimbWalls, boolean canDigMoats, boolean hasBurningOil, int ownerIndex,
			int x, int y) {
		this.name = name;
		this.speed = speed;
		this.hp = hp;
		this.damage = damage;
		this.visibilityRange = visibilityRange;
		this.attackRate = attackRate;
		this.attackRange = attackRange;
		this.canClimbLadder = canClimbLadder;
		this.canClimbWalls = canClimbWalls;
		this.canDigMoats = canDigMoats;
		this.hasBurningOil = hasBurningOil;
		this.ownerIndex = ownerIndex;
		this.x = x;
		this.y = y;
	}

	public Person(Person other, int x, int y, int ownerIndex) {
		this(other.name, other.speed, other.hp, other.damage, other.visibilityRange, other.attackRate,
			other.attackRange, other.canClimbLadder, other.canClimbWalls, other.canDigMoats, other.hasBurningOil,
			ownerIndex, x, y);
	}

	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public Government getOwner() {
		return (ownerIndex == -1 ? null : StrongHold.getCurrentGame().getGovernments()[ownerIndex]);
	}
	public int getOwnerIndex() {
		return ownerIndex;
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
	public String getName() {
		return name;
	}
	public int getAttackRange() {
		return attackRange;
	}
	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}
	public StanceType getStance() {
		return stance;
	}
	public void setStance(StanceType stance) {
		this.stance = stance;
	}
	public int getVisibilityRange() {
		return visibilityRange;
	}
	public int getAttackRate() {
		return attackRate;
	}
	public boolean canClimbLadder() {
		return canClimbLadder;
	}
	public boolean canClimbWalls() {
		return canClimbWalls;
	}
	public boolean canDigMoats() {
		return canDigMoats;
	}
	public void setOwnerIndex(int ownerIndex) {
		this.ownerIndex = ownerIndex;
	}
	public boolean hasBurningOil() {
		return hasBurningOil;
	}

	public void move(int targetX, int targetY) {
		// TODO: run BFS on the map
	}

	public void disband() {
		// TODO
	}
}
