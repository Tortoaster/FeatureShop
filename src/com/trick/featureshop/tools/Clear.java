package com.trick.featureshop.tools;

import com.trick.featureshop.Canvas;
import com.trick.featureshop.actions.Action;

import java.awt.event.MouseEvent;

public class Clear extends Tool {
    @Override
    public String getName() {
        return "Clear";
    }

    @Override
    public Action[] getActions() {
        return new Action[0];
    }

    @Override
    public void selected(Canvas canvas) {
        canvas.clear();
        canvas.save();
    }

    @Override
    public String getIconName() {
        return "turn";
    }
}
