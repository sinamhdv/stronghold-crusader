package stronghold.view.captcha;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ConvertCaptchaToPng {
	private static final String CAPTCHA_IMAGE_FILENAME = "stronghold/src/main/config/captcha.png";

	public static void converter(String[] asciiart) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO
				.read(new File(CAPTCHA_IMAGE_FILENAME));
		}
		catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("FATAL: IOException while converting captcha to png");
			System.exit(1);
		}
		Image image = bufferedImage.getScaledInstance(40, 6, Image.SCALE_DEFAULT);
		WritableRaster raster = ((BufferedImage) image).getRaster();
		int[] rgb = new int[3];
		for(int i = 0; i<6; i++) {
			for(int j=0; j<40; j++) {
				if(asciiart[i].charAt(j) == ' ') {
					rgb[0] = 0;
					rgb[1] = 102;
					rgb[2] = 102;
					raster.setPixel(i, j, rgb);
				}
				rgb[0] = 0;
				rgb[1] = 204;
				rgb[2] = 204;
				raster.setPixel(i, j, rgb);
			}
		}
	}
}