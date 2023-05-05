package stronghold.model.map;

import stronghold.view.TerminalColor;

public enum GroundType {
	NORMAL(true, TerminalColor.YELLOW, TerminalColor.GRAY, "normal"),
	GRIT(true, TerminalColor.YELLOW, TerminalColor.GRAY, "grit"),

	;

	private final boolean isPassable;
	private final TerminalColor backgroundColor;
	private final TerminalColor foregroundColor;
	private final String name;

	private GroundType(boolean isPassable, TerminalColor backgroundColor, TerminalColor foregroundColor, String name) {
		this.isPassable = isPassable;
		this.backgroundColor = backgroundColor;
		this.foregroundColor = foregroundColor;
		this.name = name;
	}

	public boolean isPassable() {
		return isPassable;
	}
	public String getName() {
		return name;
	}
	public TerminalColor getBackgroundColor() {
		return backgroundColor;
	}
	public TerminalColor getForegroundColor() {
		return foregroundColor;
	}

	public static GroundType getGroundTypeByName(String name) {
		for (GroundType groundType : GroundType.values())
			if (groundType.getName().equals(name))
				return groundType;
		return null;
	}
}
