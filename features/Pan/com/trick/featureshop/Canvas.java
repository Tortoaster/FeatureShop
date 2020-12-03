package com.trick.featureshop;

public class Canvas extends JPanel {
	
	private int panX = 0, panY = 0;
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        float imageWidth = scale * canvasWidth;
        float imageHeight = scale * canvasHeight;

        left = (int) (getWidth() - imageWidth) / 2 + panX;
        top = (int) (getHeight() - imageHeight) / 2 + panY;

        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.clearRect(left, top, (int) imageWidth, (int) imageHeight);

        drawImage(g);

        g.setColor(BACKGROUND);
        g.drawRect(left - 1, top - 1, (int) imageWidth + 1, (int) imageHeight + 1);
    }
	
	public void setPanX(int panX) {
        this.panX = panX;
    }

    public void setPanY(int panY) {
        this.panY = panY;
    }
    
    public int getPanX() {
        return panX;
    }

    public int getPanY() {
        return panY;
    }
}
