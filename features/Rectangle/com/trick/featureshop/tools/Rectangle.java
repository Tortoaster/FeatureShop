package com.trick.featureshop.tools;

import com.trick.featureshop.Canvas;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class Rectangle extends Tool {

    private int fromX, fromY;

    private boolean getFill() {
    	return false;
    }
    
    private int getRadius() {
    	return 1;
    }
    
    private Color getColor() {
    	return Color.black;
    }
    
    @Override
    public String getName() {
        return "Rectangle";
    }
    
    @Override
    public String getIconName() {
        return "black-and-white";
    }
    
    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.clearPreview();
        canvas.point(x, y, getRadius(), getColor(), true);
        canvas.repaint();

        fromX = x;
        fromY = y;
    }

    @Override
    public void mouseDragged(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.clearPreview();
        canvas.line(fromX, fromY, fromX, y, getRadius(), getColor(), true);
        canvas.line(fromX, fromY, x, fromY, getRadius(), getColor(), true);
        canvas.line(x, y, fromX, y, getRadius(), getColor(), true);
        canvas.line(x, y, x, fromY, getRadius(), getColor(), true);

        if (getFill()) {
            int minX = Math.min(x, fromX);
            int maxX = Math.max(x, fromX);
            int minY = Math.min(y, fromY);
            int maxY = Math.max(y, fromY);

            for (int i = minX; i < maxX; i++) {
                for (int j = minY; j < maxY; j++) {
                    canvas.point(i, j, 1, getColor(), true);
                }
            }
        }

        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.clearPreview();
        canvas.line(fromX, fromY, fromX, y, getRadius(), getColor());
        canvas.line(fromX, fromY, x, fromY, getRadius(), getColor());
        canvas.line(x, y, fromX, y, getRadius(), getColor());
        canvas.line(x, y, x, fromY, getRadius(), getColor());

        if (getFill()) {
            int minX = Math.min(x, fromX);
            int maxX = Math.max(x, fromX);
            int minY = Math.min(y, fromY);
            int maxY = Math.max(y, fromY);

            for (int i = minX; i < maxX; i++) {
                for (int j = minY; j < maxY; j++) {
                    canvas.point(i, j, 1, getColor());
                }
            }
        }
        
        canvas.repaint();
    }
}
