package stronghold.model.environment;

import java.io.Serializable;

import stronghold.model.Government;
import stronghold.model.StrongHold;

public abstract class EnvironmentItem implements Serializable {
	private final int ownerIndex;

	public EnvironmentItem(int ownerIndex) {
		this.ownerIndex = ownerIndex;
	}

	public Government getOwner() {
		return (ownerIndex == -1 ? null : StrongHold.getCurrentGame().getGovernments()[ownerIndex]);
	}
	public int getOwnerIndex() {
		return ownerIndex;
	}

	public abstract String getName();
}
