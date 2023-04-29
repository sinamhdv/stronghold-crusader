package stronghold.model.people;

import stronghold.model.Government;

public abstract class Person {
	private int speed;
	private int hp;
	private int damage;
	private final Government owner;
	private int x;
	private int y;
	private final String name;
	
	public Person(int speed, int hp, int damage, Government owner, int x, int y, String name) {
		this.speed = speed;
		this.hp = hp;
		this.damage = damage;
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public Government getOwner() {
		return owner;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getName() {
		return name;
	}

	public void move(int targetX, int targetY) {
		// TODO: run BFS on the map
	}

	public void disband() {
		// TODO
	}
}
