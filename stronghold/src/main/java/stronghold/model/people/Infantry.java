package stronghold.model.people;

public class Infantry extends Person {

	public Infantry(int speed, int hp, int damage, int ownerIndex, int x, int y, String name) {
		super(speed, hp, damage, ownerIndex, x, y, name);
	}

	public void fight(Person other) {
		//TODO
	}
}
