package com.trick.featureshop.tools;

import com.trick.featureshop.actions.ColorPicker;

aspect FillColor {
	after(Fill tool) : initialization(Fill.new()) && target(tool) {
		tool.getActions().add(ColorPicker.ACTION);
	}
}