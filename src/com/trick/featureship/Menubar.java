package com.trick.featureship;

import com.trick.featureship.plugins.Plugin;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Menubar extends JMenuBar {

    public Menubar(Plugin[] plugins, FeatureShop shop) {
        for (Plugin p: plugins) {
            JMenu menu = new JMenu(p.getName());
            menu.addMenuListener(new MenuListener() {
                @Override
                public void menuSelected(MenuEvent e) {
                    p.buttonPressed(e, shop, menu);
                }

                @Override
                public void menuDeselected(MenuEvent e) {

                }

                @Override
                public void menuCanceled(MenuEvent e) {

                }
            });
            add(menu);
        }
    }

}
