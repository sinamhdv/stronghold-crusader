package stronghold.model.people;

import java.io.Serializable;
import java.util.ArrayList;

import stronghold.controller.GameMenuController;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.StrongHold;
import stronghold.model.buildings.Building;
import stronghold.model.buildings.DefensiveStructure;
import stronghold.model.buildings.DefensiveStructureType;
import stronghold.model.buildings.Trap;
import stronghold.model.map.MapTile;
import stronghold.model.map.Path;
import stronghold.model.map.Pathfinding;
import stronghold.utils.Miscellaneous;
import stronghold.utils.PopularityFormulas;

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
	private final int attackSuccessProbability;
	private final boolean canAttackPeople;
	private final boolean canAttackBuildings;

	private PersonAction action = PersonAction.IDLE;
	private int ownerIndex;
	private int x;
	private int y;
	private int destX;
	private int destY;
	private int patrolX;
	private int patrolY;
	private int attackTargetX;
	private int attackTargetY;
	private boolean hasAttackTarget = false;

	public Person(String name, int speed, int hp, int damage, int visibilityRange, int attackRate, int attackRange,
			boolean canClimbLadder, boolean canClimbWalls, boolean canDigMoats, boolean hasBurningOil, PersonType type,
			int ownerIndex, int x, int y, int attackSuccessProbability, boolean canAttackPeople, boolean canAttackBuildings) {
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
		this.attackSuccessProbability = attackSuccessProbability;
		this.canAttackPeople = canAttackPeople;
		this.canAttackBuildings = canAttackBuildings;
	}

	public Person(Person other, int x, int y, int ownerIndex) {
		this(other.name, other.speed, other.hp, other.damage, other.visibilityRange, other.attackRate,
				other.attackRange, other.canClimbLadder, other.canClimbWalls, other.canDigMoats, other.hasBurningOil,
				other.type, ownerIndex, x, y, other.attackSuccessProbability, other.canAttackPeople, other.canAttackBuildings);
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
		return PopularityFormulas.combineDamageAndFear(damage, getOwner().getFearFactor());
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

	public void setMoveDestination(int destX, int destY) {
		setDestination(destX, destY);
		action = PersonAction.MOVE;
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
		this.action = PersonAction.PATROL;
	}

	public int getAttackTargetX() {
		return attackTargetX;
	}
	
	public int getAttackTargetY() {
		return attackTargetY;
	}
	
	public PersonAction getAction() {
		return action;
	}

	public void setAction(PersonAction action) {
		this.action = action;
	}

	public void die() {
		MapTile tile = StrongHold.getCurrentGame().getMap().getGrid()[x][y];
		tile.getPeople().remove(this);
		getOwner().getPeople().remove(this);
		StrongHold.getCurrentGame().getSelectedUnits().remove(this);
		if (this.type == PersonType.LORD)
			getOwner().lose();
	}

	public boolean hurt(int damage) {
		setHp(getHp() - damage);
		if (getHp() <= 0) {
			die();
			return true;
		}
		return false;
	}

	public void moveTowardsDestination() {
		Path path = Pathfinding.findPath(this);

		if (GameMenuController.getDebugMode()) {
			System.out.print("DBG: PATH(" + name + "): ");
			for (int[] cell : path.getCells())
				System.out.print("(" + cell[0] + ", " + cell[1] + ") -> ");
			System.out.println();
		}

		int[][] cells = path.getCells();
		for (int i = 0; i < cells.length; i++) { // check for killing pits
			MapTile tile = StrongHold.getCurrentGame().getMap().getGrid()[cells[i][0]][cells[i][1]];
			if (tile.getBuilding() instanceof Trap) {
				Trap trap = (Trap) tile.getBuilding();
				if (!trap.hasDogs() && trap.getOwnerIndex() != this.getOwnerIndex()) {
					boolean died = this.hurt(trap.getDamage());
					trap.destroy();
					if (died)
						return;
				}
			}
		}
		StrongHold.getCurrentGame().getMap().getGrid()[x][y].getPeople().remove(this);
		setX(cells[cells.length - 1][0]);
		setY(cells[cells.length - 1][1]);
		StrongHold.getCurrentGame().getMap().getGrid()[x][y].addPerson(this);

		if (x == destX && y == destY) { // reached destination
			if (action == PersonAction.MOVE)
				setAction(PersonAction.IDLE);
			else if (action == PersonAction.PATROL)
				setPatrol(patrolX, patrolY);
		}
	}

	public void nextTurnUpdate() {
		searchForEnemies();
		moveTowardsDestination();
	}

	public void fullTurnUpdate() {
		attackTargetObject(selectTargetObject());
	}

	@Override
	public String toString() {
		String result = name + "(" + ownerIndex + ") (hp=" + hp + ", stance=" + stance +
				") @ (" + x + ", " + y + ") -> (" + destX + ", " + destY + ")";
		result += " action=" + action;
		if (action == PersonAction.ATTACK) result += "(" + attackTargetX + ", " + attackTargetY + ")";
		else if (action == PersonAction.PATROL) result += "(" + patrolX + ", " + patrolY + ")";
		return result;
	}

	public void stop() {
		setDestination(getX(), getY());
		setAction(PersonAction.IDLE);
		hasAttackTarget = false;
	}

	public void setAttackTarget(int targetX, int targetY) {
		if (getDistance(targetX, targetY) <= getAttackRange() * getAttackRange())
			setDestination(x, y);
		else
			setDestination(targetX, targetY);
		this.attackTargetX = targetX;
		this.attackTargetY = targetY;
		action = PersonAction.ATTACK;
		hasAttackTarget = true;
	}

	private void printAttackMessage(Object target) {
		if (target == null) return;
		String message = "DBG: ATTACK: " + name + "(" + ownerIndex + ") >>> ";
		if (target instanceof Person) {
			Person person = (Person) target;
			message += person.getName() + "(" + person.getOwnerIndex() + ")[";
			message += (person.getHp() + getDamage()) + " -> " + person.getHp() + "]";
		}
		else if (target instanceof Building) {
			Building building = (Building) target;
			message += building.getName() + "(" + building.getOwnerIndex() + ")[";
			message += (building.getHp() + getDamage()) + " -> " + building.getHp() + "]";
		}
		System.out.println(message);
	}

	private void attackTargetObject(Object target) {
		if (Miscellaneous.RANDOM_GENERATOR.nextInt(1, 101) > attackSuccessProbability)
			return;
		if (target instanceof Person)
			((Person)target).hurt(getDamage());
		else if (target instanceof Building)
			((Building)target).hurt(getDamage());
		if (GameMenuController.getDebugMode())
			printAttackMessage(target);
	}

	private Object selectTargetObject() {
		if (hasAttackTarget) {	// manual target selection
			if (getDistance(attackTargetX, attackTargetY) <= getAttackRange() * getAttackRange()) {
				stop();
				hasAttackTarget = true;
				Object target = selectTargetFromCell(attackTargetX, attackTargetY);
				if (target != null) return target;
				else hasAttackTarget = false;
			}
		}
		// search the attackRange for the closest target
		int[] enemyXY = findClosestEnemy(getAttackRange(), true);
		if (enemyXY[0] == -1)
			return null;
		return selectTargetFromCell(enemyXY[0], enemyXY[1]);
	}

	private int[] findClosestEnemy(int range, boolean includeBuildings) {
		int[] result = {-1, -1};
		int resultDistance = 9999999;
		for (int i = x - range; i <= x + range; i++) {
			for (int j = y - range; j <= y + range; j++) {
				if (i < 0 || j < 0 || i >= StrongHold.getCurrentGame().getMap().getHeight() ||
					j >= StrongHold.getCurrentGame().getMap().getWidth())
					continue;
				if (getDistance(i, j) > range * range)
					continue;
				if (getDistance(i, j) < resultDistance) {
					Object target = selectTargetFromCell(i, j);
					if (target != null && (includeBuildings || (target instanceof Person))) {
						result[0] = i;
						result[1] = j;
						resultDistance = getDistance(i, j);
					}
				}
			}
		}
		return result;
	}

	private Object selectTargetFromCell(int cellX, int cellY) {
		MapTile tile = StrongHold.getCurrentGame().getMap().getGrid()[cellX][cellY];
		if (canAttackPeople) {
			ArrayList<Object> enemies = new ArrayList<>();
			for (Person person : tile.getPeople())
				if (person.getOwnerIndex() != ownerIndex)
					enemies.add(person);
			if (!enemies.isEmpty())
				return enemies.get(Miscellaneous.RANDOM_GENERATOR.nextInt(enemies.size()));
		}
		if (canAttackBuildings) {
			if (tile.getBuilding() != null && tile.getBuilding().getOwnerIndex() != ownerIndex &&
				(!(tile.getBuilding() instanceof Trap) || ((Trap)tile.getBuilding()).hasDogs()) &&
				StrongHold.getCurrentGame().getMap().getGrid()[x][y].getHeight() == 0) {
				return tile.getBuilding();
			}
		}
		return null;
	}

	public void searchForEnemies() {
		if (action != PersonAction.IDLE && action != PersonAction.PATROL) return;
		if (stance == StanceType.STANDING) return;
		int[] enemyXY = findClosestEnemy(stance.getRadiusOfMovement(), false);
		if (enemyXY[0] == -1) return;
		action = PersonAction.IDLE;
		setDestination(enemyXY[0], enemyXY[1]);
	}

	public int getDistance(int targetX, int targetY) {
		return (x - targetX) * (x - targetX) + (y - targetY) * (y - targetY);
	}

	public boolean isVisible() {
		Game game = StrongHold.getCurrentGame();
		if (game == null) return true;
		if (type != PersonType.ASSASSIN || ownerIndex == game.getCurrentPlayerIndex()) return true;
		for (int i = x - getVisibilityRange(); i <= x + getVisibilityRange(); i++) {
			for (int j = y - getVisibilityRange(); j <= y + getVisibilityRange(); j++) {
				if (!Miscellaneous.checkCoordinatesOnMap(game.getMap(), i, j)) continue;
				for (Person person : game.getMap().getGrid()[i][j].getPeople())
					if (person.getOwnerIndex() == game.getCurrentPlayerIndex())
						return true;
			}
		}
		return false;
	}

	public Building getRandomDefensiveStructure() {
		Game game = StrongHold.getCurrentGame();
		for (int i = x - GameMenuController.MAX_TUNNEL_DISTANCE; i <= x + GameMenuController.MAX_TUNNEL_DISTANCE; i++) {
			for (int j = y - GameMenuController.MAX_TUNNEL_DISTANCE; j <= y + GameMenuController.MAX_TUNNEL_DISTANCE; j++) {
				if (!Miscellaneous.checkCoordinatesOnMap(game.getMap(), i, j)) continue;
				Building building = game.getMap().getGrid()[i][j].getBuilding();
				if (!(building instanceof DefensiveStructure)) continue;
				if (((DefensiveStructure)building).getType() == DefensiveStructureType.KEEP) continue;
				if (building.getOwnerIndex() == ownerIndex) continue;
				return building;
			}
		}
		return null;
	}

	public boolean digTunnel() {
		Building building = getRandomDefensiveStructure();
		if (building == null)
			return false;
		else {
			building.destroy();
			die();
			return true;
		}
	}
}
