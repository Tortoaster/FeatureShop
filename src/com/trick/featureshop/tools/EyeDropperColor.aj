package com.trick.featureshop.tools;

import com.trick.featureshop.actions.ColorPicker;

aspect EyeDropperColor {
	after(EyeDropper tool) : initialization(new()) && target(tool) {
		tool.getActions().add(ColorPicker.ACTION);
	}
}