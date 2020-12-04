package com.trick.featureshop.plugins;

import com.trick.featureshop.FeatureShop;
import com.trick.featureshop.plugins.Plugin;

import java.awt.event.ActionEvent;

public interface Plugin {

    String getName();

    Type getType();

    void buttonPressed(ActionEvent event, FeatureShop shop);

    String shortcut();

}