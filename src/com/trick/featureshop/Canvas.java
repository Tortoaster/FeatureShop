package com.trick.featureshop; 

import javax.swing.*; 
import java.awt.*; 
import java.awt.image.BufferedImage; import java.util.ArrayList; 
import java.util.Arrays; public   class  Canvas  extends JPanel {
	

    private static final Color BACKGROUND = Color.DARK_GRAY;

	
    public static final Color EMPTY = new Color(0, 0, 0, 0);

	

    private final int canvasWidth, canvasHeight;

	

    private int left;

	
    private int top;

	

    private Color[][] pixels, preview;

	

    public Canvas(int canvasWidth, int canvasHeight, String name) throws IllegalArgumentException {
        if (canvasWidth <= 0 || canvasHeight <= 0)
            throw new IllegalArgumentException("width and height must be greater than 0");
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        pixels = emptyPixels();
        preview = emptyPixels();
        setName(name);
    }

	

    public Canvas(Color[][] pixels, String name) {
        canvasWidth = pixels.length;
        canvasHeight = pixels[0].length;

        this.pixels = pixels;
        preview = emptyPixels();
        setName(name);
    }

	

    public void point(int x, int y, int radius, Color color) {
        point(x, y, radius, color, false);
    }

	

    public void point(int x, int y, int radius, Color color, boolean preview) {
        for (int dY = -radius; dY < radius; dY++) {
            for (int dX = -radius; dX < radius; dX++) {
                if (dX * dX + dY * dY < radius * radius) {
                    int fX = x + dX;
                    int fY = y + dY;
                    if (fX >= 0 && fX < canvasWidth && fY >= 0 && fY < canvasHeight) {
                        if (preview) {
                            this.preview[fX][fY] = color;
                        } else {
                            pixels[fX][fY] = color;
                        }
                    }
                }
            }
        }
    }

	

    public void line(int x1, int y1, int x2, int y2, int radius, Color color) {
        line(x1, y1, x2, y2, radius, color, false);
    }

	

    public void line(int x1, int y1, int x2, int y2, int radius, Color color, boolean preview) {
        int x, y;
        int dx, dy;
        int incx, incy;
        int balance;

        if (x2 >= x1) {
            dx = x2 - x1;
            incx = 1;
        } else {
            dx = x1 - x2;
            incx = -1;
        }

        if (y2 >= y1) {
            dy = y2 - y1;
            incy = 1;
        } else {
            dy = y1 - y2;
            incy = -1;
        }

        x = x1;
        y = y1;

        if (dx >= dy) {
            dy <<= 1;
            balance = dy - dx;
            dx <<= 1;

            while (x != x2) {
                point(x, y, radius, color, preview);
                if (balance >= 0) {
                    y += incy;
                    balance -= dx;
                }
                balance += dy;
                x += incx;
            }
            point(x, y, radius, color, preview);
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != y2) {
                point(x, y, radius, color, preview);
                if (balance >= 0) {
                    x += incx;
                    balance -= dy;
                }
                balance += dx;
                y += incy;
            }
            point(x, y, radius, color, preview);
        }
    }

	
	
	public int screenToCanvasX  (int x) {
        return (int) ((x - left) / scale);
    }

	

    public int screenToCanvasY  (int y) {
        return (int) ((y - top) / scale);
    }

	

    private void drawImage(Graphics g) {
        drawLayer(g, pixels);
        drawLayer(g, preview);
    }

	
    
    private void drawLayer  (Graphics g, Color[][] layer) {
        for (int y = 0; y < canvasHeight; y++) {
            for (int x = 0; x < canvasWidth; x++) {
                g.setColor(layer[x][y]);
                g.fillRect((int) (x * scale) + left, (int) (y * scale) + top, (int) scale + 1, (int) scale + 1);
            }
        }
    }

	
    
    public void clearPreview() {
    	preview = emptyPixels();
    }

	

    public Color[][] emptyPixels() {
        Color[][] clear = new Color[canvasWidth][canvasHeight];

        for (int y = 0; y < canvasHeight; y++) {
            for (int x = 0; x < canvasWidth; x++) {
                clear[x][y] = EMPTY;
            }
        }

        return clear;
    }

	
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        float imageWidth = scale * canvasWidth;
        float imageHeight = scale * canvasHeight;

        left = (int) (getWidth() - imageWidth) / 2 + panX;
        top = (int) (getHeight() - imageHeight) / 2 + panY;

        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.clearRect(left, top, (int) imageWidth, (int) imageHeight);

        drawImage(g);

        g.setColor(BACKGROUND);
        g.drawRect(left - 1, top - 1, (int) imageWidth + 1, (int) imageHeight + 1);
    }

	
    
    public BufferedImage toBufferedImage() {
        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        for (int y = 0; y < canvasHeight; y++) {
            for (int x = 0; x < canvasWidth; x++) {
                g.setColor(pixels[x][y]);
                g.drawLine(x, y, x, y);
            }
        }

        g.dispose();
        return image;
    }

	

    
    public Color[][] getPixels() {
    	return pixels;
    }

	
    
    public void setPixels(Color[][] pixels) {
    	this.pixels = pixels;
    }

	
	public  interface  ColorCondition {
		
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

	
	public static final int MAX_ZOOM = 50;

	
	
	private float scale = 1;

	
    
    public void setScale(float scale) {
        this.scale = scale;
    }

	
    
    public float getScale() {
        return scale;
    }

	
	
	private int panX = 0, panY = 0;

	
	
	public void setPanX(int panX) {
        this.panX = panX;
    }

	

    public void setPanY(int panY) {
        this.panY = panY;
    }

	
    
    public int getPanX() {
        return panX;
    }

	

    public int getPanY() {
        return panY;
    }

	
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

	
	
    public void clear() {
    	setPixels(emptyPixels());
    }


}
