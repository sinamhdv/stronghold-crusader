package stronghold.model.people;

import stronghold.model.Government;

public class Archer extends Troop {
	private int firingRange;
	private final int fireRate;

	public Archer(int speed, int hp, int damage, int x, int y, Government owner, String name,
			boolean canClimbLadder, boolean canDigMoats, int firingRange, int fireRate) {
		super(speed, hp, damage, x, y, owner, name, canClimbLadder, canDigMoats);
		this.fireRate = fireRate;
		this.firingRange = firingRange;
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
