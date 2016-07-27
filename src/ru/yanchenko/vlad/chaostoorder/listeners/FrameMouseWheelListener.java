/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yanchenko.vlad.chaostoorder.listeners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import ru.yanchenko.vlad.chaostoorder.Repository;

/**
 *
 * @author Влад
 */
public class FrameMouseWheelListener implements MouseWheelListener {

    private Repository oRepository = Repository.getInstance();

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        //<editor-fold defaultstate="collapsed" desc="When no ALT / CTRL / SHIFT keys pressed">
        if (!oRepository.isKeyAlt()
                && !oRepository.isKeyCtrl()
                && !oRepository.isKeyShift()) {

            /**
             * This operation rotates a scattered balls around a selected one.
             */
            //** When there is no any scattered ball selected
            if (oRepository.getBalls().getBallSelected().equals(
                    oRepository.getBalls().getBallDummy())) {

                if (e.getWheelRotation() < 0) {

                    //** Moving a destination balls in a current figure
                    oRepository.getBalls().setAnglDestDotsShift(
                            oRepository.getBalls().getAnglDestDotsShift()
                            - 0.04);
                    oRepository.getBalls().scatterTheBalls(
                            oRepository.getBalls().getBallsDestination(),
                            0,
                            oRepository.getScreenWidth(), 
                            oRepository.getScreenHeight(), 
                            oRepository.getBallsImages().getImgScattered(),
                            !oRepository.isRoam());
                }

                if (e.getWheelRotation() > 0) {

                    //** Moving a destination balls in a current figure
                    oRepository.getBalls().setAnglDestDotsShift(
                            oRepository.getBalls().getAnglDestDotsShift()
                            + 0.04);
                    oRepository.getBalls().scatterTheBalls(
                            oRepository.getBalls().getBallsDestination(),
                            0,
                            oRepository.getScreenWidth(), 
                            oRepository.getScreenHeight(), 
                            oRepository.getBallsImages().getImgScattered(),
                            !oRepository.isRoam());
                }
                //** When there is some scattered ball selected
            } else {
                if (e.getWheelRotation() < 0) {
                    oRepository.getoLogic().computePolarCoors(
                            oRepository.getBalls());
                    //** Increasing an angle
                    oRepository.getBalls().setAngle(
                            +oRepository.getBalls().getAngleStep());
                    oRepository.getoLogic().computeDekartCoors(
                            oRepository.getBalls());
                }
                if (e.getWheelRotation() > 0) {
                    oRepository.getoLogic().computePolarCoors(
                            oRepository.getBalls());
                    //** Decreasing an angle
                    oRepository.getBalls().setAngle(
                            -oRepository.getBalls().getAngleStep());
                    oRepository.getoLogic().computeDekartCoors(
                            oRepository.getBalls());
                }
            }
        }
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="When CTRL is pressed">
        if (oRepository.isKeyCtrl()) {

            //** When there is no any scattered ball selected
            if (oRepository.getBalls().getBallSelected().equals(
                    oRepository.getBalls().getBallDummy())) {

                if (e.getWheelRotation() < 0) {
                    
                    //** Increasing a size of a destination balls figure
                    oRepository.getBalls().setRadius(
                            oRepository.getBalls().getRadius() + 15);
                    oRepository.getBalls().setRadiusFloating(
                            oRepository.getBalls().getRadiusFloating() + 3);
                    oRepository.getBalls().scatterTheBalls(
                            oRepository.getBalls().getBallsDestination(),
                            0,
                            oRepository.getScreenWidth(), 
                            oRepository.getScreenHeight(), 
                            oRepository.getBallsImages().getImgScattered(),
                            !oRepository.isRoam());
                }

                if (e.getWheelRotation() > 0) {
                    //** Decreasing a size of a destination balls figure
                    oRepository.getBalls().setRadius(
                            oRepository.getBalls().getRadius() - 15);
                    oRepository.getBalls().setRadiusFloating(
                            oRepository.getBalls().getRadiusFloating() - 3);
                    oRepository.getBalls().scatterTheBalls(
                            oRepository.getBalls().getBallsDestination(),
                            0,
                            oRepository.getScreenWidth(), 
                            oRepository.getScreenHeight(), 
                            oRepository.getBallsImages().getImgScattered(),
                            !oRepository.isRoam());
                }
                //** When there is some ball selected
            } else {
                if (e.getWheelRotation() < 0) {
                    oRepository.getoLogic().computePolarCoors(
                            oRepository.getBalls());
                    oRepository.getBalls().setAngle(0);
                    for (int i = 0; i < oRepository.getBalls().
                            getBallsMetaData().length; i++) {
                        oRepository.getBalls().getBallsMetaData()[i].setRadius(
                                oRepository.getBalls().
                                getBallsMetaData()[i].getRadius()
                                + oRepository.getBalls().
                                getBallsMetaData()[i].getRadius() / 20);

                    }
                    oRepository.getoLogic().computeDekartCoors(
                            oRepository.getBalls());
                }
                if (e.getWheelRotation() > 0) {
                    oRepository.getoLogic().computePolarCoors(
                            oRepository.getBalls());
                    oRepository.getBalls().setAngle(0);
                    for (int i = 0; i < oRepository.getBalls().
                            getBallsMetaData().length; i++) {
                        oRepository.getBalls().getBallsMetaData()[i].setRadius(
                                oRepository.getBalls().
                                getBallsMetaData()[i].getRadius()
                                - oRepository.getBalls().
                                getBallsMetaData()[i].getRadius() / 20);

                    }
                    oRepository.getoLogic().computeDekartCoors(
                            oRepository.getBalls());
                }
            }
        }
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="When SHIFT is pressed">
        if (oRepository.isKeyShift()) {

            //** When there is no any scattered ball selected
            if (oRepository.getBalls().getBallSelected().equals(
                    oRepository.getBalls().getBallDummy())) {

                if (e.getWheelRotation() < 0) {
                    //** Changing a figure of a destination balls
                    oRepository.getBalls().setScatterMode(
                            oRepository.getBalls().getScatterMode() + 1
                    );
                    if (oRepository.getBalls().getScatterMode() > 24) {
                        oRepository.getBalls().setScatterMode(0);
                    }
                    oRepository.getBalls().scatterTheBalls(
                            oRepository.getBalls().getBallsDestination(),
                            0,
                            oRepository.getScreenWidth(), 
                            oRepository.getScreenHeight(), 
                            oRepository.getBallsImages().getImgScattered(),
                            !oRepository.isRoam());
                }
                if (e.getWheelRotation() > 0) {
                    //** Changing a figure of a destination balls
                    oRepository.getBalls().setScatterMode(
                            oRepository.getBalls().getScatterMode() - 1
                    );
                    if (oRepository.getBalls().getScatterMode() < 0) {
                        oRepository.getBalls().setScatterMode(24);
                    }
                    oRepository.getBalls().scatterTheBalls(
                            oRepository.getBalls().getBallsDestination(),
                            0,
                            oRepository.getScreenWidth(), 
                            oRepository.getScreenHeight(), 
                            oRepository.getBallsImages().getImgScattered(),
                            !oRepository.isRoam());
                }
            } //** When there is some scattered ball selected
            else {
                //** Changing a pattern of a scattered balls spreading
                if (e.getWheelRotation() < 0) {
                    oRepository.getBalls().setScatterMode(
                            oRepository.getBalls().getScatterMode() + 1
                    );
                    if (oRepository.getBalls().getScatterMode() > 25) {
                        oRepository.getBalls().setScatterMode(0);
                    }
                }
                if (e.getWheelRotation() > 0) {
                    oRepository.getBalls().setScatterMode(
                            oRepository.getBalls().getScatterMode() - 1
                    );
                    if (oRepository.getBalls().getScatterMode() < 0) {
                        oRepository.getBalls().setScatterMode(25);
                    }
                }
                oRepository.getBalls().scatterTheBalls(
                            oRepository.getBalls().getBallsScattered(),
                            0,
                            oRepository.getScreenWidth(), 
                            oRepository.getScreenHeight(), 
                            oRepository.getBallsImages().getImgScattered(),
                            !oRepository.isRoam());
            }
            oRepository.getBalls().computeMetaData(true);
        }
//</editor-fold>
        
