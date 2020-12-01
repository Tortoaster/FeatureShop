package com.trick.featureshop.tools;

import com.trick.featureshop.Canvas;
import com.trick.featureshop.actions.Action;
import com.trick.featureshop.actions.ColorPicker;
import com.trick.featureshop.actions.FillShape;
import com.trick.featureshop.actions.NumberPicker;

import java.awt.event.MouseEvent;

public class Rectangle extends Tool {

    private final FillShape fill;
    private final ColorPicker colorPicker;
    private final NumberPicker numberPicker;

    private int fromX, fromY;

    public Rectangle(FillShape fill, ColorPicker colorPicker, NumberPicker numberPicker) {
        this.fill = fill;
        this.colorPicker = colorPicker;
        this.numberPicker = numberPicker;
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.point(x, y, numberPicker.getNumber(), colorPicker.getColor());
        canvas.repaint();

        fromX = x;
        fromY = y;
    }

    @Override
    public String getIconName() {
        return "black-and-white";
    }

    @Override
    public void mouseReleased(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.line(fromX, fromY, fromX, y, numberPicker.getNumber(), colorPicker.getColor());
        canvas.line(fromX, fromY, x, fromY, numberPicker.getNumber(), colorPicker.getColor());
        canvas.line(x, y, fromX, y, numberPicker.getNumber(), colorPicker.getColor());
        canvas.line(x, y, x, fromY, numberPicker.getNumber(), colorPicker.getColor());

        if (fill.getFill()) {
            int minX = Math.min(x, fromX);
            int maxX = Math.max(x, fromX);
            int minY = Math.min(y, fromY);
            int maxY = Math.max(y, fromY);

            for (int i = minX; i < maxX; i++) {
                for (int j = minY; j < maxY; j++) {
                    canvas.point(i, j, 1, colorPicker.getColor());
                }
            }
        }

        canvas.save();
        canvas.repaint();
    }

    @Override
    public String getName() {
        return "Rectangle";
    }

    @Override
    public Action[] getActions() {
        return new Action[]{fill, colorPicker, numberPicker};
    }
}
