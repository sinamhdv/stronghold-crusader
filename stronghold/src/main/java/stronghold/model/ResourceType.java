package stronghold.model;

public enum ResourceType {
	WINE(10, 8, "Wine"),
	MEAT(20, 15, "Meat"),
	APPLE(8, 5, "Apple"),
	CHEESE(8, 5, "Cheese"),
	BREAD(9, 7, "Bread"),
	GRAPES(15, 10, "Grapes"),
	WHEAT(5, 3, "Wheat"),
	WOOD(20, 15, "Wood"),
	ROCK(25, 18, "Rock"),
	BOW(30, 25, "Bow"),
	CROSBOW(35, 28, "Crosbow"),
	SPEAR(30, 25, "Spear"),
	PIKE(30,20, "Pike"),
	MACE(35, 25, "Mace"),
	SWORDS(40, 30, "Swords"),
	LEATHER_ARMOR(30, 25, "Leather Armor"),
	METAL_ARMOR(35, 27, "Metal Armor"),
	IRON(15, 10, "Iron"),
	OIL(15, 10, "Oil");
	private int buyPrice;
	private int sellprice;
	private String name;
	private ResourceType(int buyPrice, int sellprice, String name) {
		this.buyPrice = buyPrice;
		this.sellprice = sellprice;
		this.name = name;
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
	public static ResourceType getResourceByName(String name) {
		for(ResourceType resourceType : ResourceType.values()) {
			if(resourceType.getName().equals(name))
				return resourceType;
		}
		return null;
	}

}
