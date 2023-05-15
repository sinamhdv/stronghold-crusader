package stronghold.model;

public enum ResourceType {
	WINE(10, 8, "Wine", true),
	MEAT(20, 15, "Meat", true),
	APPLE(8, 5, "Apple", true),
	CHEESE(8, 5, "Cheese", true),
	BREAD(9, 7, "Bread", true),
	GRAPES(15, 10, "Grapes", true),
	WHEAT(5, 3, "Wheat", true),
	FLOUR(5, 3, "Flour", true),
	WOOD(20, 15, "Wood", true),
	ROCK(25, 18, "Rock", true),
	UNTRANSPORTED_ROCK(0, 0, "Untransported Rock", false),
	BOW(30, 25, "Bow", true),
	CROSSBOW(35, 28, "Crossbow", true),
	SPEAR(30, 25, "Spear", true),
	PIKE(30,20, "Pike", true),
	MACE(35, 25, "Mace", true),
	SWORD(40, 30, "Sword", true),
	LEATHER_ARMOR(30, 25, "Leather Armor", true),
	METAL_ARMOR(35, 27, "Metal Armor", true),
	IRON(15, 10, "Iron", true),
	OIL(15, 10, "Oil", true),
	HORSE(0, 0, "Horse", false),
	GOLD(0, 0, "Gold", false),
	POPULATION(0, 0, "Population", false),
	;
	
	public static final ResourceType[] foodTypes = new ResourceType[] {
		ResourceType.APPLE,
		ResourceType.CHEESE,
		ResourceType.MEAT,
		ResourceType.BREAD
	};

	private final boolean isTradable;
	private final int buyPrice;
	private final int sellprice;
	private final String name;

	private ResourceType(int buyPrice, int sellprice, String name, boolean isTradable) {
		this.buyPrice = buyPrice;
		this.sellprice = sellprice;
		this.name = name;
		this.isTradable = isTradable;
	}

	public int getBuyPrice() {
		return buyPrice;
	}
	public int getSellprice() {
		return sellprice;
	}
	public String getName() {
		return name;
	}
	public boolean isTradable() {
		return isTradable;
	}

	public static ResourceType getResourceByName(String name) {
		for(ResourceType resourceType : ResourceType.values()) {
			if(resourceType.getName().equals(name))
				return resourceType;
		}
		return null;
	}
}
