package stronghold.model.buildings;

public class KillingPit extends Building {
	private final int damage;

	public KillingPit(int maxHp, String name, int neededWorkers, int width, int height, int verticalHeight,
			boolean isSelectable, int x, int y, int residentsCapacity, int ownerIndex, int damage) {
		super(maxHp, name, neededWorkers, width, height, verticalHeight, isSelectable, x, y, residentsCapacity,
				ownerIndex);
		this.damage = damage;
	}

	private KillingPit(KillingPit model, int x, int y, int ownerIndex) {
		super(model, x, y, ownerIndex);
		this.damage = model.damage;
	}

	@Override
	public Building generateCopy(int x, int y, int ownerIndex) {
		return new KillingPit(this, x, y, ownerIndex);
	}

	public int getDamage() {
		return damage;
	}
}
