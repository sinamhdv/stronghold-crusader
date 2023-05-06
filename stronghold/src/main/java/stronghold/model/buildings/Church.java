package stronghold.model.buildings;

public class Church extends Building {
	private int popularityBost;
	private boolean canTrainMonks;
	public Church(int maxHp, int x, int y, int ownerIndex, String name, int neededWorkers, int terunOfBuild,
			int popularityBost, boolean canTrainMonks) {
		super(maxHp, x, y, ownerIndex, name, neededWorkers, terunOfBuild);
		this.popularityBost = popularityBost;
		this.canTrainMonks = canTrainMonks;
	}
	public int getPopularityBost() {
		return popularityBost;
	}
	public boolean isCanTrainMonks() {
		return canTrainMonks;
	}
	public void setPopularityBost(int popularityBost) {
		this.popularityBost = popularityBost;
	}
	public void setCanTrainMonks(boolean canTrainMonks) {
		this.canTrainMonks = canTrainMonks;
	}
}
