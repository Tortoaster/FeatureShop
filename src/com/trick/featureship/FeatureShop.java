package com.trick.featureship;

import com.trick.featureship.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FeatureShop implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private static final Colorpicker COLORPICKER = new Colorpicker(Color.BLACK);

    private static final Tool[] TOOLS = new Tool[]{new Zoom(), new Pan(), new Pencil(COLORPICKER), new Line(COLORPICKER)};

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
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
        toolbar.setBackground(Color.lightGray);

        frame.add(canvas, BorderLayout.CENTER);
        frame.add(toolbar, BorderLayout.LINE_END);

        frame.pack();
        frame.setVisible(true);

        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);
    }

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
