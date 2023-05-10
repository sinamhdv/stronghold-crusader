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

	private String typeName;

	public Tree(String typeName) {
		super(-1);
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	@Override
	public String toString() {
		return "Tree (type = " + typeName + ")";
	}
}
