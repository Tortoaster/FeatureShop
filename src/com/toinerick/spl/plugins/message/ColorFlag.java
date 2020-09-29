package com.toinerick.spl.plugins.message;

import java.awt.*;

public class ColorFlag implements MessageFlag {

    private Color color;

    public ColorFlag(Color color) {
        this.color = color;
    }

    @Override
    public String preprocess(String message) {
        return message;
    }

    @Override
    public String postprocess(String message) {
        return message;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
