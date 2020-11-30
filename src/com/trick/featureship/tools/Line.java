package com.trick.featureship.tools;

import com.trick.featureship.Action;
import com.trick.featureship.Canvas;
import com.trick.featureship.Colorpicker;

import java.awt.event.MouseEvent;

public class Line extends Tool {

    private final Colorpicker colorpicker;

    private int fromX, fromY;

    public Line(Colorpicker colorpicker) {
        super("Line");
        this.colorpicker = colorpicker;
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        super.mousePressed(e, canvas);

        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.pixel(x, y, colorpicker.getSelectedColor());
        canvas.repaint();

        fromX = x;
        fromY = y;
    }

    @Override
    public void mouseReleased(MouseEvent e, Canvas canvas) {
        super.mouseReleased(e, canvas);

        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.line(fromX, fromY, x, y, colorpicker.getSelectedColor());
        canvas.repaint();
    }

    @Override
    public Action[] getActions() {
        return new Action[]{colorpicker};
    }
}
