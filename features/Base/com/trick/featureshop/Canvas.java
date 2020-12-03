package com.trick.featureshop;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

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

    public int screenToCanvasX(int x) {
        return (int) (x - left);
    }

    public int screenToCanvasY(int y) {
        return (int) (y - top);
    }

    private void drawImage(Graphics g) {
        drawLayer(g, pixels);
        drawLayer(g, preview);
    }

    private void drawLayer(Graphics g, Color[][] pixels) {
        for (int y = 0; y < canvasHeight; y++) {
            for (int x = 0; x < canvasWidth; x++) {
                g.setColor(pixels[x][y]);
                g.fillRect((int) x + left, y + top, 1, 1);
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

        left = (int) (getWidth() - canvasWidth) / 2;
        top = (int) (getHeight() - canvasHeight) / 2;

        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.clearRect(left, top, (int) canvasWidth, (int) canvasHeight);

        drawImage(g);

        g.setColor(BACKGROUND);
        g.drawRect(left - 1, top - 1, (int) canvasWidth + 1, (int) canvasHeight + 1);
    }
}
