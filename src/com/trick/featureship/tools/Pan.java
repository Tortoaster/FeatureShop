package com.trick.featureship.tools;

import com.trick.featureship.Action;
import com.trick.featureship.Canvas;

import java.awt.event.MouseEvent;

public class Pan extends Tool {

    private int x, y;

    public Pan() {
        super("Pan");
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        super.mousePressed(e, canvas);
        x = e.getX() - canvas.getPanX();
        y = e.getY() - canvas.getPanY();
    }

    @Override
    public void mouseDragged(MouseEvent e, Canvas canvas) {
        super.mouseDragged(e, canvas);
        canvas.setPanX(e.getX() - x);
        canvas.setPanY(e.getY() - y);
        canvas.repaint();
    }

    @Override
    public Action[] getActions() {
        return new Action[0];
    }
}
