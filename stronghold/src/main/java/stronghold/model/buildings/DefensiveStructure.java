package stronghold.model.buildings;

public class DefensiveStructure extends Building {
	private final int fireRange;
	private final int defendRange;
	private final boolean isGate;	// TODO: opening a gate is just setVerticalHeight(1)
	private final boolean isVertical;	// only used for gates
	
	private boolean isCaptured = false;

	public DefensiveStructure(int maxHp, String name, int neededWorkers, int width, int height, int verticalHeight,
			boolean isSelectable, int x, int y, int residents, int residentsCapacity, boolean hasWorkers,
			int ownerIndex, int turnOfBuild, int fireRange, int defendRange, boolean isGate, boolean isVertical) {
		super(maxHp, name, neededWorkers, width, height, verticalHeight, isSelectable, x, y, residents,
				residentsCapacity, hasWorkers, ownerIndex, turnOfBuild);
		this.fireRange = fireRange;
		this.defendRange = defendRange;
		this.isGate = isGate;
		this.isVertical = isVertical;
	}

	public int getFireRange() {
		return fireRange;
	}
	public int getDefendRange() {
		return defendRange;
	}
	public boolean isGate() {
		return isGate;
	}
	public boolean isVertical() {
		return isVertical;
	}
	public boolean isCaptured() {
		return isCaptured;
	}
	public void setCaptured(boolean isCaptured) {
		this.isCaptured = isCaptured;
	}
}
