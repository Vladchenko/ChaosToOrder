/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yanchenko.vlad.chaostoorder.generics;

import java.awt.image.BufferedImage;

/**
 * This class holds everything related to a ball. Arrays of balls to be drawn on
 * a screen - scattered and destination balls and a selected ball (the one that
 * user to click on).
 *
 * @author v.yanchenko
 */
public class Balls {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    //** Used to place a balls using a specific figure
    private int radius = 350;
    //** An extra radius, for some figure of balls
    private int radiusFloating = 30;
    //** Kind of a figure made of a destination balls
    private int destDotsFigureKind = 0;
    private int scatterMode = 0;
    /**
     * Step for balls placed in a figure. Depends on a balls quantity, so
     * defined later in a constructor.
     */
    //    private double stepPI;
    private double angle;
    //** An offset on a screen for a destination balls
    private double anglDestDotsShift = 0;
    /**
     * Used to define a shift of a mouse cursor and a ball, while dragging this
     * ball
     */
    private double offsetX = -1;
    private double offsetY = -1;
    private double angleStep = 0.05;
    //** A ball that user is to select by clicking a mouse on.
    private BallScattered ballSelected;
    //** Dummy ball.
    private BallScattered ballDummy;
    //** This is a field that holds a destination balls.
    private BallDestination[] ballsDestination;
    //** This is a field that holds a scattered balls and methods to operate with.
    private BallScattered[] ballsScattered;
    private BallsMetaData[] ballsMetaData;
    //</editor-fold>

    public Balls(int screenWidth,
            int screenHeight,
            int number,
            BallsImages ballsImages) {
        /**
         * Dummy ball is always located at the end of a screen, for it could not
         * be seen.
         */
        ballDummy = new BallScattered(screenWidth, screenHeight, false);
        ballDummy.setImage(new BufferedImage(1, 1, 1));
        /**
         * Initially, there is no selected ball, so "ballSelected" refers to a
         * dummyBall.
         */
        ballSelected = ballDummy;
        ballsDestination = new BallDestination[number];
        ballsScattered = new BallScattered[number];
        ballsMetaData = new BallsMetaData[number];
        for (int i = 0; i < number; i++) {
            //** Instantiating a meta data for balls
            ballsMetaData[i] = new BallsMetaData();
            //** Instantiating a destination balls
            ballsDestination[i] = new BallDestination(screenWidth,
                    screenHeight, false);
            //** Instantiating a scattered balls
            ballsScattered[i] = new BallScattered(screenWidth, screenHeight,
                    true);
            //** Setting images of balls to their respective representations
            ballsDestination[i].setImage(ballsImages.getImgDestination());
            ballsScattered[i].setImage(ballsImages.getImgScattered());
        }
        
        scatterMode = 8;
        //** Scattering a scattered balls
        scatterTheBalls(ballsScattered, 0, screenWidth, screenHeight, 
                ballsImages.getImgScattered(), false);
        
        scatterMode = 5;
        //** Scattering a destination balls
        scatterTheBalls(ballsDestination, 0, screenWidth, screenHeight, 
                ballsImages.getImgDestination(), false);
        
//        scatterMode = 8;
        
        //** Compute metadata, in case "after convergence" process is not running
        computeMetaData(true);
//        printMetaData();

    }

