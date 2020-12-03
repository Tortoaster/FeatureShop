package com.trick.featureshop; 

import com.trick.featureshop.actions.Action; 

import javax.swing.*; 
import java.util.ArrayList; 

public  class  Actionbar  extends JPanel {
	

    public Actionbar(ArrayList<Action> actions) {
        for (Action a: actions) {
            add(new JLabel(a.getName() + ":"));
            add(a.getComponent());
        }
    }


}
