public class Canvas {
	public interface ColorCondition {
        boolean accept(Color color);
    }
	
	public void spread(int x, int y, Color color, ColorCondition condition) {
        if (x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight && condition.accept(pixels[x][y])) {
            pixels[x][y] = color;
            spread(x, y - 1, color, condition);
            spread(x + 1, y, color, condition);
            spread(x, y + 1, color, condition);
            spread(x - 1, y, color, condition);
        }
    }

    public Color eyeDrop(int x, int y) {
        if (x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight) {
            return pixels[x][y];
        } else {
            return Color.BLACK;
        }
    }
}