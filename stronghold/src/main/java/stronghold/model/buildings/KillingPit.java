package stronghold.model.buildings;

public class KillingPit extends Building {
	private final int damage;

	public KillingPit(int maxHp, String name, int neededWorkers, int width, int height, int verticalHeight,
			boolean isSelectable, int x, int y, int residents, int residentsCapacity, boolean hasWorkers,
			int ownerIndex, int turnOfBuild, int damage) {
		super(maxHp, name, neededWorkers, width, height, verticalHeight, isSelectable, x, y, residents,
				residentsCapacity, hasWorkers, ownerIndex, turnOfBuild);
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}
}
