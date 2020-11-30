package com.trick.featureship.tools;

import com.trick.featureship.Action;
import com.trick.featureship.Canvas;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class Tool {

    private final String name;

    public Tool(String name) {
        this.name = name;
    }

    public void keyTyped(KeyEvent e, Canvas canvas) {}

    public void keyPressed(KeyEvent e, Canvas canvas) {}

    public void keyReleased(KeyEvent e, Canvas canvas) {}

    public void mouseClicked(MouseEvent e, Canvas canvas) {}

    public void mousePressed(MouseEvent e, Canvas canvas) {}

    public void mouseReleased(MouseEvent e, Canvas canvas) {}

    public void mouseEntered(MouseEvent e, Canvas canvas) {}

    public void mouseExited(MouseEvent e, Canvas canvas) {}

    public void mouseDragged(MouseEvent e, Canvas canvas) {}

    public void mouseMoved(MouseEvent e, Canvas canvas) {}

    public void mouseWheelMoved(MouseWheelEvent e, Canvas canvas) {}

    public String getName() {
        return name;
    }

    abstract public Action[] getActions();

}