    /**
     * Generating a coordinates of scattered dots at random with a specific
     * shape.
     */
    public void scatterTheBalls(Dot[] balls, double angle,
            int screenWidth, int screenHeight,
            BufferedImage image, boolean converge) {

        double maxDistance = 0;
        double screenRatio;
        double dotsQuantityRoot;
        int columnQuantity;
        int rowQuantity = 0;
        int rad = radius / 6;
        double xDelta = 0;
        double yDelta = 0;
        double incXDelta = 0;
        double incYDelta = 0;
        double stepPI = 2 * Math.PI / balls.length;
        double angleStep2 = 0;
        double step = 0.02095;
        double angleStep = (getDestDotsFigureKind() == 25) ? Math.PI : 0;
        double radius2 = 20;
        
//        {angleStep += angle;}

        if (scatterMode > 0) {
            screenRatio = screenWidth / screenHeight;
            dotsQuantityRoot = Math.sqrt(ballsScattered.length);
            columnQuantity = (int) (dotsQuantityRoot * screenRatio);
            rowQuantity = (int) (dotsQuantityRoot / screenRatio);
            if (columnQuantity * rowQuantity > ballsScattered.length) {
                columnQuantity--;
            }
            xDelta = screenWidth / columnQuantity;
            yDelta = screenHeight / rowQuantity;
            incXDelta = xDelta / 2;
            incYDelta = yDelta / 2;
//            if (scatterMode == 2) {
//                incYDelta = 0;
//            }
//
//            if (scatterMode == 5) {
//                incYDelta = yDelta / 8;
//                incXDelta = screenWidth / 2;
//            }
//            if (scatterMode == 4) {
//                incXDelta = screenWidth / 2;
//            }

        }

        for (int i = 0; i < balls.length; i++) {

            switch (scatterMode) {
                //** Balls scattered at random
                case 0: {
                    balls[i].setX((Math.random()
                            * (screenWidth - image.getWidth())));
                    balls[i].setY((Math.random()
                            * (screenHeight - image.getHeight())));
                    break;
                }
                //** Wall of a balls
                case 1: {
                    if (i % rowQuantity == 0) {
                        if (i != 0) {
                            incYDelta += yDelta;
                        }
                        incXDelta = xDelta / 2;
                    }
                    balls[i].setX(incXDelta + Math.cos(stepPI
                            + getAnglDestDotsShift() * 3) * 20);
                    balls[i].setY(incYDelta + Math.sin(stepPI
                            + getAnglDestDotsShift() * 3) * 20);
                    incXDelta += xDelta;
                    break;
                }
                //** Curvy wall of a balls
                case 2: {
                    balls[i].setX(incXDelta + xDelta * Math.sin(stepPI
                            + getAnglDestDotsShift() * 8) / 2);
                    balls[i].setY(incYDelta + yDelta * Math.cos(stepPI
                            + getAnglDestDotsShift() * 8) / 2);
                    incXDelta += xDelta;
                    stepPI += 0.02;
                    if (i % rowQuantity == 0
                            && i > 0) {
                        incYDelta += yDelta;
                        incXDelta = xDelta / 2;
                    }
                    break;
                }
                //** Several curved lines
                case 3: {
                    angleStep2 += stepPI;
                    balls[i].setX(Math.tan(angleStep2 + angleStep2 / 2 
                            + getAnglDestDotsShift()) * radius + screenWidth / 2);
                    balls[i].setY(Math.tan(angle + angleStep2 * 4 
                            + getAnglDestDotsShift()) * radius + screenHeight / 2);
                    if (balls[i].getX() > screenWidth
                            || balls[i].getX() < 0) {
                        balls[i].setX(screenWidth / 2);
                        try {
                            balls[i].setX(balls[i - 1].getX());
                            balls[i].setY(balls[i - 1].getY());
                            break;
                        } catch (Exception e) {
                            balls[i].setX(balls[i + 1].getX());
                            balls[i].setY(balls[i + 1].getY());
                        }
                    }
                    if (balls[i].getY() > screenHeight
                            || balls[i].getY() < 0) {
                        balls[i].setY(screenHeight / 2);
                        try {
                            balls[i].setX(balls[i - 1].getX());
                            balls[i].setY(balls[i - 1].getY());
                            break;
                        } catch (Exception e) {
                            balls[i].setX(balls[i + 1].getX());
                            balls[i].setY(balls[i + 1].getY());
                        }
                    }
                    break;
                }                    
                //** Circle
                case 4: {
                    angle += stepPI;
                    balls[i].setX(Math.cos(angle + getAnglDestDotsShift()) 
                            * radius + screenWidth / 2);
                    balls[i].setY(-Math.sin(angle + getAnglDestDotsShift()) 
                            * radius + screenHeight / 2);
                    break;
                }
                //** Curvy circle
                case 5: {
                    angle += .5018;
                    radius2 = (int) (Math.cos(angle) * radiusFloating);
                    radius2 += radius;
                    balls[i].setX(Math.cos(i * step + getAnglDestDotsShift())
                            * radius2 + screenWidth / 2);
                    balls[i].setY(-Math.sin(i * step + getAnglDestDotsShift())
                            * radius2 + screenHeight / 2);
                    break;
                }
                //** Shape like 8, but horizontal
                case 6: {
                    angleStep2 += stepPI;
                    balls[i].setX(Math.cos(angleStep2 + angleStep2 + Math.PI / 2
                            + getAnglDestDotsShift()) * radius + screenWidth / 2);
                    balls[i].setY(Math.sin(angle - angleStep2 + Math.PI / 2
                            + getAnglDestDotsShift()) * radius + screenHeight / 2);
                    break;
                }
                //** Shape like 88
                case 7: {
                    angleStep2 += stepPI * 2;
                    balls[i].setX(Math.cos(angleStep2 + angleStep2 / 2 - angle
                            + getAnglDestDotsShift()) * radius + screenWidth / 2);
                    balls[i].setY(Math.sin(angle / 2 - angleStep2
                            + getAnglDestDotsShift()) * radius + screenHeight / 2);
                    break;
                }
                //** Cross of a balls (4 spikes)
                case 8: {
                    stepPI = Math.PI / 2;
                    balls[i].setX(screenWidth / 2
                            + radius * i * Math.sin(i * stepPI 
                            + getAnglDestDotsShift()) / balls.length);
                    balls[i].setY(screenHeight / 2
                            + radius * i * Math.cos(i * stepPI 
                            + getAnglDestDotsShift()) / balls.length);
                    break;
                }
                //** 8 spikes of a balls
                case 9: {
                    stepPI = Math.PI / 4;
                    balls[i].setX(screenWidth / 2
                            + radius * i
                            * Math.sin(i * stepPI - Math.PI * 3 / 4 
                            + getAnglDestDotsShift()) / balls.length);
                    balls[i].setY(screenHeight / 2
                            + radius * i
                            * Math.cos(i * stepPI - Math.PI * 3 / 4 
                            + getAnglDestDotsShift()) / balls.length);
                    break;
                }
                //** 20 spikes of a balls
                case 10: {
                    stepPI = Math.PI / 20;
                    balls[i].setX(screenWidth / 2
                            + radius * i
                            * Math.sin(i * stepPI - Math.PI * 3 / 4 
                            + getAnglDestDotsShift()) / balls.length);
                    balls[i].setY(screenHeight / 2
                            + radius * i
                            * Math.cos(i * stepPI - Math.PI * 3 / 4 
                            + getAnglDestDotsShift()) / balls.length);
                    break;
                }
                //** Concaved triangle = to destination circle
                case 11: {
                    stepPI = Math.PI * 2 / balls.length;
                    balls[i].setX(screenWidth / 2
                            + radius * 6 * Math.sin(i * stepPI 
                            + getAnglDestDotsShift() * 2) / 9
                            + radius / 3 * Math.cos(i * 2 * stepPI));
                    balls[i].setY(screenHeight / 2
                            + radius * 6 * Math.cos(i * stepPI 
                            + getAnglDestDotsShift() * 2) / 9
                            + radius / 3 * Math.sin(i * 2 * stepPI));
                    break;
                }
                //** Pentagramm = to destination circle
                case 12: {
                    stepPI = Math.PI * 2 / balls.length;
                    balls[i].setX(screenWidth / 2
                            + radius * 4 * Math.sin(i * stepPI 
                            + getAnglDestDotsShift() * 2) / 5
                            + radius / 5 * Math.cos(i * 4 * stepPI));
                    balls[i].setY(screenHeight / 2
                            + radius * 4 * Math.cos(i * stepPI 
                            + getAnglDestDotsShift() * 2) / 5
                            + radius / 5 * Math.sin(i * 4 * stepPI));
                    break;
                }
                //** 10 curved lines = to a destination circle
                case 13: {
                    stepPI = Math.PI * 2 / balls.length;
                    balls[i].setX(screenWidth / 2
                            + radius * 3 * Math.sin(i * stepPI 
                            + getAnglDestDotsShift() * 2) / 3.3
                            + radius / 9 * Math.cos(i * 9 * stepPI));
                    balls[i].setY(screenHeight / 2
                            + radius * 3 * Math.cos(i * stepPI 
                            + getAnglDestDotsShift() * 2) / 3.3
                            + radius / 9 * Math.sin(i * 9 * stepPI));
                    break;
                }
                //** 4 petals inside a destination circle
                case 14: {
                    stepPI = Math.PI * 2 / balls.length;
                    balls[i].setX(screenWidth / 2
                            + radius * Math.sin(i * stepPI - Math.PI / 2
                            + getAnglDestDotsShift() * 2) / 2
                            + radius / 2 * Math.cos(i * 3 * stepPI));
                    balls[i].setY(screenHeight / 2
                            + radius * Math.cos(i * stepPI - Math.PI / 2
                            + getAnglDestDotsShift() * 2) / 2
                            + radius / 2 * Math.sin(i * 3 * stepPI));
                    break;
                }
                //** 8 petals inside a destination circle
                case 15: {
                    stepPI = Math.PI * 2 / balls.length;
                    balls[i].setX(screenWidth / 2
                            + radius * 10 * Math.sin(i * stepPI + Math.PI / 16
                            + getAnglDestDotsShift() * 2) / 20
                            + radius / 2 * Math.cos(i * 7 * stepPI));
                    balls[i].setY(screenHeight / 2
                            + radius * 10 * Math.cos(i * stepPI + Math.PI / 16
                            + getAnglDestDotsShift() * 2) / 20
                            + radius / 2 * Math.sin(i * 7 * stepPI));
                    break;
                }
                case 16: {
                    stepPI = Math.PI * 2 / balls.length;
                    balls[i].setX(screenWidth / 2
                            + radius * Math.sin(i * stepPI
                            + getAnglDestDotsShift() * 2) / 2
                            + radius / 2 * Math.cos(i * 10 * stepPI));
                    balls[i].setY(screenHeight / 2
                            + radius * Math.cos(i * stepPI
                            + getAnglDestDotsShift() * 2) / 2
                            + radius / 2 * Math.sin(i * 10 * stepPI));
                    break;
                }
                //** 20 petals inside a destination circle, with a blank in a center
                case 17: {
                    stepPI = Math.PI * 2 / balls.length;
                    balls[i].setX(screenWidth / 2
                            + radius * 2 * Math.sin(i * stepPI
                            + getAnglDestDotsShift() * 2) / 3
                            + radius / 3 * Math.cos(i * 19 * stepPI));
                    balls[i].setY(screenHeight / 2
                            + radius * 2 * Math.cos(i * stepPI
                            + getAnglDestDotsShift() * 2) / 3
                            + radius / 3 * Math.sin(i * 19 * stepPI));
                    break;
                }
                //** Spiral
                case 18: {
                    stepPI = Math.PI * 2 / balls.length * 8;
                    balls[i].setX(screenWidth / 2
                            + radius * i * Math.sin(i * stepPI - Math.PI * 3 / 4
                            + getAnglDestDotsShift() * 2) / balls.length );
                    balls[i].setY(screenHeight / 2
                            + radius * i * Math.cos(i * stepPI - Math.PI * 3 / 4
                            + getAnglDestDotsShift() * 2) / balls.length );
                    break;
                }
                //** Cobweb
                case 19: {
                    stepPI = Math.PI * 2 / balls.length * 8;
                    balls[i].setX(screenWidth / 2
                            + radius * i * Math.sin(i * stepPI - Math.PI * 3 / 4
                            + getAnglDestDotsShift() * 2) / balls.length
                            + radius / 20 * Math.cos(i * 10 * stepPI));
                    balls[i].setY(screenHeight / 2
                            + radius * i * Math.cos(i * stepPI - Math.PI * 3 / 4
                            + getAnglDestDotsShift() * 2) / balls.length
                            + radius / 20 * Math.sin(i * 10 * stepPI));
                    break;
                }
                //** Balls in the circles
                case 20: {
                    stepPI = Math.PI / 20;
                    balls[i].setX(screenWidth / 2
                            + radius * Math.sin(i * stepPI - Math.PI * 3 / 4
                            + getAnglDestDotsShift() * 2) 
                            + radius / 10 * Math.cos(i * 8 * stepPI) );
                    balls[i].setY(screenHeight / 2
                            + radius * Math.cos(i * stepPI - Math.PI * 3 / 4
                            + getAnglDestDotsShift() * 2)
                            + radius / 10 *  Math.sin(i * 8 * stepPI) );
                    break;
                }
                //** Balls placed in 2 circles
                case 21: {
                    stepPI = Math.PI * 2 / balls.length;
                    if (i % 20 == 0) {
                        radius += rad;
                        rad *= -1;
                    }
                    balls[i].setX(screenWidth / 2
                            + radius * Math.sin(i * stepPI - Math.PI * 3 / 4
                            + getAnglDestDotsShift() * 2));
                    balls[i].setY(screenHeight / 2
                            + radius * Math.cos(i * stepPI - Math.PI * 3 / 4
                            + getAnglDestDotsShift() * 2));
                    break;
                }
                //** Star
                case 22: {
                    stepPI = Math.PI * 2 / balls.length;
                    balls[i].setX(screenWidth / 2
                            + radius * 2 * Math.sin(i * stepPI * 4
                            + getAnglDestDotsShift() * 2) / 3
                            + radius / 3 * Math.cos(i * 6 * stepPI));
                    balls[i].setY(screenHeight / 2
                            + radius * 2 * Math.cos(i * stepPI * 4
                            + getAnglDestDotsShift() * 2) / 3
                            + radius / 3 * Math.sin(i * 6 * stepPI));
                    break;
                }
                //** Amplitude wave
                case 23: {
                    balls[i].setX(incXDelta * Math.sin(
                            stepPI * 1.5
                            + getAnglDestDotsShift() * 2)
                            + xDelta / 2
                            + screenWidth / 2);
                    balls[i].setY(incYDelta - 10);
                    incXDelta += xDelta / 2;
                    stepPI += 0.0093;
                    if (i % rowQuantity == 0) {
                        incXDelta = 0;
                        incYDelta += yDelta / 1.09;
                    }
                    break;
                }
                //** Shape |X| in horizontal placing
                case 24: {
                    angleStep2 += stepPI;
                    balls[i].setX(Math.tan(angleStep2 + angleStep2
                            + getAnglDestDotsShift()) * radius
                            + screenWidth / 2);
                    balls[i].setY(Math.sin(angle - angleStep2
                            + getAnglDestDotsShift()) * radius
                            + screenHeight / 2);
                    if (balls[i].getX() > screenWidth
                            || balls[i].getX() < 0) {
                        balls[i].setX(screenWidth / 2);
                        try {
                            balls[i].setX(balls[i - 1].getX());
                            balls[i].setY(balls[i - 1].getY());
                            break;
                        } catch (Exception e) {
                            balls[i].setX(balls[i + 1].getX());
                            balls[i].setY(balls[i + 1].getY());
                        }
                    }
                    if (balls[i].getY() > screenHeight
                            || balls[i].getY() < 0) {
                        balls[i].setY(screenHeight / 2);
                        try {
                            balls[i].setX(balls[i - 1].getX());
                            balls[i].setY(balls[i - 1].getY());
                            break;
                        } catch (Exception e) {
                            balls[i].setX(balls[i + 1].getX());
                            balls[i].setY(balls[i + 1].getY());
                        }
                    }
                    break;
                }
                // Strangely, this peace of code ceased to work properly.
                //** Circle with X crossed
//                case 25: {
//                    angleStep = Math.PI;
//                    angleStep2 += stepPI;
//                    radius2 = (int) (Math.cos(angle) * radiusFloating);
//                    balls[i].setX(Math.cos(angleStep2 + angleStep / 2 - angle
//                            + getAnglDestDotsShift() * 2)
//                            + getAnglDestDotsShift() * radius
//                            + screenWidth / 2);
//                    balls[i].setY(-Math.sin(angle / 2 - angleStep2
//                            + getAnglDestDotsShift() * 2)
//                            + getAnglDestDotsShift() * radius
//                            + screenHeight / 2);
//                    break;
//                }
            }

        }

        computeMetaData(converge);
    }

