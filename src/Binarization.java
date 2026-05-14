import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Binarization {
	public static BufferedImage binarizeOtsu(BufferedImage edgeImg) {

	    int w = edgeImg.getWidth();
	    int h = edgeImg.getHeight();

	    int[] hist = new int[256];
	    WritableRaster r = edgeImg.getRaster();

	    // Build histogram
	    for (int y = 0; y < h; y++) {
	        for (int x = 0; x < w; x++) {
	            hist[r.getSample(x, y, 0)]++;
	        }
	    }

	    int total = w * h;
	    float sum = 0;
	    for (int i = 0; i < 256; i++) sum += i * hist[i];

	    float sumB = 0;
	    int wB = 0;
	    int wF;
	    float maxVar = 0;
	    int threshold = 0;

	    for (int t = 0; t < 256; t++) {
	        wB += hist[t];
	        if (wB == 0) continue;

	        wF = total - wB;
	        if (wF == 0) break;

	        sumB += t * hist[t];
	        float mB = sumB / wB;
	        float mF = (sum - sumB) / wF;

	        float varBetween = (float) wB * wF * (mB - mF) * (mB - mF);
	        if (varBetween > maxVar) {
	            maxVar = varBetween;
	            threshold = t;
	        }
	    }

	    // Apply threshold
	    BufferedImage binary = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
	    WritableRaster out = binary.getRaster();

	    for (int y = 0; y < h; y++) {
	        for (int x = 0; x < w; x++) {
	            int val = r.getSample(x, y, 0);
	            out.setSample(x, y, 0, val > threshold ? 255 : 0);
	        }
	    }

	    return binary;
	}


}
