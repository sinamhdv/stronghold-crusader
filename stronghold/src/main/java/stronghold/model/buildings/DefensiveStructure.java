package stronghold.model.buildings;

import stronghold.model.Government;

public class DefensiveStructure extends Building {
	private int fireRate;
	private int defendRate;
	private DefensiveStructure type;
	private boolean isCaptured;
	public DefensiveStructure(int maxHp, int x, int y, Government owner, String name, int neededWorkers,
			int terunOfBuild, int fireRate, int defendRate, DefensiveStructure type, boolean isCaptured) {
		super(maxHp, x, y, owner, name, neededWorkers, terunOfBuild);
		this.fireRate = fireRate;
		this.defendRate = defendRate;
		this.type = type;
		this.isCaptured = isCaptured;
	}
	public int getFireRate() {
		return fireRate;
	}
	public int getDefendRate() {
		return defendRate;
	}
	public DefensiveStructure getType() {
		return type;
	}
	public boolean isCaptured() {
		return isCaptured;
	}
	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}
	public void setDefendRate(int defendRate) {
		this.defendRate = defendRate;
	}
	public void setType(DefensiveStructure type) {
		this.type = type;
	}
	public void setCaptured(boolean isCaptured) {
		this.isCaptured = isCaptured;
	}
	
}
