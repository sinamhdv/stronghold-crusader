package stronghold.model.people;

public class Engineer extends Person {
	private boolean hasBurningOil;

	public Engineer(int speed, int hp, int damage, int ownerIndex, int x, int y, String name, boolean hasBurningOil) {
		super(speed, hp, damage, ownerIndex, x, y, name);
		this.hasBurningOil = hasBurningOil;
	}

	public boolean isHasBurningOil() {
		return hasBurningOil;
	}
}