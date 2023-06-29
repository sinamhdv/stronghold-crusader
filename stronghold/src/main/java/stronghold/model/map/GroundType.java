package stronghold.model.map;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public enum GroundType {
	NORMAL(true, Color.YELLOW, "normal"),
	GRIT(true, Color.YELLOW, "grit"),
	STONE(true, Color.GRAY, "stone"),
	ROCKY(false, Color.GRAY, "rocky"),
	IRON(true, Color.RED, "iron"),
	NORMAL_GRASS(true, Color.GREEN, "normal grass"),
	LOW_DENSITY_GRASS(true, Color.GREEN, "low density grass"),
	HIGH_DENSITY_GRASS(true, Color.GREEN, "high density grass"),
	OIL(true, Color.YELLOW, "oil"),
	SWAMP(false, Color.PURPLE, "swamp"),
	SHALLOW_WATER(true, Color.CYAN, "shallow water"),
	RIVER(false, Color.BLUE, "river"),
	SMALL_POND(false, Color.BLUE, "small pond"),
	LARGE_POND(false, Color.BLUE, "large pond"),
	COAST(true, Color.YELLOW, "coast"),
	SEA(false, Color.BLUE, "sea");

	private final boolean isPassable;
	private transient final Color color;
	private final String name;
	private transient final Image image;

	private GroundType(boolean isPassable, Color color, String name) {
		this.isPassable = isPassable;
		this.color = color;
		this.name = name;
		this.image = new Image(getImagePath());
	}

	public boolean isPassable() {
		return isPassable;
	}
	public String getName() {
		return name;
	}
	public Color getColor() {
		return color;
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

	public Image getImage() {
		return image;
	}

	public String getImagePath() {
		return GroundType.class.getResource("/images/groundtypes/" + name + ".jpg").toExternalForm();
	}
}
