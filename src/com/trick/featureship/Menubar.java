package com.trick.featureship;

import com.trick.featureship.plugins.Plugin;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Menubar extends JMenuBar {

    public Menubar(Plugin[] plugins, FeatureShop shop) {
        JMenu menu = new JMenu("Plugins");

        for (Plugin p : plugins) {
            JMenuItem menuItem = new JMenuItem(p.getName());
            menuItem.addActionListener(e -> p.buttonPressed(e, shop));
            menuItem.setMnemonic(KeyEvent.VK_S);
            menu.add(menuItem);
        }

        add(menu);
    }

}
