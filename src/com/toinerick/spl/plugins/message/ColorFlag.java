package com.toinerick.spl.plugins.message;

import java.awt.*;

public class ColorFlag implements MessageFlag {

    private final Color color;

    public ColorFlag(Color color) {
        this.color = color;
    }
}
