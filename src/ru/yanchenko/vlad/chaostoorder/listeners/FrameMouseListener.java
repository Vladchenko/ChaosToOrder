/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yanchenko.vlad.chaostoorder.listeners;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import ru.yanchenko.vlad.chaostoorder.Logic;
import ru.yanchenko.vlad.chaostoorder.Repository;

/**
 *
 * @author Влад
 */
public class FrameMouseListener implements MouseListener {

    private Repository oRepository = Repository.getInstance();

    @Override
    public void mouseClicked(MouseEvent e) {
//        oRepository.setConverge(true);

    }

    @Override
    public void mousePressed(MouseEvent e) {

        //** When there is no any scattered ball selected
//        if (oRepository.getBalls().getBallSelected().equals(
//                oRepository.getBalls().getBallDummy())) {
        //** When there is no ALT / CTRL / SHIFT keys pressed
        if (!oRepository.isKeyAlt()
                && !oRepository.isKeyCtrl()
                && !oRepository.isKeyShift()) {
            //** Selecting a ball
            oRepository.getBalls().setBallSelected(
                    e.getX(),
                    e.getY(),
                    oRepository.getBallsImages());
        }

        //** When CTRL is pressed
        if (oRepository.isKeyCtrl()) {
        }

        //** When SHIFT is pressed
        if (oRepository.isKeyShift()) {
        }
//        } //** When there is some scattered ball selected    
//        else {
//        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        //<editor-fold defaultstate="collapsed" desc="Part of a code needed for a mouseWheelListener (dragging a ball) ">
        //** Drawing a mouse cursor back again
        oRepository.getoDrawing().setCursor(Cursor.getDefaultCursor());
        //** Returning an offset back again
        oRepository.getBalls().setOffsetX(-1);
        oRepository.getBalls().setOffsetY(-1);
        //</editor-fold>

        // When balls began roaming...
//        if (oRepository.getBalls().getBallSelected().equals(
//                oRepository.getBalls().getBallDummy())) {
//            if (oRepository.isRoam()) {
//                oRepository.setRoam(false);
//                oRepository.setConverge(false);
//                oRepository.getTmrRendering().stop();
//            } else {
//                oRepository.setRoam(true);
//                oRepository.setConverge(true);
//                oRepository.getTmrRendering().start();
//            }
//            // Make them stop and 
//            System.out.println("!");
//        }

        //** Stopping an "after convergence" process, in case it runs
        oRepository.getoLogic().stopAfterConvergenceProcess();

        //<editor-fold defaultstate="collapsed" desc="No ALT / CTRL / SHIFT keys pressed">
        if (!oRepository.isKeyAlt()
                && !oRepository.isKeyCtrl()
                && !oRepository.isKeyShift()) {

        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="When CTRL is pressed">
        if (oRepository.isKeyCtrl()) {
            //** When there is some ball selected
            if (!oRepository.getBalls().getBallSelected().equals(
                    oRepository.getBalls().getBallDummy())) {
                /**
                 * Exchange a selected ball's coordinate with a mouse cursor's
                 * position.
                 */
                oRepository.getBalls().getBallSelected().setX(e.getPoint().x
                        - oRepository.getBallsImages().
                        getImgScattered().getWidth() / 2);
                oRepository.getBalls().getBallSelected().setY(e.getPoint().y
                        - oRepository.getBallsImages().
                        getImgScattered().getHeight() / 2);
            } else {
                //** Place a last scattered ball to a mouse cursor's position
                oRepository.getBalls().getBallsScattered()[oRepository.getBalls().getBallsScattered().length - 1]
                        .setX(e.getPoint().x
                                - oRepository.getBallsImages().
                                getImgScattered().getWidth() / 2);
                oRepository.getBalls().getBallsScattered()[oRepository.getBalls().getBallsScattered().length - 1]
                        .setY(e.getPoint().y
                                - oRepository.getBallsImages().
                                getImgScattered().getHeight() / 2);
            }
//            oRepository.getBalls().computeMetaData(true);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="When SHIFT is pressed">
        if (oRepository.isKeyShift()) {
            for (int i = 0; i < oRepository.getBalls().
                    getBallsScattered().length; i++) {
                oRepository.getBalls().getBallsScattered()[i].setX(
                        e.getPoint().x
                        - oRepository.getBallsImages().
                        getImgScattered().getWidth() / 2);
                oRepository.getBalls().getBallsScattered()[i].setY(
                        e.getPoint().y
                        - oRepository.getBallsImages().
                        getImgScattered().getHeight() / 2);
            }
            //** 
            oRepository.getBalls().computeMetaData(true);
        }
        //</editor-fold>

//        System.out.println(oRepository.isRoam());
        oRepository.getBalls().computeMetaData(true);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
