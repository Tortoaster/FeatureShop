package com.trick.featureshop.tools;

import com.trick.featureshop.Canvas;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Eraser extends Tool {

    private int previousX, previousY;

    private int getRadius() {
    	return 2;
    }
    
    @Override
    public String getIconName() {
        return "eraser";
    }

    @Override
    public String getName() {
        return "Eraser";
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.point(x, y, getRadius(), Canvas.EMPTY);
        canvas.repaint();

        previousX = x;
        previousY = y;
    }

    @Override
    public void mouseDragged(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.line(previousX, previousY, x, y, getRadius(), Canvas.EMPTY);
        canvas.repaint();

        previousX = x;
        previousY = y;
    }
    
    @Override
    public void mouseReleased(MouseEvent e, Canvas canvas) {
        canvas.onChange();
    }
    
}
