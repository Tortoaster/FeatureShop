package com.trick.featureshop;

import com.trick.featureshop.actions.Action;
import com.trick.featureshop.actions.ColorPicker;
import com.trick.featureshop.actions.NumberPicker;
import com.trick.featureshop.plugins.Plugin;
import com.trick.featureshop.plugins.Save;
import com.trick.featureshop.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FeatureShop implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private static final ColorPicker COLOR = new ColorPicker("Color", Color.BLACK);
    private static final NumberPicker SIZE = new NumberPicker("Size", 1, 10, 1);

    private static final Tool[] TOOLS = new Tool[]{new Zoom(), new Pan(), new Pencil(COLOR, SIZE), new Eraser(SIZE), new Line(COLOR, SIZE)};

    private static final Plugin[] PLUGINS = new Plugin[]{new Save()};

    private final Canvas canvas = new Canvas(128, 128);

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

        frame.add(canvas, BorderLayout.CENTER);
        frame.add(toolbar, BorderLayout.LINE_END);
        frame.setJMenuBar(new Menubar(PLUGINS, this));

        frame.pack();
        frame.setVisible(true);

        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);
    }

    public JFrame getFrame() { return frame; }

    public Canvas getCanvas() { return canvas; }

    @Override
    public void keyTyped(KeyEvent e) {
        toolbar.getActiveTool().keyTyped(e, canvas);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toolbar.getActiveTool().keyPressed(e, canvas);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toolbar.getActiveTool().keyReleased(e, canvas);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        toolbar.getActiveTool().mouseClicked(e, canvas);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        toolbar.getActiveTool().mousePressed(e, canvas);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        toolbar.getActiveTool().mouseReleased(e, canvas);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        toolbar.getActiveTool().mouseEntered(e, canvas);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        toolbar.getActiveTool().mouseExited(e, canvas);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        toolbar.getActiveTool().mouseDragged(e, canvas);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        toolbar.getActiveTool().mouseMoved(e, canvas);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        toolbar.getActiveTool().mouseWheelMoved(e, canvas);
    }
}
