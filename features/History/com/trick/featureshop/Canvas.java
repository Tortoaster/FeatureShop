package com.trick.featureshop;

public class Canvas extends JPanel {
	
    public static final int MAX_HISTORY_SIZE = 50;
	
    private int historyIndex = 0;
	
    private final ArrayList<ArrayList<Color[][]>> history = new ArrayList<ArrayList<Color[][]>>();
    
    private void init(Color[][] pixels, String name) throws IllegalArgumentException {
    	original(pixels, name);
        history.add(cloneLayers(getLayers()));
    }
	
    public void undo() {
        if (historyIndex > 0) {
        	doUndo();
        }
    }
    
    private void doUndo() {
        historyIndex--;
        setLayers(cloneLayers(history.get(historyIndex)));
        repaint();
    }

    public void redo() {
        if (historyIndex < history.size() - 1) {
        	doRedo();
        }
    }
    
    private void doRedo() {
        historyIndex++;
        setLayers(cloneLayers(history.get(historyIndex)));
        repaint();
    }

    public void save() {
        history.add(historyIndex + 1, cloneLayers(getLayers()));
        if (history.size() > MAX_HISTORY_SIZE) {
            history.remove(0);
        } else {
            historyIndex++;
        }
    }
    
    private ArrayList<Color[][]> cloneLayers(ArrayList<Color[][]> layers) {
        ArrayList<Color[][]> clone = new ArrayList<Color[][]>();

        for (Color[][] p : layers) {
            Color[][] clonePixels = new Color[canvasWidth][canvasHeight];
            for (int x = 0; x < canvasWidth; x++) {
                if (canvasHeight >= 0) System.arraycopy(p[x], 0, clonePixels[x], 0, canvasHeight);
            }
            clone.add(clonePixels);
        }

        return clone;
    }
    
    public void onChange() {
    	original();
    	save();
    }
    

}
