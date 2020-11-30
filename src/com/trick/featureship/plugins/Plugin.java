package com.trick.featureship.plugins;

import com.trick.featureship.FeatureShop;

import javax.swing.*;
import javax.swing.event.MenuEvent;

public interface Plugin {

    String getName();

    void buttonPressed(MenuEvent event, FeatureShop shop, JMenu menu);

}