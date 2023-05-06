package stronghold.model.people;

public class Archer extends Troop {
	private int firingRange;
	private final int fireRate;

	public Archer(int speed, int hp, int damage, int ownerIndex, int x, int y, String name, boolean canClimbLadder,
			boolean canDigMoats, int firingRange, int fireRate) {
		super(speed, hp, damage, ownerIndex, x, y, name, canClimbLadder, canDigMoats);
		this.firingRange = firingRange;
		this.fireRate = fireRate;
	}

	public int getFiringRange() {
		return firingRange;
	}
	public int getFireRate() {
		return fireRate;
	}
	public void setFiringRange(int firingRange) {
		this.firingRange = firingRange;
	}
	public void fight (Person other) {
		//TODO 
	}
}
