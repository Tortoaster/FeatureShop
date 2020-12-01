package com.trick.featureshop.plugins;

import com.trick.featureshop.Canvas;
import com.trick.featureshop.FeatureShop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class New implements Plugin {

    @Override
    public String getName() { return "New"; }

    @Override
    public String shortcut() { return "control N"; }

    @Override
    public void buttonPressed(ActionEvent event, FeatureShop shop) {
        JFrame frame = shop.getFrame();

        final JDialog d = new JDialog(frame, "New Canvas", true);
        d.setSize(new Dimension(300, 130));
        d.setResizable(false);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel widthLabel = new JLabel("Width");
        JLabel heightLabel = new JLabel("Height");

        SpinnerModel widthModel = new SpinnerNumberModel(128, 1, 1024, 1);
        SpinnerModel heightModel = new SpinnerNumberModel(128, 1, 1024, 1);

        JSpinner widthSpinner = new JSpinner(widthModel);
        JSpinner heightSpinner = new JSpinner(heightModel);

        widthLabel.setLabelFor(widthSpinner);
        heightLabel.setLabelFor(heightSpinner);

        JButton button = new JButton("Create");
        button.addActionListener(e -> {
            shop.addCanvas(new Canvas((int) widthSpinner.getValue(), (int) heightSpinner.getValue()));
            d.dispose();
        });

        JPanel widthPanel = new JPanel();
        widthPanel.add(widthLabel);
        widthPanel.add(widthSpinner);

        JPanel heightPanel = new JPanel();
        heightPanel.add(heightLabel);
        heightPanel.add(heightSpinner);

        panel.add(widthPanel);
        panel.add(heightPanel);
        panel.add(button);

        d.add(panel);
        d.getRootPane().setDefaultButton(button);
        d.setLocationRelativeTo(frame);
        d.setVisible(true);
    }

}
