package com.trick.featureship.tools;

import com.trick.featureship.actions.Action;
import com.trick.featureship.Canvas;
import com.trick.featureship.actions.ColorPicker;

import java.awt.event.MouseEvent;

public class Pencil implements Tool {

    private final ColorPicker colorPicker;

    private int previousX, previousY;

    public Pencil(ColorPicker colorPicker) {
        this.colorPicker = colorPicker;
    }

    @Override
    public String getName() {
        return "Pencil";
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.pixel(x, y, colorPicker.getColor());
        canvas.repaint();

        previousX = x;
        previousY = y;
    }

    @Override
    public void mouseDragged(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.line(previousX, previousY, x, y, colorPicker.getColor());
        canvas.repaint();

        previousX = x;
        previousY = y;
    }

    @Override
    public Action[] getActions() {
        return new Action[]{colorPicker};
    }
}
