package stronghold.model.buildings;

import stronghold.model.Government;

public class PitchDitch extends Building{
	private int fireDamage;

	public PitchDitch(int maxHp, int x, int y, Government owner, String name, int neededWorkers, int terunOfBuild,
			int fireDamage) {
		super(maxHp, x, y, owner, name, neededWorkers, terunOfBuild);
		this.fireDamage = fireDamage;
	}

	public int getFireDamage() {
		return fireDamage;
	}

	public void setFireDamage(int fireDamage) {
		this.fireDamage = fireDamage;
	}
	
}
