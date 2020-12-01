package com.trick.featureshop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LayerView extends JPanel {

    private int number = 0;

    private final JPanel layers = new JPanel();
    private final Canvas canvas;

    public LayerView(Canvas canvas) {
        this.canvas = canvas;

        JPanel controls = new JPanel(new GridLayout(2, 2));
        JButton newLayer = new JButton("+");
        JButton deleteLayer = new JButton("-");
        JButton up = new JButton("^");
        JButton down = new JButton("v");

        newLayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.newLayer();
                canvas.repaint();
                update();
            }
        });

        deleteLayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.deleteLayer();
                canvas.repaint();
                update();
            }
        });

        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.moveLayerUp();
                canvas.repaint();
                update();
            }
        });

        down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.moveLayerDown();
                canvas.repaint();
                update();
            }
        });

        controls.add(newLayer);
        controls.add(deleteLayer);
        controls.add(up);
        controls.add(down);

        layers.setLayout(new BoxLayout(layers, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());

        add(controls, BorderLayout.PAGE_START);
        add(layers, BorderLayout.CENTER);

        update();
    }

    private void update() {
        layers.removeAll();

        for(int i = canvas.countLayers() - 1; i >= 0; i--) {
            JButton button = new JButton();
            button.setIcon(new ImageIcon(canvas.preview(i)));

            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvas.setSelectedLayer(index);
                }
            });

            layers.add(button);

            if(i == canvas.getSelectedLayer()) button.setSelected(true);
        }

        layers.revalidate();
    }
}
