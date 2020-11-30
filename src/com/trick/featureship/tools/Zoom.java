package com.trick.featureship.tools;

import com.trick.featureship.actions.Action;
import com.trick.featureship.Canvas;

import java.awt.event.MouseWheelEvent;

public class Zoom implements Tool {

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
    public Action[] getActions() {
        return new Action[0];
    }
}
