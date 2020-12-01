package com.trick.featureshop.tools;

import com.trick.featureshop.Canvas;
import com.trick.featureshop.actions.Action;
import com.trick.featureshop.actions.ColorPicker;

import java.awt.event.MouseEvent;

public class EyeDropper extends Tool {

    private final ColorPicker colorPicker;

    public EyeDropper(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    @Override
    public String getName() {
        return "Dropper";
    }

    @Override
    public Action[] getActions() {
        return new Action[]{colorPicker};
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        colorPicker.setColor(canvas.eyeDrop(x, y));
    }

    @Override
    public void mouseDragged(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        colorPicker.setColor(canvas.eyeDrop(x, y));
    }
}
