package com.trick.featureshop.tools; 

import com.trick.featureshop.Canvas; 

import java.awt.*; 
import java.awt.event.MouseEvent; import java.util.ArrayList; 

import com.trick.featureshop.actions.Action; 

public   class  Pencil  extends Tool {
	

    private int previousX, previousY;

	

    public Pencil() { }

	

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

        canvas.point(x, y, 1, Color.BLACK);
        canvas.repaint();

        previousX = x;
        previousY = y;
    }

	

    @Override
    public void mouseDragged(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.line(previousX, previousY, x, y, 1, Color.BLACK);
        canvas.repaint();

        previousX = x;
        previousY = y;
    }

	
	public ArrayList<Action> getActions() {
		return new ArrayList<Action>();
	}


}
