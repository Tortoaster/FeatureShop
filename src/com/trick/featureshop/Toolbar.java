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

    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    public Tool getActiveTool() {
        return activeTool;
    }
}

