package com.trick.featureship.tools;

import com.trick.featureship.actions.Action;
import com.trick.featureship.Canvas;
import com.trick.featureship.actions.ColorPicker;

import java.awt.event.MouseEvent;

public class Line implements Tool {

    private final ColorPicker colorPicker;

    private int fromX, fromY;

    public Line(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    @Override
    public String getName() {
        return "Line";
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.pixel(x, y, colorPicker.getColor());
        canvas.repaint();

        fromX = x;
        fromY = y;
    }

    @Override
    public void mouseReleased(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.line(fromX, fromY, x, y, colorPicker.getColor());
        canvas.repaint();
    }

    @Override
    public Action[] getActions() {
        return new Action[]{colorPicker};
    }
}
