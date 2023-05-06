package stronghold.model.people;

public class Troop extends Person {
	private StanceType stance = StanceType.NORMAL;
	private boolean canClimbLadder;
	private boolean canDigMoats;

	public Troop(int speed, int hp, int damage, int ownerIndex, int x, int y, String name, boolean canClimbLadder,
			boolean canDigMoats) {
		super(speed, hp, damage, ownerIndex, x, y, name);
		this.canClimbLadder = canClimbLadder;
		this.canDigMoats = canDigMoats;
	}

	public StanceType getStance() {
		return stance;
	}

	public boolean isCanClimbLadder() {
		return canClimbLadder;
	}

	public boolean isCanDigMoats() {
		return canDigMoats;
	}

	public void fight(Person other) {
		//TODO: to be implemented
	}
}