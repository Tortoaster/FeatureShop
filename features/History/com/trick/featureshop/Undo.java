package com.trick.featureshop.plugins;

import com.trick.featureshop.FeatureShop;

import java.awt.event.ActionEvent;

public class Undo implements Plugin {

    @Override
    public void buttonPressed(ActionEvent event, FeatureShop shop) {
        shop.getCanvas().undo();
    }

    @Override
    public String shortcut() { return "control Z"; }

    @Override
    public String getName() { return "Undo"; }

    @Override
    public Type getType() {
        return Type.HISTORY;
    }

}
