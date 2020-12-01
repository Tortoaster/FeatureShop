package com.trick.featureshop;

import com.trick.featureshop.tools.Tool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel {

    public interface ToolbarListener {
        void selected(Tool tool);
    }

    private Tool activeTool;

    public Toolbar(Tool[] tools, ToolbarListener listener) throws IllegalArgumentException {
        if (tools.length < 1) throw new IllegalArgumentException("You must include at least 1 tool.");

        activeTool = tools[0];

        for (Tool t: tools) {
            JButton button = new JButton();
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    activeTool = t;
                    button.setSelected(true);
                    listener.selected(t);
                }
            });
            try {
                Image img = ImageIO.read(getClass().getResource("/icons/" + t.getIconName() + ".png"));
                button.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                e.printStackTrace();
            }
            add(button);
        }
    }

    public Tool getActiveTool() {
        return activeTool;
    }
}

