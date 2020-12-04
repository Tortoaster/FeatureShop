package com.trick.featureshop.tools;

import com.trick.featureshop.Canvas;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Fill extends Tool {
    
	private Color getColor() {
		return Color.BLACK;
	}
	
    @Override
    public String getIconName() {
        return "paint-bucket";
    }

    @Override
    public String getName() {
        return "Fill";
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        Color color = canvas.eyeDrop(x, y);
        Color replace = getColor();

        if(!color.equals(replace)) {
            canvas.spread(x, y, replace, new Canvas.ColorCondition() {
                @Override
                public boolean accept(Color c) {
                    return c.equals(color);
                }
            });
        }

        canvas.repaint();
    }
    
    @Override
    public void mouseReleased(MouseEvent e, Canvas canvas) {
        canvas.onChange();
    }
    
}
