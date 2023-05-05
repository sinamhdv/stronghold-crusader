package stronghold.model.environment;

public class Rock extends EnvironmentItem {
	private final char direction;

	public Rock(char direction) {
		super(null);
		this.direction = direction;
	}

	public char getDirection() {
		return direction;
	}
}
