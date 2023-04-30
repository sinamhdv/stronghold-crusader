package stronghold.model.environment;

import stronghold.model.Government;

public abstract class EnvironmentItem {
	private final Government owner;

	public EnvironmentItem(Government owner) {
		this.owner = owner;
	}

	public Government getOwner() {
		return owner;
	}
}
