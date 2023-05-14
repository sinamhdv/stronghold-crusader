package stronghold.model.map;

import stronghold.view.TerminalColor;

public enum GroundType {
	NORMAL(true, TerminalColor.YELLOW, TerminalColor.BLACK, "normal"),
	GRIT(true, TerminalColor.YELLOW, TerminalColor.BLACK, "grit"),
	STONE(true, TerminalColor.GRAY, TerminalColor.BLACK, "stone"),
	ROCKY(false, TerminalColor.GRAY, TerminalColor.BLACK, "rocky"),
	IRON(true, TerminalColor.RED, TerminalColor.GRAY, "iron"),
	NORMAL_GRASS(true, TerminalColor.GREEN, TerminalColor.GRAY, "normal grass"),
	LOW_DENSITY_GRASS(true, TerminalColor.GREEN, TerminalColor.GRAY, "low density grass"),
	HIGH_DENSITY_GRASS(true, TerminalColor.GREEN, TerminalColor.GRAY, "high density grass"),
	OIL(true, TerminalColor.YELLOW, TerminalColor.BLACK, "oil"),
	SWAMP(false, TerminalColor.PURPLE, TerminalColor.GRAY, "swamp"),
	SHALLOW_WATER(true, TerminalColor.CYAN, TerminalColor.BLACK, "shallow water"),
	RIVER(false, TerminalColor.BLUE, TerminalColor.GRAY, "river"),
	SMALL_POND(false, TerminalColor.BLUE, TerminalColor.GRAY, "small pond"),
	LARGE_POND(false, TerminalColor.BLUE, TerminalColor.GRAY, "large pond"),
	COAST(true, TerminalColor.YELLOW, TerminalColor.BLACK, "coast"),
	SEA(false, TerminalColor.BLUE, TerminalColor.GRAY, "sea"),
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
	public boolean isBuildable() {
		return (isPassable && this != GroundType.SHALLOW_WATER);
	}

	public static GroundType getGroundTypeByName(String name) {
		for (GroundType groundType : GroundType.values())
			if (groundType.getName().equals(name))
				return groundType;
		return null;
	}
}
