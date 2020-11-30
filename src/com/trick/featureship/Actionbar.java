package com.trick.featureship;

import com.trick.featureship.Action;

import javax.swing.*;
import java.awt.*;

public class Actionbar extends JPanel {

    private final Action[] actions;

    private Action activeAction = null;

    public Actionbar(Action[] actions, FeatureShop shop) {
        this.actions = actions;

        if (actions.length > 0) activeAction = actions[0];

        for (Action a: actions) {
            JButton button = new JButton(a.getName());
            button.addActionListener(actionEvent -> {
                activeAction = a;
            });
            add(button);
        }
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension((int) super.getMinimumSize().getWidth(), 20);
    }

    public Action getActiveAction() {
        return activeAction;
    }

}
