package com.trick.featureshop.actions;

import javax.swing.*;

public class FillShape implements Action {

	public static final Action ACTION = new FillShape(false);
	
    private final JCheckBox checkBox = new JCheckBox();

    private FillShape(boolean fill) {
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
