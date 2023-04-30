package stronghold.model.people;

import stronghold.model.Government;

public class Troop extends Person {
	private StanceType stance;
	private boolean canClimbLadder;
	private boolean canDigMoats;

	public Troop(int speed, int hp, int damage, int x, int y, Government owner, String name,
			boolean canClimbLadder, boolean canDigMoats) {
		super(speed, hp, damage, owner, x, y, name);
		this.stance = StanceType.NORMAL;
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
		//TODO
	}
}