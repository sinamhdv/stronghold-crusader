package stronghold.model.map;

import java.io.Serializable;

public class Map implements Serializable {
	private final String name;
	private final int governmentsCount;
	private final int width;
	private final int height;
	private final MapTile[][] grid;
	
	public Map(String name, int governmentsCount, int width, int height) {
		this.name = name;
		this.governmentsCount = governmentsCount;
		this.width = width;
		this.height = height;
		this.grid = new MapTile[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				this.grid[i][j] = new MapTile();
	}

	public String getName() {
		return name;
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
