/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yanchenko.vlad.chaostoorder.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import ru.yanchenko.vlad.chaostoorder.Repository;

/**
 *
 * @author v.yanchenko
 */
public class LabelMouseListener implements MouseListener {

    private Repository oRepository = Repository.getInstance();

    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println("Render label mouse button clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        oRepository.setImgRenderButton(
                oRepository.getoLogic().changeLookRenderButtonPressed(
                oRepository.getImgRenderButton(),
                oRepository.getLblRenderButton(),
                oRepository.getMapStrImages()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        //** When convergence is off, turn it on and vice versa.
        if (!oRepository.isConverge()) {
            oRepository.setConverge(true);
        } else {
            oRepository.setConverge(false);
        }

        //<editor-fold defaultstate="collapsed" desc="If renderButton has an "Init" inscription on it">
        if (oRepository.getImgRenderButton().getDescription()
                == oRepository.getStrImgInitPressed()) {
            
            //** Stopping a convergence
            oRepository.setConverge(false);
            oRepository.setRoam(false);
            oRepository.getTmrAfterConvergence().stop();
            //            oRepository.getTmrRendering().stop();
            oRepository.getoLogic().setScatteredBallsLook(
                    oRepository.getBalls().getBallsScattered(),
                    oRepository.getBallsImages().getImgScattered());
            
            //** Randomizing scattered balls to repeat all over again
            //            oRepository.getBalls().setScatterMode(8);
            oRepository.getBalls().scatterTheBalls(
                    oRepository.getBalls().getBallsScattered(),
                    Math.PI,
                    oRepository.getScreenWidth(),
                    oRepository.getScreenHeight(),
                oRepository.getBallsImages().getImgScattered(), false);
            
            //** Recomputing metadata
            oRepository.getBalls().computeMetaData(
                    !oRepository.getTmrAfterConvergence().isRunning());
            oRepository.getTmrRendering().start();
        }
        //</editor-fold>

        oRepository.setImgRenderButton(
                oRepository.getoLogic().changeLookRenderButtonReleased(
                oRepository.getImgRenderButton(),
                oRepository.getLblRenderButton(),
                oRepository.getMapStrImages()));

        //** When an "after convergence" timer is running
//        if (oRepository.getTmrAfterConvergence().isRunning()) {
//            //** Stopping this timer
//            oRepository.getTmrAfterConvergence().stop();
//            //** Changing an image of a scattered balls to be back, red
//            oRepository.getoLogic().setScatteredBallsLook(
//                    oRepository.getBalls().getBallsScattered(),
//                    oRepository.getBallsImages().getImgScattered());
//            oRepository.getBalls().randomizeBalls(
//                    oRepository.getBalls().getBallsScattered(), 
//                    oRepository.getScreenWidth(),
//                    oRepository.getScreenHeight());
//            //** Recomputeulating metadata
//            oRepository.getBalls().computeMetaData(true);
//            //** Set renderbutton to start
//            oRepository.setImgRenderButton(
//                    new ImageIcon(oRepository.getStrImgStartInitial()));
//            //** Assigning an image to a button
//            oRepository.getLblRenderButton().setIcon(
//                                oRepository.getImgRenderButton());
////            oRepository.setConverge(true);
////            oRepository.setRoam(true);
////            oRepository.getTmrRendering().start();
//        }

//        if (oRepository.getTmrRoaming().isRunning()) {
//            //** Set renderbutton to start
//            oRepository.setImgRenderButton(
//                    new ImageIcon(oRepository.getStrImgPauseInitial()));
//            //** Assigning an image to a button
//            oRepository.getLblRenderButton().setIcon(
//                                oRepository.getImgRenderButton());
//        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        oRepository.setImgRenderButton(
                oRepository.getoLogic().changeLookRenderButtonEntered(
                oRepository.getImgRenderButton(),
                oRepository.getLblRenderButton(),
                oRepository.getMapStrImages()));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        oRepository.setImgRenderButton(
                oRepository.getoLogic().changeLookRenderButtonExited(
                oRepository.getImgRenderButton(),
                oRepository.getLblRenderButton(),
                oRepository.getMapStrImages()));
    }
}
