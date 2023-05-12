package stronghold.model;

public enum ResourceType {
	WINE(10, 8, "Wine", false),
	MEAT(20, 15, "Meat", false),
	APPLE(8, 5, "Apple", false),
	CHEESE(8, 5, "Cheese", false),
	BREAD(9, 7, "Bread", false),
	GRAPES(15, 10, "Grapes", false),
	WHEAT(5, 3, "Wheat", false),
	WOOD(20, 15, "Wood", false),
	ROCK(25, 18, "Rock", false),
	BOW(30, 25, "Bow", false),
	CROSBOW(35, 28, "Crosbow", false),
	SPEAR(30, 25, "Spear", false),
	PIKE(30,20, "Pike", false),
	MACE(35, 25, "Mace", false),
	SWORDS(40, 30, "Swords", false),
	LEATHER_ARMOR(30, 25, "Leather Armor", false),
	METAL_ARMOR(35, 27, "Metal Armor", false),
	IRON(15, 10, "Iron", false),
	OIL(15, 10, "Oil", false),
	GOLD(0, 0, "Gold", true),
	;
	
	// TODO: modify Market and Trade to use only resources that have isTradable == true

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
