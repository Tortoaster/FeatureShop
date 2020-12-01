package com.trick.featureshop.actions;

import javax.swing.*;

public class FillShape implements Action {

    private JCheckBox checkBox = new JCheckBox();

    public FillShape(boolean fill) {
        checkBox.setSelected(fill);
    }

    @Override
    public JComponent getComponent() {
        return checkBox;
    }

    @Override
    public String getName() {
        return "Fill";
    }

    public boolean getFill() {
        return checkBox.isSelected();
    }
}
