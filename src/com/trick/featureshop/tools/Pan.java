package com.trick.featureshop.tools;

import com.trick.featureshop.actions.Action;
import com.trick.featureshop.Canvas;

import java.awt.event.MouseEvent;

public class Pan extends Tool {

    private int x, y;

    @Override
    public String getName() {
        return "Pan";
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        x = e.getX() - canvas.getPanX();
        y = e.getY() - canvas.getPanY();
    }

    @Override
    public void mouseDragged(MouseEvent e, Canvas canvas) {
        canvas.setPanX(e.getX() - x);
        canvas.setPanY(e.getY() - y);
        canvas.repaint();
    }

    @Override
    public Action[] getActions() {
        return new Action[0];
    }
}
