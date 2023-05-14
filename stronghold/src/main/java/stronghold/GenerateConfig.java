package stronghold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import stronghold.model.ResourceType;
import stronghold.model.buildings.Barracks;
import stronghold.model.buildings.Building;
import stronghold.model.buildings.DefensiveStructure;
import stronghold.model.buildings.DefensiveStructureType;
import stronghold.model.buildings.OilSmelter;
import stronghold.model.buildings.ResourceConverterBuilding;
import stronghold.model.buildings.Stockpile;
import stronghold.model.buildings.Trap;
import stronghold.model.map.GroundType;
import stronghold.model.people.Person;
import stronghold.model.people.PersonType;
import stronghold.utils.ConfigManager;

public class GenerateConfig {
	public static void run() {
		generatePeople();
		generateBuildings();
	}

	private static void generatePeople() {
		ArrayList<Person> people = new ArrayList<>();

		people.add(new Person(
			"lord", 2, 400, 80,
			999999, 1, 1,
			false, false, false,
			false, PersonType.LORD, 0, 0, 0,
			100, true, true
		));
		
		people.add(new Person(
			"archer", 4, 50, 20,
			999999, 1, 8,
			true, false, false,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, false
		));

		people.add(new Person(
			"crossbowman", 3, 50, 40,
			999999, 1, 10,
			true, false, false,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, false
		));

		people.add(new Person(
			"swordsman", 2, 200, 80,
			999999, 1, 1,
			false, false, false,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, true
		));

		people.add(new Person(
			"spearman", 4, 50, 20,
			999999, 1, 1,
			true, false, true,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, true
		));

		people.add(new Person(
			"pikeman", 2, 100, 50,
			999999, 1, 1,
			false, false, true,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, true
		));

		people.add(new Person(
			"maceman", 3, 70, 40,
			999999, 1, 1,
			true, false, false,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, true
		));

		people.add(new Person(
			"knight", 6, 180, 80,
			999999, 1, 1,
			false, false, false,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, true
		));

		people.add(new Person(
			"tunneler", 4, 20, 5,
			999999, 1, 1,
			false, false, false,
			false, PersonType.TUNNELER, 0, 0, 0,
			100, true, false
		));

		people.add(new Person(
			"ladderman", 4, 20, 5,
			999999, 1, 1,
			false, false, false,
			false, PersonType.LADDERMAN, 0, 0, 0,
			100, true, false
		));

		people.add(new Person(
			"engineer", 4, 20, 5,
			999999, 1, 1,
			false, false, false,
			false, PersonType.ENGINEER, 0, 0, 0,
			100, true, false
		));

		people.add(new Person(
			"monk", 3, 70, 40,
			999999, 1, 1,
			false, false, false,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, true
		));

		people.add(new Person(
			"arabian archer", 4, 50, 20,
			999999, 1, 8,
			true, false, false,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, false
		));

		people.add(new Person(
			"slave", 4, 50, 20,
			999999, 1, 1,
			true, false, false,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, true
		));

		people.add(new Person(
			"slinger", 4, 50, 20,
			999999, 1, 5,
			true, false, false,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, false
		));

		people.add(new Person(
			"assassin", 4, 100, 40,
			5, 1, 1,
			true, true, false,
			false, PersonType.ASSASSIN, 0, 0, 0,
			100, true, false
		));

		people.add(new Person(
			"horse archer", 6, 100, 30,
			999999, 1, 8,
			false, false, false,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, false
		));

		people.add(new Person(
			"arabian swordsman", 2, 180, 70,
			999999, 1, 1,
			false, false, false,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, true
		));

		people.add(new Person(
			"firethrower", 4, 50, 20,
			999999, 1, 5,
			true, false, false,
			false, PersonType.NORMAL, 0, 0, 0,
			100, true, true
		));

		ConfigManager.savePeopleConfig(people.toArray(new Person[0]));
	}

