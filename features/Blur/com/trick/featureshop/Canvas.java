import java.util.ArrayList;
import java.util.Arrays;

public class Canvas {
    public void blur() {
        Color[][] currentPixels = getPixels();
        Color[][] newPixels = new Color[canvasWidth][canvasHeight];

        for (int x = 0; x < canvasWidth; x++) {
            for (int y = 0; y < canvasHeight; y++) {

                ArrayList<Color> neighbours = new ArrayList<Color>();

                for (int i = Math.max(0, x - 1); i <= Math.min(canvasWidth - 1, x + 1); i++) {
                    neighbours.addAll(Arrays.asList(currentPixels[i]).subList(Math.max(0, y - 1), Math.min(canvasHeight - 1, y + 1) + 1));
                }

                int r = 0, g = 0, b = 0, a = 0;
                for (Color c : neighbours) {
                    r += c.getRed();
                    g += c.getGreen();
                    b += c.getBlue();
                    a += c.getAlpha();
                }

                int count = neighbours.size();

                newPixels[x][y] = new Color(r / count, g / count, b / count, a / count);
            }
        }

        setPixels(newPixels);
        repaint();
    }
}