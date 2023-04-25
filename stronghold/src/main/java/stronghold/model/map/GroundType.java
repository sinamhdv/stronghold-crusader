package stronghold.model.map;

import stronghold.view.TerminalColor;

public enum GroundType {
	NORMAL(true, TerminalColor.BROWN),
	;

	private final boolean isPassable;
	private final TerminalColor color;

	private GroundType(boolean isPassable, TerminalColor color) {
		this.isPassable = isPassable;
		this.color = color;
	}

	public TerminalColor getColor() {
		return color;
	}
	public boolean isPassable() {
		return isPassable;
	}
}
