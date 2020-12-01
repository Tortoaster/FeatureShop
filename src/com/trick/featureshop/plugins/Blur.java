package com.trick.featureshop.plugins;

import com.trick.featureshop.FeatureShop;

import java.awt.event.ActionEvent;

public class Blur implements Plugin {

    @Override
    public String getName() {
        return "Blur";
    }

    @Override
    public Type getType() {
        return Type.PLUGIN;
    }

    @Override
    public void buttonPressed(ActionEvent event, FeatureShop shop) {
        shop.getCanvas().blur();
    }

    @Override
    public String shortcut() {
        return "control B";
    }

}
