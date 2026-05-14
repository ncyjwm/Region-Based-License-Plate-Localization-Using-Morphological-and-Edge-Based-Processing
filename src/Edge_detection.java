import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Edge_detection {
	public static BufferedImage Edge_detection(BufferedImage output) {
		
		int width = output.getWidth();
		int height = output.getHeight();
		
		int[][] gx = {
				{-1,0,1},
				{-2,0,2},
				{-1,0,1}
		};
		
		int[][] gy = {
				{1,2,1},
				{0,0,0},
				{-1,-2,-1}
		};
		
		BufferedImage edgeImg = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
		double maxGradient = 0;
		
		double[][] gradient = new double[width][height];
		
		//compute gradient
		for(int y = 1; y < height -1; y++) {
			for(int x = 1; x < width - 1; x++) {
				
				int sumX = 0;
				int sumY = 0;
				
				for(int ky = -1; ky <= 1; ky++) {
					for(int kx = -1; kx <= 1; kx++) {
						int pixel = output.getRGB(x + kx, y + ky) & 0xff;
						sumX += pixel * gx[ky + 1][kx + 1];
						sumY += pixel * gy[ky + 1][kx + 1];
					}
				}
				
				double magnitude = Math.sqrt(sumX * sumX + sumY * sumY);
				gradient[x][y] = magnitude;
				
				if(magnitude > maxGradient) {
					maxGradient = magnitude;
				}
			}
		}
		
		for(int y = 1; y < height-1 ;y++) {
			for(int x = 1; x < width-1 ;x++) {
				int value = (int)Math.min(255, gradient[x][y]);

				value = Math.min(255,  Math.max(0,value));
				
				int grayRGB = (0xff << 24) | (value << 16) | (value << 8) | value;
	            edgeImg.setRGB(x, y, grayRGB);
			}
		}
		
		return edgeImg;
	}

}
