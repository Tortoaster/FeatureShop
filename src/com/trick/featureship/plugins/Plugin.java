package com.trick.featureship.plugins;

import com.trick.featureship.FeatureShop;

import java.awt.event.ActionEvent;

public interface Plugin {

    String getName();

    void buttonPressed(ActionEvent e, FeatureShop shop);

    char shortcut();

}