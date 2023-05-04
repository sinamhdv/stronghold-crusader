package stronghold.model.people;

import stronghold.model.Government;

public class Infantry extends Person {

	public Infantry(int speed, int hp, int damage, Government owner, int x, int y, String name) {
		super(speed, hp, damage, owner, x, y, name);
	}

	public void fight(Person other) {
		//TODO
	}
}
