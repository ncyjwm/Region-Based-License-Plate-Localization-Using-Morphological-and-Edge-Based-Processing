import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Detect_number_plate {

    public static void processImage(File imageFile) {
        try {
            BufferedImage input = ImageIO.read(imageFile);

            long startTime = System.nanoTime();

            BufferedImage gray = Grayscale.Grayscale(input);
            BufferedImage median = Noise_reduction.medianFilter(gray);
            BufferedImage edges = Edge_detection.Edge_detection(median);
            BufferedImage binary = Binarization.binarizeOtsu(edges);
            BufferedImage closed = Morphology.closing(binary);
            BufferedImage plateDetected =
                    PlateLocalization.detectPlate(closed, input);

            long endTime = System.nanoTime();
            double elapsedTimeMs = (endTime - startTime) / 1_000_000.0;

            ImageViewer.show(gray, "Grayscale");
            ImageViewer.show(median, "Median Filter");
            ImageViewer.show(edges, "Sobel Edges");
            ImageViewer.show(binary, "Binary Image");
            ImageViewer.show(closed, "Morphological Closing");
            ImageViewer.show(plateDetected, "Detected Plate");

            System.out.println("Processing time: " + elapsedTimeMs + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ANPR_GUI();
    }
}
