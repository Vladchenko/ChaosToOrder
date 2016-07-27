/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yanchenko.vlad.chaostoorder.generics;

import java.awt.image.BufferedImage;

/**
 *
 * @author Влад
 */
public class BallDestination extends Dot {
    
    //** Reference to an image that ball is to have
    private BufferedImage image;

    public BallDestination(int x, int y, boolean random) {
        super(x, y, random);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    
    /**
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }
    
    /**
     * @param image the image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    //</editor-fold>
    
}
