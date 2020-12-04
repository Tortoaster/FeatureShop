package com.trick.featureshop.tools;

import com.trick.featureshop.actions.FillShape;
import com.trick.featureshop.actions.Action;
import java.awt.*;

aspect FillAspect {
	boolean around(Tool tool) : call(boolean getFill()) && target(tool) {
		for(Action a: tool.getActions()) {
			if(a.getName().equals(FillShape.ACTION.getName())) {
				return ((FillShape) a).getFill();
			}
		}
		return false;
	}
}
