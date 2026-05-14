import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

public class Noise_reduction {

    public static BufferedImage medianFilter(BufferedImage gray) {

        int width = gray.getWidth();
        int height = gray.getHeight();

        BufferedImage output = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_BYTE_GRAY
        );

        WritableRaster input = gray.getRaster();
        WritableRaster out = output.getRaster();

        int[] window = new int[9];

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {

                int k = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        window[k++] = input.getSample(x+i, y+j, 0);
                    }
                }

                Arrays.sort(window);
                out.setSample(x, y, 0, window[4]); // median
            }
        }

        return output;
    }
}