        /**
         * If a scattered balls are converged (that could be defined by 
         * checking at least one ball's image), make them to have scattered 
         * images (look) back again.
         */
        if (oRepository.getTmrAfterConvergence().isRunning()) {
            //** Stopping this timer
            oRepository.getTmrAfterConvergence().stop();
            oRepository.getTmrRendering().start();
            oRepository.getoLogic().setScatteredBallsLook(
                    oRepository.getBalls().getBallsScattered(),
                    oRepository.getBallsImages().getImgScattered());
            //** Changing an image of a selected ball to be back, yellow
            oRepository.getBalls().getBallSelected().setImage(
                oRepository.getBallsImages().getImgSelected());
            /**
             * Setting an image for a RenderButton to be Start, for a 
             * convergence could run consequently.
             */
            oRepository.setImgRenderButton(
                    new ImageIcon(oRepository.getStrImgStartInitial()));
            //** Assigning an image to a renderButton 
            oRepository.getLblRenderButton().setIcon(
                                oRepository.getImgRenderButton());
        }
        
//        System.out.println(oRepository.getTmrRendering().isRunning());

        //** Computing metadata, only in case a "convergence" process is running
        oRepository.getBalls().computeMetaData(
                !oRepository.isRoam());
//        oRepository.getBalls().printMetaData();
    }
}
