package com.trick.featureshop.plugins;

import com.trick.featureshop.FeatureShop;

import java.awt.event.ActionEvent;

public interface Plugin {

    String getName();

    void buttonPressed(ActionEvent event, FeatureShop shop);

    String shortcut();

}