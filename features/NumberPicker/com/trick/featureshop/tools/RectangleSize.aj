package com.trick.featureshop.tools;

import com.trick.featureshop.actions.NumberPicker;

aspect RectangleSize {
	after(Rectangle tool) : initialization(new()) && target(tool) {
		tool.getActions().add(NumberPicker.SIZE);
	}
}