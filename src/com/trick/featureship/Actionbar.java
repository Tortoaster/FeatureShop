package com.trick.featureship;

import javax.swing.*;
import java.awt.*;

public class Actionbar extends JPanel {

    public Actionbar(Action[] actions, FeatureShop shop) {
        for (Action a: actions) {
            add(a.getComponent());
        }
    }
}
