package stronghold.model.environment;

import java.util.ArrayList;
import java.util.Arrays;

public class Tree extends EnvironmentItem {
	public static final ArrayList<String> TREE_NAMES = new ArrayList<>(Arrays.asList(
		"desert tree",
		"cherry tree",
		"olive tree",
		"coconut tree",
		"date tree"
	));

	private String name;

	public Tree(String typeName) {
		super(-1);
		this.name = typeName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Tree (type = " + name + ")";
	}
}
