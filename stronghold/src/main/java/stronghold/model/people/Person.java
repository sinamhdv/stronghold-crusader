package stronghold.model.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import stronghold.controller.MapEditorMenuController;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.StrongHold;
import stronghold.model.buildings.Trap;
import stronghold.model.map.MapTile;
import stronghold.model.map.Path;
import stronghold.model.map.Pathfinding;
import stronghold.utils.Miscellaneous;

public class Person implements Serializable {
	private final String name;
	private int speed;
	private int hp;
	private int damage;
	private final int visibilityRange;
	private final int attackRate;
	private int attackRange;
	private StanceType stance = StanceType.STANDING;
	private final boolean canClimbLadder;
	private final boolean canClimbWalls;
	private final boolean canDigMoats;
	private final boolean hasBurningOil;
	private final PersonType type;

	private int ownerIndex;
	private int x;
	private int y;
	private int destX;
	private int destY;

	private int patrolX;
	private int patrolY;
	private boolean patrolMode = false;

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
		this.destX = x;
		this.destY = y;
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

	public int getPatrolX() {
		return patrolX;
	}

	public int getPatrolY() {
		return patrolY;
	}

	public void setPatrol(int patrolX, int patrolY) {
		this.patrolX = x;
		this.patrolY = y;
		this.destX = patrolX;
		this.destY = patrolY;
		this.patrolMode = true;
	}

	public void setPatrolMode(boolean patrolMode) {
		this.patrolMode = patrolMode;
	}

	public boolean getPatrolMode() {
		return patrolMode;
	}

	public boolean hurt(int damage) {
		setHp(getHp() - damage);
		if (getHp() <= 0) { // die
			MapTile tile = StrongHold.getCurrentGame().getMap().getGrid()[x][y];
			tile.getPeople().remove(this);
			getOwner().getPeople().remove(this);
			// TODO: the government must lose if the lord dies
			return true;
		}
		return false;
	}

	public void moveTowardsDestination() {
		Path path = Pathfinding.findPath(this);

		System.out.println("DBG: the found path for " + name + ":");
		for (int[] cell : path.getCells())
			System.out.print("(" + cell[0] + ", " + cell[1] + ") -> ");
		System.out.println();

		int[][] cells = path.getCells();
		for (int i = 0; i < cells.length; i++) { // check for killing pits
			MapTile tile = StrongHold.getCurrentGame().getMap().getGrid()[cells[i][0]][cells[i][1]];
			if (tile.getBuilding() instanceof Trap) {
				Trap trap = (Trap) tile.getBuilding();
				if (!trap.hasDogs() && trap.getOwnerIndex() != this.getOwnerIndex()) {
					boolean died = this.hurt(trap.getDamage());
					MapEditorMenuController.setMap(StrongHold.getCurrentGame().getMap());
					MapEditorMenuController.setSelectedGovernment(this.getOwnerIndex());
					MapEditorMenuController.eraseBuilding(trap);
					if (died)
						return;
				}
			}
		}
		StrongHold.getCurrentGame().getMap().getGrid()[x][y].getPeople().remove(this);
		setX(cells[cells.length - 1][0]);
		setY(cells[cells.length - 1][1]);
		StrongHold.getCurrentGame().getMap().getGrid()[x][y].addPerson(this);

		if (patrolMode && x == destX && y == destY) { // reached patrol destination
			setPatrol(patrolX, patrolY);
		}
	}

	@Override
	public String toString() {
		String result = name + "(" + ownerIndex + ") (hp=" + hp +
				") @ (" + x + ", " + y + ") -> (" + destX + ", " + destY + ")";
		return result;
	}

	public void disband() {
		// TODO
	}

	public void automaticFight(Person person) {
		if (getDistance(person) <= (attackRange * attackRange)) {
			person.hurt(getDamage());
		} else if ((stance == StanceType.DEFENSIVE && getDistance(
				person) == (StanceType.DEFENSIVE.getRadiusOfMovement() * StanceType.DEFENSIVE.getRadiusOfMovement()))
				|| stance == StanceType.OFFENSIVE) {
			setDestination(person.getX(), person.getY());
		}

	}

	public int getDistance(Person person) {
		int destance = (x - person.getX()) * (x - person.getX()) + (y - person.getY()) * (y - person.getY());
		return destance;
	}

	public Person getEnemy() {
		int limit = Math.max(stance.getRadiusOfMovement(), attackRange);
		Game currentGame = StrongHold.getCurrentGame();
		int randomNumber = Miscellaneous.RANDOM_GENERATOR.nextInt(1);
		if(randomNumber == 0) {
			for(int i = 0; i < limit; i++) {
				ArrayList<Person> peopleClon = new ArrayList<>(currentGame.getMap().getGrid()[x+i][y].getPeople());
				for(Person person : peopleClon) {
					if(person.getOwner() != getOwner()) {
						return person;
					}
				}
				ArrayList<Person> people= new ArrayList<>(currentGame.getMap().getGrid()[x][y+i].getPeople());
				for(Person person : people) {
					if(person.getOwner() != getOwner())
						return person;
				}
			}
		}

		else {
			for(int i = 0; i < limit; i++) {
				ArrayList<Person> people= new ArrayList<>(currentGame.getMap().getGrid()[x][y+i].getPeople());
				for(Person person : people) {
					if(person.getOwner() != getOwner())
						return person;
				}
				ArrayList<Person> peopleClon = new ArrayList<>(currentGame.getMap().getGrid()[x+i][y].getPeople());
				for(Person person : peopleClon) {
					if(person.getOwner() != getOwner()) {
						return person;
					}
				}
			}
		}

		return null;
	}


}
