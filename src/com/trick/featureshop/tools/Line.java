package com.trick.featureshop.tools; 

import com.trick.featureshop.Canvas; 

import java.awt.*; 
import java.awt.event.MouseEvent; 

public  class  Line  extends Tool {
	
	
    private int fromX, fromY;

	

    private int getRadius() {
    	return 1;
    }

	
    
    private Color getColor() {
    	return Color.BLACK;
    }

	
    
    @Override
    public String getIconName() {
        return "line-tool";
    }

	

    @Override
    public String getName() {
        return "Line";
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
        canvas.line(fromX, fromY, x, y, getRadius(), getColor(), true);
        canvas.repaint();
    }

	

    @Override
    public void mouseReleased(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.clearPreview();
        canvas.line(fromX, fromY, x, y, getRadius(), getColor());
        canvas.repaint();
        canvas.onChange();
    }


}
