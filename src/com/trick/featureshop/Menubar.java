package com.trick.featureshop;

import com.trick.featureshop.plugins.Plugin;
import com.trick.featureshop.plugins.Redo;
import com.trick.featureshop.plugins.Type;
import com.trick.featureshop.plugins.Undo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Menubar extends JMenuBar {

    public Menubar(Plugin[] plugins, FeatureShop shop) {

        for (Type t : Type.values()) {
            ArrayList<Plugin> filteredPlugins = new ArrayList<Plugin>();
            for (Plugin p : plugins) {
                if (p.getType() == t) {
                    filteredPlugins.add(p);
                }
            }

            if (filteredPlugins.size() > 0) {
                JMenu menu = new JMenu(t.toString());

                for (Plugin p : filteredPlugins) {
                    JMenuItem menuItem = new JMenuItem(p.getName());
                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            p.buttonPressed(e, shop);
                        }
                    });
                    menuItem.setAccelerator(KeyStroke.getKeyStroke(p.shortcut()));
                    menu.add(menuItem);
                }

                add(menu);
            }
        }

    }

}
