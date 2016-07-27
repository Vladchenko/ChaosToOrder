/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yanchenko.vlad.chaostoorder.generics;

import java.awt.Point;

/**
 * Class in charge of a data related to a balls, but not directly belongs
 * to. Balls might have dx, dy - the values that reflect a speed the ball goes
 * at on a screen while converging. radius - is actually a distance between one
 * selected ball and the others. It is used while rotating a balls around a
 * selected one. distance - distance between a scattered ball and its respective
 * destination ball.
 *
 * @author v.yanchenko
 */
public class BallsMetaData {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    static boolean convergeAtOnce = true;
    private static Point mousePos = new Point();
    private static Point mousePosDifference = new Point();
    private double dx = 0;
    private double dy = 0;
    private double angle = 0;
    private double radius = 0;
    private double distance = 0;
//    private boolean selected = false;
    //</editor-fold>

    public BallsMetaData() {
    } 

    //<editor-fold defaultstate="collapsed" desc="Setters & Getters">

    /**
     * @return the mousePos
     */
    public static Point getMousePos() {
        return mousePos;
    }

    /**
     * @param aMousePos the mousePos to set
     */
    public static void setMousePos(Point aMousePos) {
        mousePos = aMousePos;
    }

    /**
     * @return the mousePosDifference
     */
    public static Point getMousePosDifference() {
        return mousePosDifference;
    }

    /**
     * @param aMousePosDifference the mousePosDifference to set
     */
    public static void setMousePosDifference(Point aMousePosDifference) {
        mousePosDifference = aMousePosDifference;
    }
    
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getdx() {
        return dx;
    }

    public void setdx(double dx) {
        this.dx = dx;
    }

    public double getdy() {
        return dy;
    }

    public void setdy(double dy) {
        this.dy = dy;
    }

//    public boolean isSelected() {
//        return selected;
//    }
//
//    public void setSelected(boolean selected) {
//        this.selected = selected;
//    }
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
