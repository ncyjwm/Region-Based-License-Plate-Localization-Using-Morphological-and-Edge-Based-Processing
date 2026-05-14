import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Morphology {

    public static BufferedImage dilate(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);

        WritableRaster inR = img.getRaster();
        WritableRaster outR = out.getRaster();

        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {
                int max = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        max = Math.max(max, inR.getSample(x+i, y+j, 0));
                    }
                }
                outR.setSample(x, y, 0, max);
            }
        }
        return out;
    }

    public static BufferedImage erode(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);

        WritableRaster inR = img.getRaster();
        WritableRaster outR = out.getRaster();

        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {
                int min = 255;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        min = Math.min(min, inR.getSample(x+i, y+j, 0));
                    }
                }
                outR.setSample(x, y, 0, min);
            }
        }
        return out;
    }

    // Closing = dilation followed by erosion
    public static BufferedImage closing(BufferedImage img) {
        return erode(dilate(img));
    }
}
