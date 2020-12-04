package com.trick.featureshop.tools;

import com.trick.featureshop.Canvas;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Pencil extends Tool {

    private int previousX, previousY;

    public Pencil() { }

    public int getRadius() {
    	return 1;
    }
    
    public Color getColor() {
    	return Color.BLACK;
    }
    
    @Override
    public String getName() {
        return "Pencil";
    }

    @Override
    public String getIconName() {
        return "pencil";
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.point(x, y, getRadius(), getColor());
        canvas.repaint();

        previousX = x;
        previousY = y;
    }

    @Override
    public void mouseDragged(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.line(previousX, previousY, x, y, getRadius(), getColor());
        canvas.repaint();

        previousX = x;
        previousY = y;
    }
    
    @Override
    public void mouseReleased(MouseEvent e, Canvas canvas) {
        canvas.onChange();
    }

}
