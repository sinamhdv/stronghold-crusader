package stronghold.model.people;

import java.io.Serializable;

import stronghold.controller.MapEditorMenuController;
import stronghold.model.Government;
import stronghold.model.StrongHold;
import stronghold.model.buildings.Trap;
import stronghold.model.map.MapTile;
import stronghold.model.map.Path;
import stronghold.model.map.Pathfinding;

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
	private final PersonType type;

	private int ownerIndex;
	private int x;
	private int y;
	private int destX = -1;
	private int destY = -1;

	public Person(String name, int speed, int hp, int damage, int visibilityRange, int attackRate, int attackRange,
			boolean canClimbLadder, boolean canClimbWalls, boolean canDigMoats, boolean hasBurningOil, PersonType type,
			int ownerIndex, int x, int y) {
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
		this.type = type;
		this.ownerIndex = ownerIndex;
		this.x = x;
		this.y = y;
	}

	public Person(Person other, int x, int y, int ownerIndex) {
		this(other.name, other.speed, other.hp, other.damage, other.visibilityRange, other.attackRate,
			other.attackRange, other.canClimbLadder, other.canClimbWalls, other.canDigMoats, other.hasBurningOil,
			other.type, ownerIndex, x, y);
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
	public PersonType getType() {
		return type;
	}
	public int getDestX() {
		return destX;
	}
	public int getDestY() {
		return destY;
	}
	public void setDestination(int destX, int destY) {
		this.destX = destX;
		this.destY = destY;
	}

	public boolean hurt(int damage) {
		setHp(getHp() - damage);
		if (getHp() <= 0) {	// die
			MapTile tile = StrongHold.getCurrentGame().getMap().getGrid()[x][y];
			tile.getPeople().remove(this);
			// TODO: the government must lose if the lord dies
			return true;
		}
		return false;
	}

	public void moveTowardsDestination() {
		Path path = Pathfinding.findPath(this);
		int[][] cells = path.getCells();
		for (int i = 0; i < cells.length; i++) {	// check for killing pits
			MapTile tile = StrongHold.getCurrentGame().getMap().getGrid()[cells[i][0]][cells[i][1]];
			if (tile.getBuilding() instanceof Trap) {
				Trap trap = (Trap)tile.getBuilding();
				if (!trap.hasDogs() && trap.getOwnerIndex() != this.getOwnerIndex()) {
					boolean died = this.hurt(trap.getDamage());
					MapEditorMenuController.setMap(StrongHold.getCurrentGame().getMap());
					MapEditorMenuController.setSelectedGovernment(this.getOwnerIndex());
					MapEditorMenuController.eraseBuilding(trap);
					if (died) return;
				}
			}
		}
		setX(cells[cells.length - 1][0]);
		setY(cells[cells.length - 1][1]);
	}

	public void disband() {
		// TODO
	}
}
