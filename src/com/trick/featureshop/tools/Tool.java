package com.trick.featureshop.tools;

import com.trick.featureshop.Canvas;
import com.trick.featureshop.actions.Action;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface Tool {

    String getName();

    Action[] getActions();

    default void keyTyped(KeyEvent e, Canvas canvas) {
    }

    default void keyPressed(KeyEvent e, Canvas canvas) {
    }

    default void keyReleased(KeyEvent e, Canvas canvas) {
    }

    default void mouseClicked(MouseEvent e, Canvas canvas) {
    }

    default void mousePressed(MouseEvent e, Canvas canvas) {
    }

    default void mouseReleased(MouseEvent e, Canvas canvas) {
    }

    default void mouseEntered(MouseEvent e, Canvas canvas) {
    }

    default void mouseExited(MouseEvent e, Canvas canvas) {
    }

    default void mouseDragged(MouseEvent e, Canvas canvas) {
    }

    default void mouseMoved(MouseEvent e, Canvas canvas) {
    }

    default void mouseWheelMoved(MouseWheelEvent e, Canvas canvas) {
    }
}
