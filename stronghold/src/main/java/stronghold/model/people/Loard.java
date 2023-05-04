package stronghold.model.people;

import stronghold.model.Government;

public class Loard extends Infantry {
	public Loard (int speed, int hp, int damage, Government owner, int x, int y, String name) {
		super(speed, hp, damage, owner, x, y, name);
	}
}
