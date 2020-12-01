package com.trick.featureshop.actions;

import javax.swing.*;

public class FillShape implements Action {

    private boolean fill;
    private final JPanel panel;

    public FillShape(boolean fill) {
        this.fill = fill;
        panel = new JPanel();
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(fill);
        checkBox.addItemListener(e -> {
            this.fill = e.getStateChange() == 1;
        });
        panel.add(checkBox);
    }

    @Override
    public JComponent getComponent() {
        return panel;
    }

    @Override
    public String getName() {
        return "Fill";
    }

    public boolean getFill() {
        return fill;
    }
}
