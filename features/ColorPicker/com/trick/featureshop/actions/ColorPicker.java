package com.trick.featureshop.actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ColorPicker implements Action {

	public static final ColorPicker ACTION = new ColorPicker("Color", Color.BLACK);
	
    private static final int SIZE = 20;

    private final String name;

    private Color color;

    private final JButton button = new JButton();

    private ColorPicker(String name, Color color) {
        this.name = name;
        setColor(color);

        button.setPreferredSize(new Dimension(SIZE, SIZE));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Pick a color", color);
                setColor(newColor);
            }
        });
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public JComponent getComponent() {
        return button;
    }

    public static ImageIcon createIcon(Color main, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(main);
        graphics.fillRect(0, 0, width, height);
        image.flush();
        return new ImageIcon(image);
    }

    public void setColor(Color color) {
        this.color = color;
        button.setIcon(createIcon(color, SIZE, SIZE));
        button.repaint();
    }

    public Color getColor() {
        return color;
    }
}
