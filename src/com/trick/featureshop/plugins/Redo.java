package com.trick.featureshop.plugins;

import com.trick.featureshop.FeatureShop;

import java.awt.event.ActionEvent;

public class Redo implements Plugin {

    @Override
    public void buttonPressed(ActionEvent event, FeatureShop shop) {

    }

    @Override
    public String shortcut() { return "control Y"; }

    @Override
    public String getName() { return "Redo"; }

}
