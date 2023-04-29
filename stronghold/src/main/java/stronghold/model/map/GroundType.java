package stronghold.model.map;

import stronghold.view.TerminalColor;

public enum GroundType {
	NORMAL(true, TerminalColor.BROWN, "normal"),
	;

	private final boolean isPassable;
	private final TerminalColor color;
	private final String name;

	private GroundType(boolean isPassable, TerminalColor color, String name) {
		this.isPassable = isPassable;
		this.color = color;
		this.name = name;
	}

	public TerminalColor getColor() {
		return color;
	}
	public boolean isPassable() {
		return isPassable;
	}
	public String getName() {
		return name;
	}
}
