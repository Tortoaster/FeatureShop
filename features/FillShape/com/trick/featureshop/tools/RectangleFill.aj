package com.trick.featureshop.tools;

import com.trick.featureshop.actions.FillShape;

aspect RectangleFill {
	after(Rectangle tool) : initialization(new()) && target(tool) {
		tool.getActions().add(FillShape.ACTION);
	}
}