package stronghold.model.environment;

public class Wall extends EnvironmentItem {
	private int hp;

	public Wall(int ownerIndex) {
		super(ownerIndex);
		this.hp = 100;	// TODO: move this to the game config?
	}

	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
}