	private static void generateBuildings() {
		
		addBuilding(new DefensiveStructure(
			1000, "keep", 0,
			3, 3, 1,
			true, 0, 0,
			8, 0,
			10, 7,
			DefensiveStructureType.KEEP, false
		));

		addBuilding(new DefensiveStructure(
			400, "horizontal small gate", 0,
			2, 2, 2,
			true, 0, 0,
			8, 0,
			10, 7,
			DefensiveStructureType.GATE, false
		));

		addBuilding(new DefensiveStructure(
			400, "vertical small gate", 0,
			2, 2, 2,
			true, 0, 0,
			8, 0,
			10, 7,
			DefensiveStructureType.GATE, true
		));

		addBuilding(new DefensiveStructure(
			600, "horizontal large gate", 0,
			3, 3, 2,
			true, 0, 0,
			10, 0,
			12, 6,
			DefensiveStructureType.GATE, false
		));

		addBuilding(new DefensiveStructure(
			600, "vertical large gate", 0,
			3, 3, 2,
			true, 0, 0,
			10, 0,
			12, 6,
			DefensiveStructureType.GATE, true
		));

		addBuilding(new DefensiveStructure(
			200, "draw bridge", 0,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			10, 10,
			DefensiveStructureType.GATE, false
		));

		addBuilding(new DefensiveStructure(
			300, "lookout tower", 0,
			1, 1, 2,
			true, 0, 0,
			0, 0,
			12, 3,
			DefensiveStructureType.TURRET, false
		));

		addBuilding(new DefensiveStructure(
			300, "small turret", 0,
			1, 1, 2,
			true, 0, 0,
			0, 0,
			10, 6,
			DefensiveStructureType.TURRET, false
		));

		addBuilding(new DefensiveStructure(
			400, "large turret", 0,
			2, 2, 2,
			true, 0, 0,
			0, 0,
			10, 6,
			DefensiveStructureType.TURRET, false
		));

		addBuilding(new DefensiveStructure(
			700, "square tower", 0,
			3, 3, 2,
			true, 0, 0,
			0, 0,
			12, 4,
			DefensiveStructureType.TOWER, false
		));

		addBuilding(new DefensiveStructure(
			1000, "round tower", 0,
			3, 3, 2,
			true, 0, 0,
			0, 0,
			12, 4,
			DefensiveStructureType.TOWER, false
		));

		addBuilding(new DefensiveStructure(
			100, "high wall", 0,
			1, 1, 2,
			false, 0, 0,
			0, 0,
			4, 10,
			DefensiveStructureType.WALL, false
		));

		addBuilding(new DefensiveStructure(
			50, "low wall", 0,
			1, 1, 2,
			false, 0, 0,
			0, 0,
			2, 12,
			DefensiveStructureType.WALL, false
		));

		addBuilding(new DefensiveStructure(
			50, "stairs", 0,
			1, 1, 1,
			false, 0, 0,
			0, 0,
			2, 12,
			DefensiveStructureType.STAIRS, false
		));

		addBuilding(new Barracks(
			200, "european barracks", 0,
			2, 2, 999,
			true, 0, 0,
			0, 0,
			0, new String[] {
				"european archer", "swordsman"
			}
		));

		addBuilding(new Barracks(
			200, "arabian barracks", 0,
			2, 2, 999,
			true, 0, 0,
			0, 0,
			0, new String[] {
				"arabian archer", "arabian swordsman"
			}
		));

		addBuilding(new Barracks(
			200, "engineers guild", 0,
			2, 2, 999,
			true, 0, 0,
			0, 0,
			0, new String[] {
				"engineer", "ladderman", "tunneler"
			}
		));

		addBuilding(new Barracks(
			200, "chapel", 0,
			1, 1, 999,
			true, 0, 0,
			2, 0,
			0, new String[] {}
		));

		addBuilding(new Barracks(
			200, "church", 0,
			2, 2, 999,
			true, 0, 0,
			0, 0,
			2, new String[] { "monk" }
		));

		addBuilding(new Trap(
			10, "killing pit", 0,
			1, 1, 0,
			false, 0, 0,
			0, 0,
			9999, false
		));

		addBuilding(new Trap(
			100, "dogs cage", 0,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			0, true
		));

		addBuilding(new Stockpile(
			200, "inn", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			new HashMap<>(Map.of(
				ResourceType.WINE, 0
			)), 20
		));

		addBuilding(new Stockpile(
			200, "armory", 0,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			new HashMap<>(Map.of(
				ResourceType.BOW, 0,
				ResourceType.CROSSBOW, 0,
				ResourceType.SPEAR, 0,
				ResourceType.PIKE, 0,
				ResourceType.MACE, 0,
				ResourceType.SWORDS, 0,
				ResourceType.LEATHER_ARMOR, 0,
				ResourceType.METAL_ARMOR, 0
			)), 40
		));

		addBuilding(new Stockpile(
			200, "granary", 0,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			new HashMap<>(Map.of(
				ResourceType.APPLE, 0,
				ResourceType.CHEESE, 0,
				ResourceType.BREAD, 0,
				ResourceType.MEAT, 0
			)), 50
		));

		addBuilding(new Stockpile(
			200, "stockpile", 0,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			new HashMap<>(Map.of(
				ResourceType.ROCK, 0,
				ResourceType.UNTRANSPORTED_ROCK, 0,
				ResourceType.WOOD, 0,
				ResourceType.OIL, 0,
				ResourceType.IRON, 0,
				ResourceType.GRAPES, 0,
				ResourceType.WHEAT, 0,
				ResourceType.FLOUR, 0
			)), 80
		));

		addBuilding(new Stockpile(
			200, "stable", 0,
			2, 2, 999,
			true, 0, 0,
			0, 0,
			new HashMap<>(Map.of(
				ResourceType.HORSE, 4
			)), 4
		));

		addBuilding(new ResourceConverterBuilding(
			200, "mill", 3,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			ResourceType.WHEAT, ResourceType.FLOUR,
			null,
			2, 2, 1
		));

		addBuilding(new ResourceConverterBuilding(
			200, "iron mine", 2,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			null, ResourceType.IRON,
			new GroundType[] { GroundType.IRON },
			2, 3, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "stone mine", 3,
			2, 2, 999,
			true, 0, 0,
			0, 0,
			null, ResourceType.UNTRANSPORTED_ROCK,
			new GroundType[] { GroundType.STONE },
			2, 8, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "woodcutter", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			null, ResourceType.WOOD,
			null,
			3, 10, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "pitch rig", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			null, ResourceType.OIL,
			new GroundType[] { GroundType.OIL },
			3, 2, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "armorer", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			ResourceType.IRON, ResourceType.METAL_ARMOR,
			null,
			2, 2, 1
		));

		addBuilding(new ResourceConverterBuilding(
			200, "sword blacksmith", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			ResourceType.IRON, ResourceType.SWORDS,
			null,
			2, 2, 1
		));

		addBuilding(new ResourceConverterBuilding(
			200, "mace blacksmith", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			ResourceType.IRON, ResourceType.MACE,
			null,
			2, 2, 1
		));

		addBuilding(new ResourceConverterBuilding(
			200, "tanner", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			null, ResourceType.LEATHER_ARMOR,
			null,
			2, 1, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "spear poleturner", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			ResourceType.WOOD, ResourceType.SPEAR,
			null,
			2, 2, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "pike poleturner", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			ResourceType.WOOD, ResourceType.PIKE,
			null,
			2, 2, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "bow fletcher", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			ResourceType.WOOD, ResourceType.BOW,
			null,
			2, 2, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "crossbow fletcher", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			ResourceType.WOOD, ResourceType.CROSSBOW,
			null,
			2, 2, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "apple farm", 1,
			3, 3, 999,
			true, 0, 0,
			0, 0,
			null, ResourceType.APPLE,
			new GroundType[] { GroundType.HIGH_DENSITY_GRASS, GroundType.NORMAL_GRASS },
			3, 8, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "dairy farm", 1,
			3, 3, 999,
			true, 0, 0,
			0, 0,
			null, ResourceType.CHEESE,
			new GroundType[] { GroundType.HIGH_DENSITY_GRASS, GroundType.NORMAL_GRASS },
			3, 8, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "grape farm", 1,
			3, 3, 999,
			true, 0, 0,
			0, 0,
			null, ResourceType.GRAPES,
			new GroundType[] { GroundType.HIGH_DENSITY_GRASS, GroundType.NORMAL_GRASS },
			3, 4, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "wheat farm", 1,
			3, 3, 999,
			true, 0, 0,
			0, 0,
			null, ResourceType.WHEAT,
			new GroundType[] { GroundType.HIGH_DENSITY_GRASS, GroundType.NORMAL_GRASS },
			3, 4, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "hunter", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			null, ResourceType.MEAT,
			null,
			3, 4, 0
		));

		addBuilding(new ResourceConverterBuilding(
			200, "bakery", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			ResourceType.FLOUR, ResourceType.BREAD,
			null,
			2, 4, 2
		));

		addBuilding(new ResourceConverterBuilding(
			200, "brewery", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			ResourceType.GRAPES, ResourceType.WINE,
			null,
			2, 4, 2
		));

		addBuilding(new ResourceConverterBuilding(
			200, "ox tether", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			ResourceType.UNTRANSPORTED_ROCK, ResourceType.ROCK,
			null,
			3, 8, 8
		));

		addBuilding(new OilSmelter(
			200, "oil smelter", 0,
			0, 0, 0,
			false, 0, 0,
			0, 0
		));

		addBuilding(new Building(
			200, "house", 0,
			1, 1, 999,
			false, 0, 0,
			8, 0
		));

		addBuilding(new Building(
			200, "market", 0,
			1, 1, 999,
			true, 0, 0,
			0, 0
		));
	}

	private static void addBuilding(Building building) {
		ConfigManager.saveBuildingConfig(building);
	}
}
