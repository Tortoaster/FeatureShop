package com.trick.featureshop;

import com.trick.featureshop.actions.Action;
import com.trick.featureshop.actions.ColorPicker;
import com.trick.featureshop.actions.NumberPicker;
import com.trick.featureshop.plugins.Plugin;
import com.trick.featureshop.plugins.*;
import com.trick.featureshop.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FeatureShop implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private static final ColorPicker COLOR = new ColorPicker("Color", Color.BLACK);
    private static final NumberPicker SIZE = new NumberPicker("Size", 1, 10, 1);

    private static final Tool[] TOOLS = new Tool[]{new Zoom(), new Pan(), new Pencil(COLOR, SIZE), new Eraser(SIZE), new Line(COLOR, SIZE), new Fill(COLOR), new EyeDropper(COLOR)};

    private static final Plugin[] PLUGINS = new Plugin[]{new New(), new Save(), new Open()};

    private final ArrayList<Canvas> canvases = new ArrayList<>();

    private final JTabbedPane canvasPanes = new JTabbedPane();

    private final JFrame frame = new JFrame("FeatureShop");

    private final Toolbar.ToolbarListener listener = tool -> {
        if (this.actionbar != null) frame.remove(this.actionbar);

        Action[] actions = tool.getActions();
        Actionbar actionbar = actions.length > 0 ? new Actionbar(actions, this) : null;

        if (actionbar != null) frame.add(actionbar, BorderLayout.PAGE_START);

        this.actionbar = actionbar;
        frame.revalidate();
    };

    private final Toolbar toolbar = new Toolbar(TOOLS, this, listener);

    private Actionbar actionbar = null;

    public FeatureShop() {
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
        toolbar.setBackground(Color.lightGray);

        frame.add(canvasPanes, BorderLayout.CENTER);
        frame.add(toolbar, BorderLayout.LINE_END);
        frame.setJMenuBar(new Menubar(PLUGINS, this));

        frame.pack();
        frame.setVisible(true);
    }

    public void addCanvas(Canvas canvas) {
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);

        canvases.add(canvas);
        canvasPanes.addTab("Untitled", canvas);
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

    public JFrame getFrame() {
        return frame;
    }
}
