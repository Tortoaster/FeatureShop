package com.trick.featureshop.plugins; 

import com.trick.featureshop.Canvas; 
import com.trick.featureshop.FeatureShop; 

import javax.imageio.ImageIO; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.ActionEvent; 
import java.awt.image.BufferedImage; 
import java.io.File; 
import java.io.IOException; 

public  class  Open  implements Plugin {
	

    @Override
    public String getName() { return "Open"; }

	

    @Override
    public String shortcut() { return "control O"; }

	

    @Override
    public Type getType() {
        return Type.FILE;
    }

	

    @Override
    public void buttonPressed(ActionEvent event, FeatureShop shop) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image to open.");
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".jpg") || filename.endsWith(".jpeg") ;
                }
            }

            @Override
            public String getDescription() {
                return "JPG Images (*.jpg)";
            }
        });
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".png");
                }
            }

            @Override
            public String getDescription() {
                return "PNG Images (*.png)";
            }
        });

        int userSelection = fileChooser.showOpenDialog(shop.getFrame());

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            System.out.println("Open file: " + fileToOpen.getAbsolutePath());

            try {
                BufferedImage image = ImageIO.read(fileToOpen);

                Color[][] pixels = new Color[image.getWidth()][image.getHeight()];

                for(int y = 0; y < image.getHeight(); y++) {
                    for(int x = 0; x < image.getWidth(); x++) {
                        pixels[x][y] = new Color(image.getRGB(x, y));
                    }
                }

                shop.addCanvas(new Canvas(pixels, fileToOpen.getName()));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
