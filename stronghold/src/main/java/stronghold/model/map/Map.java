package stronghold.model.map;

public class Map {
	private final String mapName;
	private final int governmentsCount;
	private final int width;
	private final int height;
	private final MapTile[][] grid;
	
	public Map(String mapName, int governmentsCount, int width, int height) {
		this.mapName = mapName;
		this.governmentsCount = governmentsCount;
		this.width = width;
		this.height = height;
		this.grid = new MapTile[height][width];
	}

	public String getMapName() {
		return mapName;
	}
	public int getGovernmentsCount() {
		return governmentsCount;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public MapTile[][] getGrid() {
		return grid;
	}
}
