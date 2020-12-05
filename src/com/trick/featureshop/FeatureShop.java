package com.trick.featureshop; 

import com.trick.featureshop.tools.*; 

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; import java.util.ArrayList; 

import com.trick.featureshop.tools.Tool; 

import com.trick.featureshop.tools.Rectangle; import com.trick.featureshop.plugins.Plugin; import com.trick.featureshop.plugins.New; import com.trick.featureshop.plugins.Save; import com.trick.featureshop.plugins.Open; import com.trick.featureshop.plugins.Blur; import com.trick.featureshop.plugins.Clear; import com.trick.featureshop.plugins.Undo; 
import com.trick.featureshop.plugins.Redo; import com.trick.featureshop.actions.Action; 
import java.util.List; 

public   class  FeatureShop  implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	

    private final ArrayList<Canvas> canvases = new ArrayList<Canvas>();

	

    private final JTabbedPane canvasPanes = new JTabbedPane();

	

    private final JFrame frame = new JFrame("FeatureShop");

	
	
	private final Toolbar.ToolbarListener listener = new Toolbar.ToolbarListener() {
        @Override
        public void selected(Tool tool) {
            if (actionbar != null) frame.remove(actionbar);

            List<Action> actions = tool.getActions();
            Actionbar replacement = actions.size() > 0 ? new Actionbar(actions) : null;

            if (replacement != null) frame.add(replacement, BorderLayout.PAGE_START);

            actionbar = replacement;
            frame.revalidate();
        }
    };

	

    private final Toolbar toolbar = new Toolbar(getTools(), listener, this);

	
    public FeatureShop  () {
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
        toolbar.setBackground(Color.lightGray);

        addCanvas(new Canvas(256,256, "Untitled"));

        frame.add(canvasPanes, BorderLayout.CENTER);
        frame.add(toolbar, BorderLayout.LINE_END);

        frame.pack();
        frame.setVisible(true);
    
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
        toolbar.setBackground(Color.lightGray);

        frame.add(canvasPanes, BorderLayout.CENTER);
        frame.add(toolbar, BorderLayout.LINE_END);
        frame.setJMenuBar(new Menubar(getPlugins(), this));

        frame.pack();
        frame.setVisible(true);
    }

	
    
     private static ArrayList<Tool>  getTools__wrappee__Base  () {
    	ArrayList<Tool> tools = new ArrayList<Tool>();
    	
    	tools.add(new Pencil());
    	
    	return tools;
    }

	
	 private static ArrayList<Tool>  getTools__wrappee__EyeDropper  () {
    	ArrayList<Tool> tools = getTools__wrappee__Base();
    	
    	tools.add(new EyeDropper());
    	
    	return tools;
    }

	
	 private static ArrayList<Tool>  getTools__wrappee__Line  () {
    	ArrayList<Tool> tools = getTools__wrappee__EyeDropper();
    	
    	tools.add(new Line());
    	
    	return tools;
    }

	
	 private static ArrayList<Tool>  getTools__wrappee__Fill  () {
    	ArrayList<Tool> tools = getTools__wrappee__Line();
    	
    	tools.add(new Fill());
    	
    	return tools;
    }

	
	 private static ArrayList<Tool>  getTools__wrappee__Eraser  () {
    	ArrayList<Tool> tools = getTools__wrappee__Fill();
    	
    	tools.add(new Eraser());
    	
    	return tools;
    }

	
	 private static ArrayList<Tool>  getTools__wrappee__Rectangle  () {
    	ArrayList<Tool> tools = getTools__wrappee__Eraser();
    	
    	tools.add(new Rectangle());
    	
    	return tools;
    }

	
	 private static ArrayList<Tool>  getTools__wrappee__Zoom  () {
    	ArrayList<Tool> tools = getTools__wrappee__Rectangle();
    	
    	tools.add(new Zoom());
    	
    	return tools;
    }

	
	private static ArrayList<Tool> getTools() {
    	ArrayList<Tool> tools = getTools__wrappee__Zoom();
    	
    	tools.add(new Pan());
    	
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

	
    
      static private List<Plugin>  getPlugins__wrappee__Plugins  () {
    	return new ArrayList<Plugin>();
    }

	
	
	 private List<Plugin>  getPlugins__wrappee__New  () {
		List<Plugin> plugins = getPlugins__wrappee__Plugins();
		plugins.add(new New());
		return plugins;
	}

	
	
	 private List<Plugin>  getPlugins__wrappee__Save  () {
		List<Plugin> plugins = getPlugins__wrappee__New();
		plugins.add(new Save());
		return plugins;
	}

	
	
	 private List<Plugin>  getPlugins__wrappee__Open  () {
		List<Plugin> plugins = getPlugins__wrappee__Save();
		plugins.add(new Open());
		return plugins;
	}

	
	
	 private List<Plugin>  getPlugins__wrappee__Blur  () {
		List<Plugin> plugins = getPlugins__wrappee__Open();
		plugins.add(new Blur());
		return plugins;
	}

	
	
	 private List<Plugin>  getPlugins__wrappee__Clear  () {
		List<Plugin> plugins = getPlugins__wrappee__Blur();
		plugins.add(new Clear());
		return plugins;
	}

	
	
	public List<Plugin> getPlugins() {
		List<Plugin> plugins = getPlugins__wrappee__Clear();
		plugins.add(new Undo());
		plugins.add(new Redo());
		return plugins;
	}

	
    
    public JFrame getFrame() {
        return frame;
    }

	
	private Actionbar actionbar = null;


}
