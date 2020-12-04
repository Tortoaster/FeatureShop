package com.trick.featureshop.tools;

import com.trick.featureshop.actions.ColorPicker;

aspect LineColor {
	after(Line tool) : initialization(new()) && target(tool) {
		tool.getActions().add(ColorPicker.ACTION);
	}
}