    //** Computing distances between 2 balls.
    public double computeDistance(double x1, double y1, double x2, double y2) {
        return (Math.sqrt(
                Math.pow((double) (x1 - x2), (double) 2)
                + Math.pow((double) (y1 - y2), (double) 2)));
    }

    //** Computing data for balls - coordinates deltas, distances among balls
    public void computeMetaData(boolean doComputations) {

        double maxDistance = 0;

        //** Make a computeulations, in case it is allowed )
        if (doComputations) {

            for (int i = 0; i < ballsScattered.length; i++) {

                //** computeulating distance between the dots
                ballsMetaData[i].setDistance(
                        computeDistance(
                        ballsScattered[i].x,
                        ballsScattered[i].y,
                        ballsDestination[i].x,
                        ballsDestination[i].y));
                if (BallsMetaData.convergeAtOnce) {
                    if (maxDistance < ballsMetaData[i].getDistance()) {
                        maxDistance = ballsMetaData[i].getDistance();
                    }
                }
                //** computeulating delta for x
                ballsMetaData[i].setdx(
                        (ballsDestination[i].x
                        - ballsScattered[i].x)
                        / ballsMetaData[i].getDistance());
                //** computeulating delta for y
                ballsMetaData[i].setdy(
                        (ballsDestination[i].y
                        - ballsScattered[i].y)
                        / ballsMetaData[i].getDistance());
            }
            if (BallsMetaData.convergeAtOnce) {
                double distanceRatio = 0;
                int i = 0;
                for (i = 0; i < ballsMetaData.length; i++) {
                    if (ballsMetaData[i].getDistance() > 0) {
                        distanceRatio = maxDistance / ballsMetaData[i].getDistance();
                    }
                    ballsMetaData[i].setdx(ballsMetaData[i].getdx() / distanceRatio);
                    ballsMetaData[i].setdy(ballsMetaData[i].getdy() / distanceRatio);
                }
            }
        }
    }

