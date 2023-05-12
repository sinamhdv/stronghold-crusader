package stronghold.model.map;

import java.util.Arrays;

import stronghold.model.people.Person;

public class Path {
	private final Person sampleAgent;
	private final int[] finalDestination;
	private final int[][] cells;

	public Path(Person sampleAgent, int[] finalDestination, int[][] cells) {
		this.sampleAgent = sampleAgent;
		this.finalDestination = finalDestination;
		this.cells = cells;
	}

	boolean matchesAgent(Person agent) {
		return (
			agent.canClimbLadder() == sampleAgent.canClimbLadder() &&
			agent.canClimbWalls() == sampleAgent.canClimbWalls() &&
			agent.getOwnerIndex() == sampleAgent.getOwnerIndex() &&
			agent.getSpeed() == sampleAgent.getSpeed() &&
			Arrays.equals(this.finalDestination, new int[] {agent.getDestX(), agent.getDestY()}) &&
			Arrays.equals(this.cells[0], new int[] {agent.getX(), agent.getY()})
		);
	}

	public int[][] getCells() {
		return cells;
	}
}
