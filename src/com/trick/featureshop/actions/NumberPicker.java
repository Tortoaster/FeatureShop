package com.trick.featureshop.actions;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NumberPicker implements Action {

    private final String name;

    private final JPanel layout;
    private final JSlider slider;
    private final JLabel label;

    public NumberPicker(String name, int min, int max, int value) throws IllegalArgumentException {
        if (max < min) throw new IllegalArgumentException("max cannot be less than min");
        if (value < min || value > max) throw new IllegalArgumentException("value out of range");

        this.name = name;

        layout = new JPanel();
        slider = new JSlider(min, max, value);
        label = new JLabel();

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateLabel();
            }
        });

        updateLabel();

        layout.add(slider);
        layout.add(label);
    }

    private void updateLabel() {
        label.setText("" + slider.getValue());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JComponent getComponent() {
        return layout;
    }

    public int getNumber() {
        return slider.getValue();
    }
}
