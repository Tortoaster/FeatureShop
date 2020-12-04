package com.trick.featureshop.tools;

import com.trick.featureshop.actions.NumberPicker;

aspect LineSize {
	after(Line tool) : initialization(new()) && target(tool) {
		tool.getActions().add(NumberPicker.SIZE);
	}
}