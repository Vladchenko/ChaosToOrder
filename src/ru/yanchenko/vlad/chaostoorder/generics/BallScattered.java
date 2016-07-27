/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yanchenko.vlad.chaostoorder.generics;

/**
 *
 * @author Влад
 */
public class BallScattered extends BallDestination {
    
    private double dx = 0;
    private double dy = 0;
    private double angle = 0;
    private double radius = 0;
    
    public BallScattered(int x, int y, boolean random) {
        super(x, y, random);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public double getDx() {
        return dx;
    }
    
    public void setDx(double dx) {
        this.dx = dx;
    }
    
    public double getDy() {
        return dy;
    }
    
    public void setDy(double dy) {
        this.dy = dy;
    }
    
    public double getAngle() {
        return angle;
    }
    
    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
    
    //</editor-fold>
    
}
