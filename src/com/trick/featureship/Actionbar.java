package com.trick.featureship;

import com.trick.featureship.actions.Action;

import javax.swing.*;

public class Actionbar extends JPanel {

    public Actionbar(com.trick.featureship.actions.Action[] actions, FeatureShop shop) {
        for (Action a: actions) {
            add(a.getComponent());
        }
    }
}