    //** Making balls scattered on a screen at random
    public BallScattered[] randomizeBalls(BallScattered[] balls,
            int screenWidth, int screenHeight) {
        for (BallScattered ball : balls) {
            ball.x = Math.random() * screenWidth;
            ball.y = Math.random() * screenHeight;
        }
        return balls;
    }

    //** Make some ball selected in an array of a scattered balls
    public void setBallSelected(int x, int y, BallsImages images) {

        double xMin = Integer.MAX_VALUE;
        double yMin = Integer.MAX_VALUE;

        ballSelected.setImage(images.getImgScattered());
        ballSelected = ballDummy;

        for (int i = 0; i < ballsScattered.length; i++) {
            if (x - ballsScattered[i].getX() <= ballsScattered[i].getImage().getWidth() - 2
                    && x - ballsScattered[i].getX() > 1
                    && y - ballsScattered[i].getY() <= ballsScattered[i].getImage().getHeight() - 2
                    && y - ballsScattered[i].getY() > 1) {
                if (xMin > (ballsScattered[i].getX() + (ballsScattered[i].getImage().getWidth() / 2))
                        || yMin > (ballsScattered[i].getY() + (ballsScattered[i].getImage().getHeight() / 2))) {
                    ballSelected = ballsScattered[i];
                }
            }
        }
        ballSelected.setImage(images.getImgSelected());
    }
    
