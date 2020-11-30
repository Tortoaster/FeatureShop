package com.trick.featureship;

import com.trick.featureship.tools.Tool;

import javax.swing.*;

public class Toolbar extends JPanel {

    public interface ToolbarListener {
        void selected(Tool tool);
    }

    private Tool activeTool;

    public Toolbar(Tool[] tools, FeatureShop shop, ToolbarListener listener) throws IllegalArgumentException {
        if (tools.length < 1) throw new IllegalArgumentException("You must include at least 1 tool.");

        activeTool = tools[0];

        for (Tool t: tools) {
            JButton button = new JButton(t.getName());
            button.addActionListener(actionEvent -> {
                activeTool = t;
                listener.selected(t);
            });
            add(button);
        }
    }

    public Tool getActiveTool() {
        return activeTool;
    }
}
