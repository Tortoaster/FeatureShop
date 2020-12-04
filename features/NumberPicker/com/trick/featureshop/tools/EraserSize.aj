package com.trick.featureshop.tools;

import com.trick.featureshop.actions.NumberPicker;

aspect EraserSize {
	after(Eraser tool) : initialization(new()) && target(tool) {
		tool.getActions().add(NumberPicker.SIZE);
	}
}