package com.trick.featureshop.tools;

import com.trick.featureshop.actions.Action;
import com.trick.featureshop.Canvas;
import com.trick.featureshop.actions.ColorPicker;
import com.trick.featureshop.actions.NumberPicker;

import java.awt.event.MouseEvent;

public class Line implements Tool {

    private final ColorPicker colorPicker;
    private final NumberPicker numberPicker;

    private int fromX, fromY;

    public Line(ColorPicker colorPicker, NumberPicker numberPicker) {
        this.colorPicker = colorPicker;
        this.numberPicker = numberPicker;
    }

    @Override
    public String getIconName() {
        return "line-tool";
    }

    @Override
    public String getName() {
        return "Line";
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
    public void mouseReleased(MouseEvent e, Canvas canvas) {
        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.line(fromX, fromY, x, y, numberPicker.getNumber(), colorPicker.getColor());
        canvas.repaint();
    }

    @Override
    public Action[] getActions() {
        return new Action[]{colorPicker};
    }
}
