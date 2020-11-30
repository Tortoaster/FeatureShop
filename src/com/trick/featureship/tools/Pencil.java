package com.trick.featureship.tools;

import com.trick.featureship.Action;
import com.trick.featureship.Canvas;
import com.trick.featureship.Colorpicker;

import java.awt.event.MouseEvent;

public class Pencil extends Tool {

    private final Colorpicker colorpicker;

    private int previousX, previousY;

    public Pencil(Colorpicker colorpicker) {
        super("Pencil");
        this.colorpicker = colorpicker;
    }

    @Override
    public void mousePressed(MouseEvent e, Canvas canvas) {
        super.mousePressed(e, canvas);

        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.pixel(x, y, colorpicker.getSelectedColor());
        canvas.repaint();

        previousX = x;
        previousY = y;
    }

    @Override
    public void mouseDragged(MouseEvent e, Canvas canvas) {
        super.mouseDragged(e, canvas);

        int x = canvas.screenToCanvasX(e.getX());
        int y = canvas.screenToCanvasY(e.getY());

        canvas.line(previousX, previousY, x, y, colorpicker.getSelectedColor());
        canvas.repaint();

        previousX = x;
        previousY = y;
    }

    @Override
    public Action[] getActions() {
        return new Action[]{colorpicker};
    }
}
