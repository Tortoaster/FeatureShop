package com.trick.featureshop;

import com.trick.featureshop.LayerView;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Canvas extends JPanel {

    public interface ColorCondition {
        boolean accept(Color color);
    }
    
    public static final int PREVIEW_SIZE = 80;

    private int selectedLayer;

    private ArrayList<Color[][]> layers = new ArrayList<Color[][]>();

    private final LayerView layerView = new LayerView(this);
    
    private void init(Color[][] pixels, String name) throws IllegalArgumentException {
    	original(pixels, name);
//        if (pixels.length <= 0 || pixels[0].length <= 0)
//            throw new IllegalArgumentException("width and height must be greater than 0");
//        
//        
//        this.pixels = pixels;
//        preview = emptyPixels();
//        setName(name);
        
        layers.add(pixels);
        layerView.update();
        
        setLayout(new BorderLayout());
        add(layerView, BorderLayout.LINE_END);
    }

    public void point(int x, int y, int radius, Color color, boolean preview) {
        for (int dY = -radius; dY < radius; dY++) {
            for (int dX = -radius; dX < radius; dX++) {
                if (dX * dX + dY * dY < radius * radius) {
                    int fX = x + dX;
                    int fY = y + dY;
                    if (fX >= 0 && fX < canvasWidth && fY >= 0 && fY < canvasHeight) {
                        if (preview) {
                            this.preview[fX][fY] = color;
                        } else {
                            layers.get(selectedLayer)[fX][fY] = color;
                        }
                    }
                }
            }
        }
    }


    public void spread(int x, int y, Color color, ColorCondition condition) {
        if (x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight && condition.accept(layers.get(selectedLayer)[x][y])) {
            layers.get(selectedLayer)[x][y] = color;
            spread(x, y - 1, color, condition);
            spread(x + 1, y, color, condition);
            spread(x, y + 1, color, condition);
            spread(x - 1, y, color, condition);
        }
    }

    public Color eyeDrop(int x, int y) {
        if (x >= 0 && x < canvasWidth && y >= 0 && y < canvasHeight) {
            return layers.get(selectedLayer)[x][y];
        } else {
            return Color.BLACK;
        }
    }

    public void newLayer() {
        layers.add(selectedLayer, emptyPixels());
    }

    public void deleteLayer() {
        if(layers.size() > 1) {
            layers.remove(selectedLayer);
            selectedLayer = Math.max(0, selectedLayer - 1);
        }
    }

    public void moveLayerUp() {
        if(selectedLayer < layers.size() - 1) {
            Color[][] pixels = layers.remove(selectedLayer);
            layers.add(selectedLayer + 1, pixels);
            selectedLayer++;
        }
    }

    public void moveLayerDown() {
        if(selectedLayer > 0) {
            Color[][] pixels = layers.remove(selectedLayer);
            layers.add(selectedLayer - 1, pixels);
            selectedLayer--;
        }
    }

    private void drawImage(Graphics g) {
        for (Color[][] pixels : layers) {
            drawLayer(g, pixels);
        }
        drawLayer(g, preview);
    }

    public int countLayers() {
        return layers.size();
    }

    public BufferedImage toBufferedImage() {
        BufferedImage image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        for(Color[][] pixels : layers) {
            for (int y = 0; y < canvasHeight; y++) {
                for (int x = 0; x < canvasWidth; x++) {
                    g.setColor(pixels[x][y]);
                    g.drawLine(x, y, x, y);
                }
            }
        }

        g.dispose();
        return image;
    }

    public BufferedImage preview(int layer) {
        BufferedImage image = new BufferedImage(PREVIEW_SIZE, PREVIEW_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        Color[][] pixels = layers.get(layer);

        for (int y = 0; y < PREVIEW_SIZE; y++) {
            for (int x = 0; x < PREVIEW_SIZE; x++) {
                g.setColor(pixels[(int) ((float) x / PREVIEW_SIZE * canvasWidth)][(int) ((float) y / PREVIEW_SIZE * canvasHeight)]);
                g.drawLine(x, y, x, y);
            }
        }

        g.dispose();
        return image;
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
    	layerView.update();
    }
    
    private void doRedo() {
        original();
        layerView.update();
    }
    
    private void doUndo() {
        original();
        layerView.update();
    }
    
    public void save() {
        original();
        layerView.update();
    }
    
    private ArrayList<Color[][]> getLayers() {
    	return layers;
    }
    
    public Color[][] getPixels() {
    	return layers.get(selectedLayer);
    }
    
    public void setPixels(Color[][] pixels) {
    	layers.set(selectedLayer, pixels);
    }
    
    private void setLayers(ArrayList<Color[][]> l) {
    	this.layers = l;
    }
    
    public int getSelectedLayer() {
    	return selectedLayer;
    }
    
    public void setSelectedLayer(int selectedLayer) {
    	this.selectedLayer = selectedLayer;
    }

}
