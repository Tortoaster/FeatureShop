package com.trick.featureshop.tools;

import com.trick.featureshop.actions.Action;
import com.trick.featureshop.Canvas;
import com.trick.featureshop.actions.NumberPicker;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Eraser extends Tool {

    private static final Color EMPTY = Color.WHITE;

    private int previousX, previousY;

    private final NumberPicker numberPicker;

    public Eraser(NumberPicker numberPicker) {
        this.numberPicker = numberPicker;
    }

    @Override
    public String getName() {
        return "Eraser";
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.point(x, y, numberPicker.getNumber(), EMPTY);
        canvas.repaint();

        previousX = x;
        previousY = y;
    }

    @Override
    public void mouseDragged(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.line(previousX, previousY, x, y, numberPicker.getNumber(), EMPTY);
        canvas.repaint();

        previousX = x;
        previousY = y;
    }

    @Override
    public Action[] getActions() {
        return new Action[]{numberPicker};
    }
}
