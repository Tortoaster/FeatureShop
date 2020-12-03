package com.trick.featureshop.tools; 
import com.trick.featureshop.Canvas; 

import java.awt.event.MouseWheelEvent; 

public  class  Zoom  extends Tool {
	

    @Override
    public String getName() {
        return "Zoom";
    }

	

    @Override
    public void mouseWheelMoved(MouseWheelEvent e, Canvas canvas) {
        canvas.setScale(Math.min(Canvas.MAX_ZOOM, Math.max(1, canvas.getScale() - ((float) e.getPreciseWheelRotation() * canvas.getScale()))));
        canvas.repaint();
    }

	

    @Override
    public String getIconName() {
        return "search";
    }


}
