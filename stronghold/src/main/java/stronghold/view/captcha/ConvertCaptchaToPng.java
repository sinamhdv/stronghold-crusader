package stronghold.view.captcha;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ConvertCaptchaToPng {
	public static void Converter(String[] asciiart) throws IOException {
		BufferedImage bufferedImage = ImageIO
				.read(new File("C:\\Users\\Asus\\Desktop\\project-workspace\\Phase1\\captcha.png"));
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