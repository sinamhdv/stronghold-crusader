package stronghold.model.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import stronghold.model.StrongHold;
import stronghold.model.people.Person;
import stronghold.model.people.PersonType;

public class Pathfinding {
	private static final int[][] neighbors = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
	private static final ArrayList<Path> cache = new ArrayList<>();
	private static Map map;

	public static void clearCache() {
		cache.clear();
	}

	public static Path findPath(Person agent) {
		map = StrongHold.getCurrentGame().getMap();
		Path precomputedPath = searchCache(agent);
		if (precomputedPath != null) return precomputedPath;
		int[][] distance = BFS(agent);
		Path path = extractPath(agent, distance);
		cache.add(path);
		return path;
	}

	private static Path searchCache(Person agent) {
		for (Path path : cache)
			if (path.matchesAgent(agent))
				return path;
		return null;
	}

	private static int[][] BFS(Person agent) {
		Queue<Integer> queueX = new LinkedList<>();
		Queue<Integer> queueY = new LinkedList<>();
		int[][] distance = new int[map.getHeight()][map.getWidth()];
		int sourceX = agent.getX();
		int sourceY = agent.getY();
		queueX.add(sourceX);
		queueY.add(sourceY);
		distance[sourceX][sourceY] = 1;

		while (!queueX.isEmpty()) {
			int x = queueX.remove();
			int y = queueY.remove();
			for (int[] delta : neighbors) {
				int ux = x + delta[0];
				int uy = y + delta[1];
				if (ux < 0 || uy < 0 || ux >= map.getHeight() || uy >= map.getWidth() || distance[ux][uy] > 0)
					continue;
				if (!canPassEdge(agent, map.getGrid()[x][y], map.getGrid()[ux][uy]))
					continue;
				distance[ux][uy] = distance[x][y] + 1;
				queueX.add(ux);
				queueY.add(uy);
			}
		}
		return distance;
	}

	private static Path extractPath(Person agent, int[][] distance) {
		ArrayList<Integer> cellsX = new ArrayList<>();
		ArrayList<Integer> cellsY = new ArrayList<>();
		int x = agent.getDestX();
		int y = agent.getDestY();
		cellsX.add(x);
		cellsY.add(y);
		while (distance[x][y] != 0 && (x != agent.getX() || y != agent.getY())) {
			int[] parent = getParent(x, y, distance);
			x = parent[0];
			y = parent[1];
			cellsX.add(x);
			cellsY.add(y);
		}
		Collections.reverse(cellsX);
		Collections.reverse(cellsY);
		int savedLength = Math.min(cellsX.size(), agent.getSpeed() + 1);
		int[][] cells = new int[savedLength][2];
		for (int i = 0; i < savedLength; i++) {
			cells[i][0] = cellsX.get(i);
			cells[i][1] = cellsY.get(i);
		}
		return new Path(agent, new int[] {agent.getDestX(), agent.getDestY()}, cells);
	}

	private static int[] getParent(int x, int y, int[][] distance) {
		for (int[] delta : neighbors) {
			int ux = x + delta[0];
			int uy = y = delta[1];
			if (ux < 0 || uy < 0 || ux >= distance.length || uy >= distance[0].length) continue;
			if (distance[ux][uy] == distance[x][y] - 1) return new int[] {ux, uy};
		}
		return null;
	}

	private static boolean canPassEdge(Person agent, MapTile source, MapTile destination) {
		if (!destination.isPassable()) return false;
		int deltaHeight = Math.abs(source.getHeight() - destination.getHeight());
		if (deltaHeight > 2) return false;
		if (agent.canClimbWalls() || deltaHeight <= 1) return true;
		MapTile ladderPlace = (source.getHeight() < destination.getHeight() ? source : destination);
		for (Person person : ladderPlace.getPeople())
			if (person.getOwnerIndex() == agent.getOwnerIndex() && person.getType() == PersonType.LADDERMAN)
				return true;
		return false;
	}
}
