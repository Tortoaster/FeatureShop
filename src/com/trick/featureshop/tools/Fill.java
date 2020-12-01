package com.trick.featureshop.tools;

import com.trick.featureshop.Canvas;
import com.trick.featureshop.actions.Action;
import com.trick.featureshop.actions.ColorPicker;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Fill extends Tool {

    private final ColorPicker colorPicker;

    public Fill(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        Color color = canvas.eyeDrop(x, y);
        Color replace = colorPicker.getColor();

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
    public String getName() {
        return "Fill";
    }

    @Override
    public Action[] getActions() {
        return new Action[]{colorPicker};
    }
}
