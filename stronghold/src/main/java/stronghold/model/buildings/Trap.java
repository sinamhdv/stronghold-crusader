package stronghold.model.buildings;

public class Trap extends Building {
	private final int damage;
	private final boolean hasDogs;	// used for Dogs' cage

	public Trap(int maxHp, String name, int neededWorkers, int width, int height, int verticalHeight,
			boolean isSelectable, int x, int y, int residentsCapacity, int ownerIndex, int damage, boolean hasDogs) {
		super(maxHp, name, neededWorkers, width, height, verticalHeight, isSelectable, x, y, residentsCapacity,
				ownerIndex);
		this.damage = damage;
		this.hasDogs = hasDogs;
	}

	private Trap(Trap model, int x, int y, int ownerIndex) {
		super(model, x, y, ownerIndex);
		this.damage = model.damage;
		this.hasDogs = model.hasDogs;
	}

	@Override
	public Building generateCopy(int x, int y, int ownerIndex) {
		return new Trap(this, x, y, ownerIndex);
	}

	public int getDamage() {
		return damage;
	}
}
