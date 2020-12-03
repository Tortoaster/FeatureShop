package com.trick.featureshop;

public class Canvas {
	public static final int MAX_ZOOM = 50;
	
	private float scale = 1;
	
	public int screenToCanvasX(int x) {
        return (int) ((x - left) / scale);
    }

    public int screenToCanvasY(int y) {
        return (int) ((y - top) / scale);
    }
    
    private void drawLayer(Graphics g, Color[][] layer) {
        for (int y = 0; y < canvasHeight; y++) {
            for (int x = 0; x < canvasWidth; x++) {
                g.setColor(layer[x][y]);
                g.fillRect((int) (x * scale) + left, (int) (y * scale) + top, (int) scale + 1, (int) scale + 1);
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        float imageWidth = scale * canvasWidth;
        float imageHeight = scale * canvasHeight;

        left = (int) (getWidth() - imageWidth) / 2;
        top = (int) (getHeight() - imageHeight) / 2;

        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.clearRect(left, top, (int) imageWidth, (int) imageHeight);

        drawImage(g);

        g.setColor(BACKGROUND);
        g.drawRect(left - 1, top - 1, (int) imageWidth + 1, (int) imageHeight + 1);
    }
    
    public void setScale(float scale) {
        this.scale = scale;
    }
    
    public float getScale() {
        return scale;
    }
}