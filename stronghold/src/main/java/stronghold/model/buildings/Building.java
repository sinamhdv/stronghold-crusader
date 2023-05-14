package stronghold.model.buildings;

import java.io.Serializable;

import stronghold.controller.MapEditorMenuController;
import stronghold.model.Government;
import stronghold.model.StrongHold;

public class Building implements Serializable {
	private final int maxHp;
	private final String name;
	private final int neededWorkers;
	private final int width;
	private final int height;
	private int verticalHeight;
	private final boolean isSelectable;
	private final int residentsCapacity;

	private int hp;
	private int x;
	private int y;
	private int residents;
	private boolean hasWorkers;
	private final int ownerIndex;
	private final int turnOfBuild;

	public Building(int maxHp, String name, int neededWorkers, int width, int height, int verticalHeight,
			boolean isSelectable, int x, int y, int residentsCapacity, int ownerIndex) {
		this.maxHp = maxHp;
		this.name = name;
		this.neededWorkers = neededWorkers;
		this.width = width;
		this.height = height;
		this.verticalHeight = verticalHeight;
		this.isSelectable = isSelectable;
		this.x = x;
		this.y = y;
		this.residents = 0;
		this.residentsCapacity = residentsCapacity;
		this.hasWorkers = false;
		this.ownerIndex = ownerIndex;
		this.turnOfBuild = (StrongHold.getCurrentGame() == null ? 0 : StrongHold.getCurrentGame().getPassedTurns());
		this.hp = maxHp;
	}

	protected Building(Building model, int x, int y, int ownerIndex) {
		this(model.maxHp, model.name, model.neededWorkers, model.width, model.height, model.verticalHeight,
			model.isSelectable, x, y, model.residentsCapacity, ownerIndex);
	}

	public Building generateCopy(int x, int y, int ownerIndex) {
		return new Building(this, x, y, ownerIndex);
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
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public int getVerticalHeight() {
		return verticalHeight;
	}
	public boolean isSelectable() {
		return isSelectable;
	}
	public int getResidents() {
		return residents;
	}
	public void setResidents(int residents) {
		this.residents = residents;
	}
	public int getResidentsCapacity() {
		return residentsCapacity;
	}
	public boolean hasWorkers() {
		return hasWorkers;
	}
	public void setHasWorkers(boolean hasWorkers) {
		this.hasWorkers = hasWorkers;
	}
	public void setVerticalHeight(int verticalHeight) {
		this.verticalHeight = verticalHeight;
	}

	@Override
	public String toString() {
		String result = name + "(" + ownerIndex + ") -> hp=" + hp + "/" + maxHp;
		if (residentsCapacity > 0) result += ", residents=" + residents + "/" + residentsCapacity;
		if (neededWorkers > 0) result += ", neededWorkers=" + neededWorkers + ", hasWorkers=" + hasWorkers;
		return result;
	}

	public void destroy() {
		MapEditorMenuController.setMap(StrongHold.getCurrentGame().getMap());
		MapEditorMenuController.setSelectedGovernment(this.ownerIndex);
		MapEditorMenuController.eraseBuilding(this);
		getOwner().getBuildings().remove(this);
		if ((this instanceof DefensiveStructure) && ((DefensiveStructure)this).getType() == DefensiveStructureType.KEEP)
			getOwner().lose();
	}

	public boolean hurt(int damage) {
		setHp(getHp() - damage);
		if (getHp() <= 0) {
			destroy();
			return true;
		}
		return false;
	}

	public boolean isVisible() {
		return !(this instanceof Trap) || ((Trap)this).hasDogs();
	}
}
