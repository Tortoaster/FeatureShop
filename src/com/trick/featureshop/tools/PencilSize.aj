package com.trick.featureshop.tools;

import com.trick.featureshop.actions.NumberPicker;

aspect PencilSize {
	after(Pencil tool) : initialization(new()) && target(tool) {
		tool.getActions().add(NumberPicker.SIZE);
	}
}
