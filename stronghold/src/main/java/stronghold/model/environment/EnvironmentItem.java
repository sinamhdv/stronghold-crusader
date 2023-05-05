package stronghold.model.environment;

import java.io.Serializable;

import stronghold.model.Government;

public abstract class EnvironmentItem implements Serializable {
	private final Government owner;

	public EnvironmentItem(Government owner) {
		this.owner = owner;
	}

	public Government getOwner() {
		return owner;
	}
}
