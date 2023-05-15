package stronghold.model.people;

public enum StanceType {
	STANDING(0),
	DEFENSIVE(5),
	OFFENSIVE(20);
	
	private int radiusOfMovement;

	private StanceType(int radiusOfMovement) {
		this.radiusOfMovement = radiusOfMovement;
	}

	public int getRadiusOfMovement() {
		return radiusOfMovement;
	}
	
}
