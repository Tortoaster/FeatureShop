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

public class Save implements Plugin {

    @Override
    public String getName() {
        return "Save";
    }

    @Override
    public String shortcut() { return "control S"; }

    @Override
    public void buttonPressed(ActionEvent event, FeatureShop shop) {
        Canvas canvas = shop.getCanvas();

        if(canvas == null) return;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
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

        int userSelection = fileChooser.showSaveDialog(shop.getFrame());

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());

            try {
                ImageIO.write(canvas.toBufferedImage(), fileToSave.getName().endsWith("png") ? "png" : "jpg", fileToSave);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
