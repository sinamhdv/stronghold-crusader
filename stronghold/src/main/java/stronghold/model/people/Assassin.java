package stronghold.model.people;

public class Assassin extends Infantry {
	private int visibllityRange;

	public Assassin(int speed, int hp, int damage, int ownerIndex, int x, int y, String name, int visibllityRange) {
		super(speed, hp, damage, ownerIndex, x, y, name);
		this.visibllityRange = visibllityRange;
	}

	public void move(int targetX, int targetY) {
		//TODO
	}

	public int getVisibllityRange() {
		return visibllityRange;
	}
}
