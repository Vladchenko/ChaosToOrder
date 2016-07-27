/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yanchenko.vlad.chaostoorder.listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import ru.yanchenko.vlad.chaostoorder.Repository;
import ru.yanchenko.vlad.chaostoorder.generics.BallsMetaData;

/**
 *
 * @author Влад
 */
public class FrameMouseMotionListener implements MouseMotionListener {

    private Repository oRepository = Repository.getInstance();

    //** When mouse cursor dragged
    @Override
    public void mouseDragged(MouseEvent e) {

        //** When some ball selected
        if (!oRepository.getBalls().getBallSelected().equals(
                oRepository.getBalls().getBallDummy())) {
            /**
             * Hiding a mouse cursor, for it could not cover(overshadow) a
             * dragged ball.
             */
            oRepository.getoDrawing().setCursor(
                    oRepository.getoDrawing().getToolkit().createCustomCursor(
                            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
                            new Point(0, 0),
                            "null"));

            if (oRepository.getBalls().getOffsetX() == -1) {
                oRepository.getBalls().setOffsetX(
                        Math.abs(e.getPoint().x
                                - oRepository.getBalls().getBallSelected().getX()));
                oRepository.getBalls().setOffsetY(
                        Math.abs(e.getPoint().y
                                - oRepository.getBalls().getBallSelected().getY()));
                if (oRepository.isKeyCtrl()) {
                    BallsMetaData.setMousePos(e.getPoint());
                }
            }

            oRepository.getBalls().getBallSelected().setX(
                    e.getPoint().x - oRepository.getBalls().getOffsetX());
            oRepository.getBalls().getBallSelected().setY(
                    e.getPoint().y - oRepository.getBalls().getOffsetY());

            //** If CTRL is pushed, then moving all the balls
            if (oRepository.isKeyCtrl()) {
                BallsMetaData.getMousePosDifference().x = BallsMetaData.getMousePos().x - e.getPoint().x;
                BallsMetaData.getMousePosDifference().y = BallsMetaData.getMousePos().y - e.getPoint().y;
                BallsMetaData.setMousePos(e.getPoint());
                for (int i = 0; i < oRepository.getBalls().
                        getBallsScattered().length; i++) {

                    if (!oRepository.getBalls().getBallSelected().equals(
                            oRepository.getBalls().getBallsScattered()[i])) {

                        //** Shifting each ball relatively selected ball
                        oRepository.getBalls().getBallsScattered()[i].setX(
                                oRepository.getBalls().getBallsScattered()[i].getX()
                                - BallsMetaData.getMousePosDifference().x);
                        oRepository.getBalls().getBallsScattered()[i].setY(
                                oRepository.getBalls().getBallsScattered()[i].getY()
                                - BallsMetaData.getMousePosDifference().y);

                    }

                }

            }

            //** Stopping an after convergence process, in case it runs
            oRepository.getoLogic().stopAfterConvergenceProcess();
            
            //** When balls are not roaming, but converging, do this
            oRepository.getBalls().computeMetaData(!oRepository.isRoam());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
