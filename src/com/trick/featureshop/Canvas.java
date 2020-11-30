package com.trick.featureshop;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    public interface ColorCondition {
        boolean accept(Color color);
    }

    public static final int MAX_ZOOM = 50;

    private static final Color BACKGROUND = Color.DARK_GRAY;

    private int canvasWidth, canvasHeight;

    private int panX = 0, panY = 0;

    private int left;
    private int top;

    private float scale = 1;

    private Color[][] pixels;

    public Canvas(int canvasWidth, int canvasHeight) throws IllegalArgumentException {
        if (canvasWidth <= 0 || canvasHeight <= 0) throw new IllegalArgumentException("width and height must be greater than 0");
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        pixels = new Color[canvasWidth][canvasHeight];

        for (int y = 0; y < canvasHeight; y++) {
            for (int x = 0; x < canvasWidth; x++) {
                pixels[x][y] = Color.WHITE;
            }
        }
    }

    public Canvas(Color[][] pixels) {
        this.pixels = pixels;

        canvasWidth = pixels.length;
        canvasHeight = pixels[0].length;
    }

    public void point(int x, int y, int radius, Color color) {
        for(int dY = -radius; dY < radius; dY++) {
            for (int dX = -radius; dX < radius; dX++) {
                if(dX * dX + dY * dY < radius * radius) {
                    int fX = x + dX;
                    int fY = y + dY;
                    if(fX >= 0 && fX < canvasWidth && fY >= 0 && fY < canvasHeight) {
                        pixels[fX][fY] = color;
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
        if(x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight && condition.accept(pixels[x][y])) {
            pixels[x][y] = color;
            spread(x, y - 1, color, condition);
            spread(x + 1, y, color, condition);
            spread(x, y + 1, color, condition);
            spread(x - 1, y, color, condition);
        }
    }

    public Color eyeDrop(int x, int y) {
        if(x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight) {
            return pixels[x][y];
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        float imageWidth = scale * canvasWidth;
        float imageHeight = scale * canvasHeight;

        left = (int) (getWidth() - imageWidth) / 2 + panX;
        top = (int) (getHeight() - imageHeight) / 2 + panY;

        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int y = 0; y < canvasHeight; y++) {
            for (int x = 0; x < canvasWidth; x++) {
                g.setColor(pixels[x][y]);
                g.fillRect((int) (x * scale) + left, (int) (y * scale) + top, (int) scale + 1, (int) scale + 1);
            }
        }

        g.setColor(BACKGROUND);
        g.drawRect(left - 1, top - 1, (int) imageWidth + 1, (int) imageHeight + 1);
    }

    public void setPixels(Color[][] pixels) {
        this.pixels = pixels;
    }

    public void setCanvasHeight(int canvasHeight) {
        this.canvasHeight = canvasHeight;
    }

    public void setCanvasWidth(int canvasWidth) {
        this.canvasWidth = canvasWidth;
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

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
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

    public Color[][] getPixels() { return pixels; }

}
