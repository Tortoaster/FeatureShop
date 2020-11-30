package com.trick.featureshop;

import com.trick.featureshop.plugins.Plugin;

import javax.swing.*;

public class Menubar extends JMenuBar {

    public Menubar(Plugin[] plugins, FeatureShop shop) {
        JMenu menu = new JMenu("Plugins");

        for (Plugin p : plugins) {
            JMenuItem menuItem = new JMenuItem(p.getName());
            menuItem.addActionListener(e -> p.buttonPressed(e, shop));
            menuItem.setAccelerator(KeyStroke.getKeyStroke(p.shortcut()));
            menu.add(menuItem);
        }

        add(menu);
    }

}
