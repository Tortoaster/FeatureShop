package com.trick.featureship;

import javax.swing.*;

public class Menubar extends JMenuBar {

    public Menubar(Plugin[] plugins) {
        JMenu save = new JMenu("Save");
        save.setMnemonic('s');
        add(save);
    }

}
