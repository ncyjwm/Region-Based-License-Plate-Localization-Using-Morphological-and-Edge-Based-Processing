import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Queue;

public class PlateLocalization {

    static class Box {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;

        int area() {
            return (maxX - minX + 1) * (maxY - minY + 1);
        }

        double aspectRatio() {
            return (double) (maxX - minX + 1) / (maxY - minY + 1);
        }
    }

    public static BufferedImage detectPlate(
            BufferedImage binary,
            BufferedImage originalColor
    ) {

        int w = binary.getWidth();
        int h = binary.getHeight();
        boolean[][] visited = new boolean[w][h];

        Box best = null;

        // Region-Based Segmentation (Connected Regions)
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                if ((binary.getRGB(x, y) & 0xff) == 255 && !visited[x][y]) {

                    Box box = new Box();
                    floodFill(binary, visited, x, y, box);

                    double ar = box.aspectRatio();
                    int area = box.area();

                    // Shape-based geometric filtering for license plate candidates
                    if (ar > 2.0 && ar < 6.0 && area > 2000 && area < 30000) {
                        if (best == null || area > best.area()) {
                            best = box;
                        }
                    }
                }
            }
        }

        // Visualization on Morphological (Binary) Image
        BufferedImage binaryBox = new BufferedImage(
                w, h, BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g1 = binaryBox.createGraphics();
        g1.drawImage(binary, 0, 0, null);

        if (best != null) {
            g1.setColor(Color.RED);
            g1.setStroke(new BasicStroke(2));
            g1.drawRect(
                    best.minX,
                    best.minY,
                    best.maxX - best.minX,
                    best.maxY - best.minY
            );
        }
        g1.dispose();

        ImageViewer.show(binaryBox, "Plate Detection on Morphological Image");

        // Visualization on Original Color Image
        BufferedImage colorBox = new BufferedImage(
                originalColor.getWidth(),
                originalColor.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g2 = colorBox.createGraphics();
        g2.drawImage(originalColor, 0, 0, null);

        if (best != null) {
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(
                    best.minX,
                    best.minY,
                    best.maxX - best.minX,
                    best.maxY - best.minY
            );
        }
        g2.dispose();

        return colorBox;
    }

    // Region Growing (Flood-Fill) for Region Segmentation
    private static void floodFill(
            BufferedImage img,
            boolean[][] visited,
            int sx,
            int sy,
            Box box
    ) {

        int w = img.getWidth();
        int h = img.getHeight();

        Queue<Point> q = new ArrayDeque<>();
        q.add(new Point(sx, sy));
        visited[sx][sy] = true;

        while (!q.isEmpty()) {
            Point p = q.poll();
            int x = p.x;
            int y = p.y;

            box.minX = Math.min(box.minX, x);
            box.minY = Math.min(box.minY, y);
            box.maxX = Math.max(box.maxX, x);
            box.maxY = Math.max(box.maxY, y);

            for (int j = -1; j <= 1; j++) {
                for (int i = -1; i <= 1; i++) {
                    int nx = x + i;
                    int ny = y + j;

                    if (nx >= 0 && ny >= 0 && nx < w && ny < h &&
                            !visited[nx][ny] &&
                            (img.getRGB(nx, ny) & 0xff) == 255) {

                        visited[nx][ny] = true;
                        q.add(new Point(nx, ny));
                    }
                }
            }
        }
    }
}