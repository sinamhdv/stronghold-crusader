package stronghold.model.environment;

public class Rock extends EnvironmentItem {
	private final char direction;

	public Rock(char direction) {
		super(-1);
		this.direction = direction;
	}

	public char getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return "Rock (direction = " + direction + ")";
	}
}