    public void printMetaData() {
        for (BallsMetaData ballsmd : ballsMetaData) {
            System.out.println(ballsmd.getDistance());
            System.out.println(ballsmd.getdx());
            System.out.println(ballsmd.getdy());
        }
    }       

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public int getScatterMode() {
        return scatterMode;
    }

    public void setScatterMode(int scatterMode) {
        this.scatterMode = scatterMode;
    }

    /**
     * @return the ballsMetaData
     */
    public BallsMetaData[] getBallsMetaData() {
        return ballsMetaData;
    }

    /**
     * @param ballsMetaData the ballsMetaData to set
     */
    public void setBallsMetaData(BallsMetaData[] ballsMetaData) {
        this.ballsMetaData = ballsMetaData;
    }

    /**
     * @return the destDotsFigureKind
     */
    public int getDestDotsFigureKind() {
        return destDotsFigureKind;
    }

    /**
     * @param destDotsFigureKind the destDotsFigureKind to set
     */
    public void setDestDotsFigureKind(int destDotsFigureKind) {
        this.destDotsFigureKind = destDotsFigureKind;
    }

    public BallScattered getBallSelected() {
        return ballSelected;
    }

    public void setBallSelected(BallScattered ballSelected) {
        this.ballSelected = ballSelected;
    }

