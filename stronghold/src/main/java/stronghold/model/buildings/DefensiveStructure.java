package stronghold.model.buildings;

public class DefensiveStructure extends Building {
	private final int fireRange;
	private final int defendRange;
	private final boolean isGate;	// TODO: opening a gate is just setVerticalHeight(1)
	private final boolean isVertical;	// only used for gates
	
	private boolean isCaptured = false;

	public DefensiveStructure(int maxHp, String name, int neededWorkers, int width, int height, int verticalHeight,
			boolean isSelectable, int x, int y, int residentsCapacity, int ownerIndex, int fireRange, int defendRange,
			boolean isGate, boolean isVertical) {
		super(maxHp, name, neededWorkers, width, height, verticalHeight, isSelectable, x, y, residentsCapacity,
				ownerIndex);
		this.fireRange = fireRange;
		this.defendRange = defendRange;
		this.isGate = isGate;
		this.isVertical = isVertical;
	}

	private DefensiveStructure(DefensiveStructure model, int x, int y, int ownerIndex) {
		super(model, x, y, ownerIndex);
		this.fireRange = model.fireRange;
		this.defendRange = model.defendRange;
		this.isGate = model.isGate;
		this.isVertical = model.isVertical;
	}

	@Override
	public Building generateCopy(int x, int y, int ownerIndex) {
		return new DefensiveStructure(this, x, y, ownerIndex);
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
