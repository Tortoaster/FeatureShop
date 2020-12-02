package com.trick.featureshop.plugins;

import com.trick.featureshop.Canvas;
import com.trick.featureshop.FeatureShop;

import java.awt.event.ActionEvent;

public class Clear implements Plugin {
    @Override
    public String getName() {
        return "Clear Layer";
    }

    @Override
    public Type getType() {
        return Type.PLUGIN;
    }

    @Override
    public void buttonPressed(ActionEvent event, FeatureShop shop) {
        Canvas canvas = shop.getCanvas();
        canvas.clear();
        canvas.save();
        canvas.repaint();
    }

    @Override
    public String shortcut() {
        return "control C";
    }
}
