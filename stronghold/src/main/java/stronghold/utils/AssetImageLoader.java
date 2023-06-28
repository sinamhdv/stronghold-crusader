package stronghold.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;

import javafx.scene.image.Image;

public class AssetImageLoader {
	private static HashMap<String, Image> map;

	static {
		try {
			File directory = new File(AssetImageLoader.class.getResource("/images/mapobjects").toURI());
			for (String name : directory.list()) {
				System.out.println(name);
			}
		} catch (URISyntaxException ex) {
			ex.printStackTrace();
		}
	}

	public static Image getAssetImage(String name) {
		return map.get(name);
	}
}
