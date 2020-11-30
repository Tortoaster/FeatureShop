package com.trick.featureshop;

import com.trick.featureshop.actions.Action;

import javax.swing.*;

public class Actionbar extends JPanel {

    public Actionbar(com.trick.featureshop.actions.Action[] actions, FeatureShop shop) {
        for (Action a: actions) {
            add(new JLabel(a.getName() + ":"));
            add(a.getComponent());
        }
    }
}
