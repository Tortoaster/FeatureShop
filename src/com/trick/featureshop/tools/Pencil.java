package com.trick.featureshop.tools;

import com.trick.featureshop.actions.Action;
import com.trick.featureshop.Canvas;
import com.trick.featureshop.actions.ColorPicker;
import com.trick.featureshop.actions.NumberPicker;

import java.awt.event.MouseEvent;

public class Pencil implements Tool {

    private final ColorPicker colorPicker;
    private final NumberPicker numberPicker;

    private int previousX, previousY;

    public Pencil(ColorPicker colorPicker, NumberPicker numberPicker) {
        this.colorPicker = colorPicker;
        this.numberPicker = numberPicker;
    }

    @Override
    public String getName() {
        return "Pencil";
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.point(x, y, numberPicker.getNumber(), colorPicker.getColor());
        canvas.repaint();

        previousX = x;
        previousY = y;
    }

    @Override
    public void mouseDragged(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.line(previousX, previousY, x, y, numberPicker.getNumber(), colorPicker.getColor());
        canvas.repaint();

        previousX = x;
        previousY = y;
    }

    @Override
    public Action[] getActions() {
        return new Action[]{colorPicker, numberPicker};
    }
}
