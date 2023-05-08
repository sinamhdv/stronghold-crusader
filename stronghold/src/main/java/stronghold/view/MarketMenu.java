package stronghold.view;

import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;

public class MarketMenu {
	public static void showPriceList() {
		Government currnetPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		System.out.println("ITEM     BUYPRICE     SELLPRICE     YOURASSET");
		for (ResourceType resourceType : ResourceType.values()) {
			ResourceType resource = resourceType;
			int asset = currnetPlayer.getSumOfSpecificResource(resource);
			System.out.println(resource.getName() + "     " + resource.getBuyPrice() + "     " + resource.getSellprice()
					+ "     " + asset);
		}
	}
}
