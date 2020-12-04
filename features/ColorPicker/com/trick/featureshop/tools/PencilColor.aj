package com.trick.featureshop.tools;

import com.trick.featureshop.actions.ColorPicker;

aspect PencilColor {
	after(Pencil tool) : initialization(new()) && target(tool) {
		tool.getActions().add(ColorPicker.ACTION);
	}
}