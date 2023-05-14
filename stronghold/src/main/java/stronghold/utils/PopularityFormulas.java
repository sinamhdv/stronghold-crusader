package stronghold.utils;

public class PopularityFormulas {
	public static int foodRate2Popularity(int foodRate) {
		return foodRate * 4;
	}

	public static int foodRate2FoodAmountx2(int foodRate) {
		return foodRate + 2;
	}

	public static int taxRate2Popularity(int taxRate) {
		if (taxRate <= 0) return 2 * (-taxRate) + 1;
		if (taxRate <= 4) return -2 * taxRate;
		return -(8 + 4 * (taxRate - 4));
	}

	public static int taxRate2Moneyx5(int taxRate) {
		if (taxRate < 0) return taxRate - 2;
		if (taxRate == 0) return 0;
		return taxRate + 2;
	}

	public static int combineDamageAndFear(int damage, int fear) {
		return damage - (5 * fear * damage) / 100;
	}
}
