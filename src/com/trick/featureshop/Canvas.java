package com.trick.featureshop;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Canvas extends JPanel {

    public interface ColorCondition {
        boolean accept(Color color);
    }

    public interface RepaintListener {
        void repaint();
    }

    public static final int MAX_ZOOM = 50;
    public static final int PREVIEW_SIZE = 80;

    private static final Color BACKGROUND = Color.DARK_GRAY, EMPTY = new Color(0, 0, 0, 0);

    private final int canvasWidth, canvasHeight;

    private int panX = 0, panY = 0;

    private int selectedLayer;

    private int left;
    private int top;

    private float scale = 1;

    private final ArrayList<RepaintListener> repaintListeners = new ArrayList<RepaintListener>();

    private final ArrayList<Color[][]> layers = new ArrayList<Color[][]>();

    public Canvas(int canvasWidth, int canvasHeight) throws IllegalArgumentException {
        if (canvasWidth <= 0 || canvasHeight <= 0)
            throw new IllegalArgumentException("width and height must be greater than 0");
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        newLayer();

        setLayout(new BorderLayout());
        add(new LayerView(this), BorderLayout.LINE_END);
    }

    public Canvas(Color[][] pixels) {
        layers.add(pixels);

        canvasWidth = pixels.length;
        canvasHeight = pixels[0].length;
    }

    public void point(int x, int y, int radius, Color color) {
        for (int dY = -radius; dY < radius; dY++) {
            for (int dX = -radius; dX < radius; dX++) {
                if (dX * dX + dY * dY < radius * radius) {
                    int fX = x + dX;
                    int fY = y + dY;
                    if (fX >= 0 && fX < canvasWidth && fY >= 0 && fY < canvasHeight) {
                        layers.get(selectedLayer)[fX][fY] = color;
                    }
                }
            }
        }
    }

    public void line(int x1, int y1, int x2, int y2, int radius, Color color) {
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
                point(x, y, radius, color);
                if (balance >= 0) {
                    y += incy;
                    balance -= dx;
                }
                balance += dy;
                x += incx;
            }
            point(x, y, radius, color);
        } else {
            dx <<= 1;
            balance = dx - dy;
            dy <<= 1;

            while (y != y2) {
                point(x, y, radius, color);
                if (balance >= 0) {
                    x += incx;
                    balance -= dy;
                }
                balance += dx;
                y += incy;
            }
            point(x, y, radius, color);
        }
    }

    public void spread(int x, int y, Color color, ColorCondition condition) {
        if (x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight && condition.accept(layers.get(selectedLayer)[x][y])) {
            layers.get(selectedLayer)[x][y] = color;
            spread(x, y - 1, color, condition);
            spread(x + 1, y, color, condition);
            spread(x, y + 1, color, condition);
            spread(x - 1, y, color, condition);
        }
    }

    public Color eyeDrop(int x, int y) {
        if (x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight) {
            return layers.get(selectedLayer)[x][y];
        } else {
            return Color.BLACK;
        }
    }

    public int screenToCanvasX(int x) {
        return (int) ((x - left) / scale);
    }

    public int screenToCanvasY(int y) {
        return (int) ((y - top) / scale);
    }

    public void newLayer() {
        Color[][] pixels = new Color[canvasWidth][canvasHeight];

        for (int y = 0; y < canvasHeight; y++) {
            for (int x = 0; x < canvasWidth; x++) {
                pixels[x][y] = EMPTY;
            }
        }

        layers.add(selectedLayer, pixels);
    }

    public void deleteLayer() {
        if(layers.size() > 1) {
            layers.remove(selectedLayer);
            selectedLayer = Math.max(0, selectedLayer--);
        }
    }

    public void moveLayerUp() {
        if(selectedLayer < layers.size() - 1) {
            Color[][] pixels = layers.remove(selectedLayer);
            layers.add(selectedLayer + 1, pixels);
            selectedLayer++;
        }
    }

    public void moveLayerDown() {
        if(selectedLayer > 0) {
            Color[][] pixels = layers.remove(selectedLayer);
            layers.add(selectedLayer - 1, pixels);
            selectedLayer--;
        }
    }

    private void drawImage(Graphics g) {
        for (Color[][] pixels : layers) {
            for (int y = 0; y < canvasHeight; y++) {
                for (int x = 0; x < canvasWidth; x++) {
                    g.setColor(pixels[x][y]);
                    g.fillRect((int) (x * scale) + left, (int) (y * scale) + top, (int) scale + 1, (int) scale + 1);
                }
            }
        }
    }

    void addRepaintListener(RepaintListener repaintListener) {
        repaintListeners.add(repaintListener);
    }

    public int countLayers() {
        return layers.size();
    }

    public BufferedImage toBufferedImage() {
        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        for(Color[][] pixels : layers) {
            for (int y = 0; y < canvasHeight; y++) {
                for (int x = 0; x < canvasWidth; x++) {
                    g.setColor(pixels[x][y]);
                    g.drawLine(x, y, x, y);
                }
            }
        }

        g.dispose();
        return image;
    }

    public BufferedImage preview(int layer) {
        BufferedImage image = new BufferedImage(PREVIEW_SIZE, PREVIEW_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        Color[][] pixels = layers.get(layer);

        for (int y = 0; y < PREVIEW_SIZE; y++) {
            for (int x = 0; x < PREVIEW_SIZE; x++) {
                g.setColor(pixels[(int) ((float) x / PREVIEW_SIZE * canvasWidth)][(int) ((float) y / PREVIEW_SIZE * canvasHeight)]);
                g.drawLine(x, y, x, y);
            }
        }

        g.dispose();
        return image;
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

    @Override
    public void repaint(Rectangle r) {
        super.repaint(r);
        for(RepaintListener l: repaintListeners) {
            l.repaint();
        }
    }

    public void setPanX(int panX) {
        this.panX = panX;
    }

    public void setPanY(int panY) {
        this.panY = panY;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setSelectedLayer(int selectedLayer) {
        this.selectedLayer = selectedLayer;
    }

    public int getPanX() {
        return panX;
    }

    public int getPanY() {
        return panY;
    }

    public float getScale() {
        return scale;
    }

    public int getSelectedLayer() {
        return selectedLayer;
    }
}
