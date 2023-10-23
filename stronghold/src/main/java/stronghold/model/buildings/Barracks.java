package stronghold.model.buildings;

public class Barracks extends Building {
	private final int popularityBoost;
	private final String[] troopNames;

	public Barracks(int maxHp, String name, int neededWorkers, int width, int height, int verticalHeight,
			boolean isSelectable, int x, int y, int residentsCapacity, int ownerIndex, int popularityBoost,
			String[] troopNames) {
		super(maxHp, name, neededWorkers, width, height, verticalHeight, isSelectable, x, y, residentsCapacity,
				ownerIndex);
		this.popularityBoost = popularityBoost;
		this.troopNames = troopNames;
	}

	private Barracks(Barracks model, int x, int y, int ownerIndex) {
		super(model, x, y, ownerIndex);
		this.popularityBoost = model.popularityBoost;
		this.troopNames = model.troopNames;
	}

	@Override
	public Building generateCopy(int x, int y, int ownerIndex) {
		return new Barracks(this, x, y, ownerIndex);
	}

	public int getPopularityBoost() {
		return popularityBoost;
	}

	public String[] getTroopNames() {
		return troopNames;
	}
}