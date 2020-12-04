package com.trick.featureshop.tools;

import com.trick.featureshop.actions.ColorPicker;

aspect RectangleColor {
	after(Rectangle tool) : initialization(new()) && target(tool) {
		tool.getActions().add(ColorPicker.ACTION);
	}
}