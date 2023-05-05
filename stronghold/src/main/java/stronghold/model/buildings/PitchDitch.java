package stronghold.model.buildings;

public class PitchDitch extends Building{
	private int fireDamage;

	public PitchDitch(int maxHp, int x, int y, int ownerIndex, String name, int neededWorkers, int terunOfBuild,
			int fireDamage) {
		super(maxHp, x, y, ownerIndex, name, neededWorkers, terunOfBuild);
		this.fireDamage = fireDamage;
	}

	public int getFireDamage() {
		return fireDamage;
	}

	public void setFireDamage(int fireDamage) {
		this.fireDamage = fireDamage;
	}
	
}
