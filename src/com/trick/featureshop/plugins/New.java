package com.trick.featureshop.plugins;

import com.trick.featureshop.Canvas;
import com.trick.featureshop.FeatureShop;

import java.awt.event.ActionEvent;

public class New implements Plugin {

    @Override
    public String getName() { return "New"; }

    @Override
    public String shortcut() { return "control N"; }

    @Override
    public void buttonPressed(ActionEvent event, FeatureShop shop) {
        shop.addCanvas(new Canvas(128, 128));
    }
}
