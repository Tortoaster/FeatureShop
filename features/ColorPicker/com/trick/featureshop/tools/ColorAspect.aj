package com.trick.featureshop.tools;

import com.trick.featureshop.actions.ColorPicker;
import com.trick.featureshop.actions.Action;
import java.awt.*;

aspect ColorAspect {
	Color around(Tool tool) : call(Color getColor()) && target(tool) {
		for(Action a: tool.getActions()) {
			if(a.getName().equals(ColorPicker.ACTION.getName())) {
				return ((ColorPicker) a).getColor();
			}
		}
		return Color.BLACK;
	}
}
