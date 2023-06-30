package stronghold.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;

import javafx.scene.image.Image;

public class AssetImageLoader {
	private static HashMap<String, Image> map = new HashMap<>();

	static {
		try {
			File directory = new File(AssetImageLoader.class.getResource("/images/mapobjects").toURI());
			for (String name : directory.list()) {
				map.put(name.split("\\.")[0],
					new Image(AssetImageLoader.class.getResource("/images/mapobjects/" + name).toExternalForm()));
			}
		} catch (URISyntaxException ex) {
			ex.printStackTrace();
		}
	}

	public static Image getAssetImage(String name) {
		return map.get(name);
	}
}