    public BallDestination getBallDummy() {
        return ballDummy;
    }

    public void setBallDummy(BallScattered ballDummy) {
        this.ballDummy = ballDummy;
    }

    public BallDestination[] getBallsDestination() {
        return ballsDestination;
    }

    public void setBallsDestination(BallDestination[] ballsDestination) {
        this.ballsDestination = ballsDestination;
    }

    public BallScattered[] getBallsScattered() {
        return ballsScattered;
    }

    public void setBallsScattered(BallScattered[] ballsScattered) {
        this.ballsScattered = ballsScattered;
    }

    /**
     * @return the angle
     */
    public double getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * @return the angleStep
     */
    public double getAngleStep() {
        return angleStep;
    }

    /**
     * @param angleStep the angleStep to set
     */
    public void setAngleStep(double angleStep) {
        this.angleStep = angleStep;
    }

    /**
     * @return the anglDestDotsShift
     */
    public double getAnglDestDotsShift() {
        return anglDestDotsShift;
    }

    /**
     * @param anglDestDotsShift the anglDestDotsShift to set
     */
    public void setAnglDestDotsShift(double anglDestDotsShift) {
        this.anglDestDotsShift = anglDestDotsShift;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadiusFloating() {
        return radiusFloating;
    }

    public void setRadiusFloating(int radiusFloating) {
        this.radiusFloating = radiusFloating;
    }

    /**
     * @return the offsetX
     */
    public double getOffsetX() {
        return offsetX;
    }

    /**
     * @param offsetX the offsetX to set
     */
    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * @return the offsetY
     */
    public double getOffsetY() {
        return offsetY;
    }

    /**
     * @param offsetY the offsetY to set
     */
    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }
    //</editor-fold>
}
