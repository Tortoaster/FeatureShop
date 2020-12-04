package com.trick.featureshop.tools;

import com.trick.featureshop.actions.NumberPicker;
import com.trick.featureshop.actions.Action;
import java.awt.*;

aspect NumberAspect {
	int around(Tool tool) : call(int getRadius()) && target(tool) {
		for(Action a: tool.getActions()) {
			if(a.getName().equals(NumberPicker.SIZE.getName())) {
				return ((NumberPicker) a).getNumber();
			}
		}
		return 1;
	}
}
