package com.trick.featureshop;

import com.trick.featureshop.plugins.Plugin;
import com.trick.featureshop.plugins.Redo;
import com.trick.featureshop.plugins.Undo;

import javax.swing.*;

public class Menubar extends JMenuBar {

    public Menubar(Plugin[] plugins, FeatureShop shop) {
        JMenu pluginsMenu = new JMenu("Plugins");
        JMenu historyMenu = new JMenu("History");

        for (Plugin p : plugins) {
            JMenuItem menuItem = new JMenuItem(p.getName());
            menuItem.addActionListener(e -> p.buttonPressed(e, shop));
            menuItem.setAccelerator(KeyStroke.getKeyStroke(p.shortcut()));
            pluginsMenu.add(menuItem);
        }

        for (Plugin p : new Plugin[]{new Undo(), new Redo()}) {
            JMenuItem menuItem = new JMenuItem(p.getName());
            menuItem.addActionListener(e -> p.buttonPressed(e, shop));
            menuItem.setAccelerator(KeyStroke.getKeyStroke(p.shortcut()));
            historyMenu.add(menuItem);
        }

        add(pluginsMenu);
        add(historyMenu);
    }

}
