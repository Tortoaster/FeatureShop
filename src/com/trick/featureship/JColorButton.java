package com.trick.featureship;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JColorButton extends JButton {

    private Color color;

    public JColorButton(Color c) {
    	setColor(c);
        addActionListener(arg0 -> {
            Color newColor = JColorChooser.showDialog(null, "Pick a color", color);
            setColor(newColor);
        });
    }

    public static ImageIcon createIcon(Color main, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(main);
        graphics.fillRect(0, 0, width, height);
        image.flush();
        return new ImageIcon(image);
    }

    private void setColor(Color color) {
        this.color = color;
        setIcon(createIcon(this.color, Math.max(getWidth(), 100), Math.max(getHeight(), 100)));
        repaint();
    }
    
    public Color getColor() {
    	return color;
    }
}
