import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Grayscale{
	public static BufferedImage Grayscale(BufferedImage source) {
		
		int width = source.getWidth();
		int height = source.getHeight();
	
	//create grayscale image
		BufferedImage gray = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	
	//Writable raster
		WritableRaster raster = gray.getRaster();
	
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				
				int rgb = source.getRGB(x,y);
				
				int r = (rgb >> 16) & 0xff;
				int g = (rgb >> 8) & 0xff;
				int b = rgb & 0xff; 

				int grayVal = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
				
				raster.setSample(x, y, 0, grayVal);
			}
		}
	
	return gray;
	}

}
