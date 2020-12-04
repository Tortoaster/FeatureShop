package com.trick.featureshop.tools; 

import com.trick.featureshop.Canvas; 
import com.trick.featureshop.actions.ColorPicker; 

import java.awt.event.MouseEvent; 

public  class  EyeDropper  extends Tool {
	

    @Override
    public String getIconName() {
        return "pipette";
    }

	

    @Override
    public String getName() {
        return "Dropper";
    }

	

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        ColorPicker.ACTION.setColor(canvas.eyeDrop(x, y));
    }

	

    @Override
    public void mouseDragged(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        ColorPicker.ACTION.setColor(canvas.eyeDrop(x, y));
    }


}
