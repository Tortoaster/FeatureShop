package com.trick.featureship;

import java.awt.*;

public class Colorpicker extends Action {

    private Color selectedColor = Color.BLACK;

    public Colorpicker() {
        super("Color");
    }

    public Color getSelectedColor() {
        return selectedColor;
    }
}
