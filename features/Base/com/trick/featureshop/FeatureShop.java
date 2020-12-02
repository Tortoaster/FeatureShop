package com.trick.featureshop;

import com.trick.featureshop.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FeatureShop implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private final ArrayList<Canvas> canvases = new ArrayList<Canvas>();

    private final JTabbedPane canvasPanes = new JTabbedPane();

    private final JFrame frame = new JFrame("FeatureShop");

    private final Toolbar.ToolbarListener listener = new Toolbar.ToolbarListener() {
        @Override
        public void selected(Tool tool) {
            frame.revalidate();
        }
    };

    private final Toolbar toolbar = new Toolbar(getTools(), listener, this);

    public FeatureShop() {
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
        toolbar.setBackground(Color.lightGray);

        addCanvas(new Canvas(256,256, "Untitled"));

        frame.add(canvasPanes, BorderLayout.CENTER);
        frame.add(toolbar, BorderLayout.LINE_END);

        frame.pack();
        frame.setVisible(true);
    }
    
    private static ArrayList<Tool> getTools() {
    	ArrayList<Tool> tools = new ArrayList<Tool>();
    	
    	tools.add(new Pencil());
    	
    	return tools;
    }

    public void addCanvas(Canvas canvas) {
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);

        canvases.add(canvas);
        canvasPanes.addTab(canvas.getName(), canvas);
    }

    public Canvas getCanvas() {
        int index = canvasPanes.getSelectedIndex();
        if(index > -1) {
            return canvases.get(index);
        } else {
            return null;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(getCanvas() != null) {
            toolbar.getActiveTool().keyTyped(e, getCanvas());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(getCanvas() != null) {
            toolbar.getActiveTool().keyPressed(e, getCanvas());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(getCanvas() != null) {
            toolbar.getActiveTool().keyReleased(e, getCanvas());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(getCanvas() != null) {
            toolbar.getActiveTool().mouseClicked(e, getCanvas());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(getCanvas() != null) {
            toolbar.getActiveTool().mousePressed(e, getCanvas());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(getCanvas() != null) {
            toolbar.getActiveTool().mouseReleased(e, getCanvas());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(getCanvas() != null) {
            toolbar.getActiveTool().mouseEntered(e, getCanvas());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(getCanvas() != null) {
            toolbar.getActiveTool().mouseExited(e, getCanvas());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(getCanvas() != null) {
            toolbar.getActiveTool().mouseDragged(e, getCanvas());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(getCanvas() != null) {
            toolbar.getActiveTool().mouseMoved(e, getCanvas());
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(getCanvas() != null) {
            toolbar.getActiveTool().mouseWheelMoved(e, getCanvas());
        }
    }

}
