import com.trick.featureshop.plugins.Plugin;

public class FeatureShop {
    public FeatureShop() {
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
    
    private static List<Plugin> getPlugins() {
    	return new ArrayList<Plugin>();
    }
    
    public JFrame getFrame() {
        return frame;
    }
    
}