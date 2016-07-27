package ru.yanchenko.vlad.chaostoorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import ru.yanchenko.vlad.chaostoorder.generics.BallDestination;
import ru.yanchenko.vlad.chaostoorder.generics.BallScattered;
import ru.yanchenko.vlad.chaostoorder.generics.Balls;

//** This class is in charge of a logic of a program */
public class Logic {

    private Repository oRepository = Repository.getInstance();
    /**
     * Represents a current ball to be used in a "RunAfterConvergence" timer.
     */
    private static int ballImageCount = 0;
    private static int i = 0;

    /**
     * Timer that is in charge of computations done while balls are converging,
     * i.e. scatterred balls going towards the destination balls.
     *
     * @return
     */
    private Timer tmrRendering() {

        class TimerImpl implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                oRepository.setEndTime((new Date()).getTime());
                //** If balls are converging
                if (oRepository.isConverge()) {
                    /**
                     * Converging a balls (moving scattered balls towards
                     * destination balls)
                     */
                    for (int i = 0; i < oRepository.getBalls().
                            getBallsScattered().length; i++) {
//                        System.out.println(i);
                        //** Adding a deltaX to a scattered ball's x ordinate
                        oRepository.getBalls().
                                getBallsScattered()[i].setX(
                                oRepository.getBalls().
                                getBallsScattered()[i].getX()
                                + oRepository.getBalls().
                                getBallsMetaData()[i].getdx());
                        //** Adding a deltaY to a scattered ball's y ordinate
                        oRepository.getBalls().
                                getBallsScattered()[i].setY(
                                oRepository.getBalls().
                                getBallsScattered()[i].getY()
                                + oRepository.getBalls().
                                getBallsMetaData()[i].getdy());
                        /**
                         * When a ball resides outside of a screen by oX, invert
                         * a dX by multiplying it by -1.
                         */
                            if (oRepository.getBalls().
                                    getBallsScattered()[i].getX() < 0
                                    && oRepository.getBalls().
                                    getBallsMetaData()[i].getdx() < 0
                                    || oRepository.getBalls().
                                    getBallsScattered()[i].getX()
                                    > (oRepository.getScreenWidth()
                                    - oRepository.getBalls().
                                    getBallSelected().getImage().getWidth())
                                    && oRepository.getBalls().
                                    getBallsMetaData()[i].getdx() > 0) {
                                oRepository.getBalls().
                                        getBallsMetaData()[i].setdx(
                                        oRepository.getBalls().
                                        getBallsMetaData()[i].getdx() * -1);
                            }
                        /**
                         * When a ball resides outside of a screen by oY, invert
                         * a dY by multiplying it by -1.
                         */
                        if (oRepository.getBalls().
                                getBallsScattered()[i].getY() < 0
                                && oRepository.getBalls().
                                getBallsMetaData()[i].getdy() < 0
                                || oRepository.getBalls().
                                getBallsScattered()[i].getY()
                                > (oRepository.getScreenHeight()
                                - oRepository.getBalls().
                                getBallSelected().getImage().getHeight())
                                && oRepository.getBalls().
                                getBallsMetaData()[i].getdy() > 0) {
                            oRepository.getBalls().
                                    getBallsMetaData()[i].setdy(
                                    oRepository.getBalls().
                                    getBallsMetaData()[i].getdy() * -1);
                        }
                    }

                    /**
                     * As soon as convergence for all the balls is done at once,
                     * one can check if any of the scattered balls is very close
                     * to (or is placed at) a destination ball. Thus, checking 
                     * if a scattered ball is very close to a destination ball.
                     */
                    if (!oRepository.isRoam()
                            && (Math.abs(oRepository.getBalls().
                            getBallsScattered()[1].getX()
                            - oRepository.getBalls().
                            getBallsDestination()[1].getX())
                            <= oRepository.getBalls().
                            getBallsMetaData()[1].getdx()
                            || Math.abs(oRepository.getBalls().
                            getBallsScattered()[1].getY()
                            - oRepository.getBalls().
                            getBallsDestination()[1].getY())
                            <= Math.abs(oRepository.getBalls().
                            getBallsMetaData()[1].getdy()))) {
                        //** When so, stopping a convergence
                        oRepository.setConverge(false);
                        /**
                         * Placing all the scattered balls to a destination
                         * balls. It's done because some balls coordinates 
                         * slightly do not match the respective destination 
                         * balls.
                         */
                        putScatteredToDestination(
                                oRepository.getBalls().
                                getBallsDestination(),
                                oRepository.getBalls().
                                getBallsScattered());
                        //** Setting a look of a scattered balls to be converged
                        setScatteredBallsLook(oRepository.getBalls().
                                getBallsScattered(),
                                oRepository.getBallsImages().getImgConverged());
                        //** Setting an image for RenderButton to be "Init"
                        oRepository.setImgRenderButton(
                                new ImageIcon(oRepository.getStrImgInitHovered()));
                        oRepository.getLblRenderButton().setIcon(
                                oRepository.getImgRenderButton());
//                        oRepository.getoDrawing().repaint();
//                        try {
//                            oRepository.getTmrAfterConvergence().wait(3000);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                        oRepository.getTmrRendering().stop();
                        oRepository.getTmrAfterConvergence().start();

                    }
                }

                /* 
                 * On some PCs, that presumably slow, next statement fires a 
                 * NullPointerException on oRepository.getoDrawing(). Seems that
                 * Drawing() instance does not get created. Strangely why, it is 
                 * created earlier on, nonconditionless. Nevertheless, creating
                 * it again.
                 */
                try {
                    oRepository.getoDrawing().repaint();
                } catch (NullPointerException ex) {
                    System.out.println(ex.getMessage());
                    if (oRepository.getoDrawing() == null) {
                        oRepository.setoDrawing(new Drawing());
                    } else {
                        System.out.println("But Drawing() is present !");
                    }
                }

                oRepository.setFps(oRepository.getFps() + 1);
                if ((oRepository.getEndTime() - oRepository.getBeginTime())
                        >= oRepository.getFpsUpdateTimeOut()) {
                    oRepository.setBeginTime((new Date()).getTime());
                    //** Such a computeulation is required due to a variative fps measurement time
                    oRepository.getLblFPS().setText("FPS: " + oRepository.getFps()
                            * (1000 / oRepository.getFpsUpdateTimeOut()));
                    oRepository.setFps(0);
                }
            }
        }

        return new Timer(0, new TimerImpl());
    }

    /**
     * Timer that is run right after balls convergence is over. In current
     * implementation, this timer changes a color of a balls, consequently from
     * first to last one.
     *
     * @return
     */
    private Timer tmrRunAfterConvergence() {

        class TimerImpl implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                /**
                 * Passing every scattered ball and change its look
                 */
                switch (ballImageCount) {
                    case (0):
                        oRepository.getBalls().
                                getBallsScattered()[i].setImage(
                                oRepository.getBallsImages().getImgDestination());
                        break;
                    case (1):
                        oRepository.getBalls().
                                getBallsScattered()[i].setImage(
                                oRepository.getBallsImages().getImgScattered());
                        break;
                    case (2):
                        oRepository.getBalls().
                                getBallsScattered()[i].setImage(
                                oRepository.getBallsImages().getImgConverged());
                        break;
                    case (3):
                        oRepository.getBalls().
                                getBallsScattered()[i].setImage(
                                oRepository.getBallsImages().getImgSelected());
                        break;
                }
                i++;
                if (i == oRepository.getBalls().getBallsScattered().length) {
                    i = 0;
                    ballImageCount++;
                }
                /** 
                 * When a counter of images bigger than 3, make it 0. It's done,
                 * because there are only 4 colors for a balls and this action 
                 * makes color go in loop.
                 */
                if (ballImageCount > 3) {
                    ballImageCount = 0;
                }
                oRepository.getoDrawing().repaint();
            }
        }

        return new Timer(50, new TimerImpl());
    }

    /**
     * This timer makes balls roam on a screen.
     */
    private Timer tmrRunRoaming() {

        class TimerImpl implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                oRepository.setEndTime((new Date()).getTime());
                /**
                 * This action makes balls roam around the screen. Deltas are
                 * taken as they were left during a convergence.
                 */
                for (int i = 0; i < oRepository.getBalls().
                        getBallsScattered().length; i++) {
                    /**
                     * When a ball resides outside of a screen by oX, invert a
                     * dX by multiplying it by -1.
                     */
                    if (oRepository.getBalls().
                            getBallsScattered()[i].getX() < 0
                            && oRepository.getBalls().
                            getBallsMetaData()[i].getdx() < 0
                            || oRepository.getBalls().
                            getBallsScattered()[i].getX()
                            > (oRepository.getScreenWidth()
                            - oRepository.getBalls().
                            getBallSelected().getImage().getWidth())
                            && oRepository.getBalls().
                            getBallsMetaData()[i].getdx() > 0) {
                        oRepository.getBalls().
                                getBallsMetaData()[i].setdx(
                                oRepository.getBalls().
                                getBallsMetaData()[i].getdx() * -1);
                    }
                    //** Adding a deltaX to a scattered ball's x ordinate
                    oRepository.getBalls().
                            getBallsScattered()[i].setX(
                            oRepository.getBalls().
                            getBallsScattered()[i].getX()
                            + oRepository.getBalls().
                            getBallsMetaData()[i].getdx());
                    /**
                     * When a ball resides outside of a screen by oY, invert a
                     * dY by multiplying it by -1.
                     */
                    if (oRepository.getBalls().
                            getBallsScattered()[i].getY() < 0
                            && oRepository.getBalls().
                            getBallsMetaData()[i].getdy() < 0
                            || oRepository.getBalls().
                            getBallsScattered()[i].getY()
                            > (oRepository.getScreenHeight()
                            - oRepository.getBalls().
                            getBallSelected().getImage().getHeight())
                            && oRepository.getBalls().
                            getBallsMetaData()[i].getdy() > 0) {
                        oRepository.getBalls().
                                getBallsMetaData()[i].setdy(
                                oRepository.getBalls().
                                getBallsMetaData()[i].getdy() * -1);
                    }
                    //** Adding a deltaY to a scattered ball's y ordinate
                    oRepository.getBalls().
                            getBallsScattered()[i].setY(
                            oRepository.getBalls().
                            getBallsScattered()[i].getY()
                            + oRepository.getBalls().
                            getBallsMetaData()[i].getdy());
                }

                oRepository.getoDrawing().repaint();

                oRepository.setFps(oRepository.getFps() + 1);
                if ((oRepository.getEndTime() - oRepository.getBeginTime())
                        >= oRepository.getFpsUpdateTimeOut()) {
                    oRepository.setBeginTime((new Date()).getTime());
                    //** Such a computeulation is required due to a variative fps measurement time
                    oRepository.getLblFPS().setText("FPS: " + oRepository.getFps()
                            * (1000 / oRepository.getFpsUpdateTimeOut()));
                    oRepository.setFps(0);
                }
            }
        }

        return new Timer(0, new TimerImpl());
    }

    //** Placing a coordinates of a scattered balls to a destination balls.
    private void putScatteredToDestination(
            BallDestination[] ballsDest,
            BallScattered[] ballsScat) {
        for (int i = 0; i < ballsDest.length; i++) {
            ballsScat[i].setX(ballsDest[i].getX());
            ballsScat[i].setY(ballsDest[i].getY());
        }

    }

    //** Setting images for a balls
    public void setScatteredBallsLook(BallScattered[] balls,
            BufferedImage image) {
        for (BallScattered ball : balls) {
            ball.setImage(image);
        }
    }

    /**
     * Computing a polar coordinates of balls scatterred at random, relatively
     * to a selected one.
     */
    public void computePolarCoors(Balls balls) {

        double deltaX;
        double deltaY;

        for (int i = 0; i < balls.getBallsScattered().length; i++) {
            //** When the dot is not selected, perform a computeulation,
            //** because there is no sense in doing this with the same dot
//            if (!balls.getBallSelected().equals(balls.getBallDummy())) {
            deltaY = balls.getBallsScattered()[i].getY()
                    - balls.getBallSelected().getY();
            deltaX = balls.getBallsScattered()[i].getX()
                    - balls.getBallSelected().getX();
            //** computeulating the angles between selected 
            //** dot and a dots scattered at random
            if (deltaX != 0) {
                balls.getBallsScattered()[i].setAngle(Math.atan(Math.abs(deltaY)
                        / Math.abs(deltaX)));
            }
            //** Adjusting the angle
            if (deltaX < 0 && deltaY < 0) {
                balls.getBallsScattered()[i].
                        setAngle(-balls.getBallsScattered()[i].getAngle()
                        + Math.PI);
            }
            if (deltaX < 0 && deltaY > 0) {
                balls.getBallsScattered()[i].
                        setAngle(balls.getBallsScattered()[i].getAngle()
                        + Math.PI);
            }
            if (deltaX > 0 && deltaY > 0) {
                balls.getBallsScattered()[i].
                        setAngle(-balls.getBallsScattered()[i].getAngle()
                        + Math.PI * 2);
            }

            if (deltaX == 0 && deltaY > 0) {
                balls.getBallsScattered()[i].
                        setAngle(2 * Math.PI - Math.PI / 2);
            }
            if (deltaX == 0 && deltaY < 0) {
                balls.getBallsScattered()[i].setAngle(Math.PI / 2);
            }
            if (deltaX > 0 && deltaY == 0) {
                balls.getBallsScattered()[i].setAngle(0);
            }
            if (deltaX < 0 && deltaY == 0) {
                balls.getBallsScattered()[i].setAngle(Math.PI);
            }

            if (deltaX == 0 && deltaY == 0) {
                balls.getBallsScattered()[i].setAngle(0);
            }
            //** computeulating the radiuses between selected 
            //** dot and a dots scattered at random
            balls.getBallsMetaData()[i].setRadius(
                    Math.sqrt(
                    Math.pow(balls.getBallSelected().getY()
                    - balls.getBallsScattered()[i].getY(), 2)
                    + Math.pow(balls.getBallSelected().getX()
                    - balls.getBallsScattered()[i].getX(), 2)));
        }
    }

    /**
     * Computing a dekart coordinates of a balls scatterred at
     * random, relatively to a selected one.
     */
    public void computeDekartCoors(Balls balls) {
        for (int i = 0; i < balls.getBallsDestination().length; i++) {
//            if (!balls.getBallsScattered()[i].isSelected()) {
            balls.getBallsScattered()[i].setX(
                    balls.getBallSelected().getX()
                    + Math.cos(balls.getBallsScattered()[i].getAngle()
                    + balls.getAngle())
                    * balls.getBallsMetaData()[i].getRadius());
            balls.getBallsScattered()[i].setY(
                    balls.getBallSelected().getY()
                    - Math.sin(balls.getBallsScattered()[i].getAngle()
                    + balls.getAngle())
                    * balls.getBallsMetaData()[i].getRadius());
//            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Changing a look of a renderButton">
    public ImageIcon changeLookRenderButtonEntered(ImageIcon image, JLabel label, Map map) {

        if (image.getDescription().equals(map.get("startInitial"))) {
            image = new ImageIcon((String) map.get("startHovered"));
            label.setIcon(image);
        }

        if (image.getDescription().equals(map.get("pauseInitial"))) {
            image = new ImageIcon((String) map.get("pauseHovered"));
            label.setIcon(image);
        }

        if (image.getDescription().equals(map.get("continueInitial"))) {
            image = new ImageIcon((String) map.get("continueHovered"));
            label.setIcon(image);
        }

        if (image.getDescription().equals(map.get("initInitial"))) {
            image = new ImageIcon((String) map.get("initHovered"));
            label.setIcon(image);
        }

        return image;

    }

    public ImageIcon changeLookRenderButtonExited(ImageIcon image, JLabel label, Map map) {

        //        System.out.println(image.getDescription());
        //        System.out.println(map.get("startHovered"));
        if (image.getDescription().equals(map.get("startHovered"))) {
            image = new ImageIcon((String) map.get("startInitial"));
            label.setIcon(image);
        }

        if (image.getDescription().equals(map.get("pauseHovered"))) {
            image = new ImageIcon((String) map.get("pauseInitial"));
            label.setIcon(image);
        }

        if (image.getDescription().equals(map.get("continueHovered"))) {
            image = new ImageIcon((String) map.get("continueInitial"));
            label.setIcon(image);
        }

        if (image.getDescription().equals(map.get("initHovered"))) {
            image = new ImageIcon((String) map.get("initInitial"));
            label.setIcon(image);
        }

        return image;

    }

    public ImageIcon changeLookRenderButtonPressed(ImageIcon image, JLabel label, Map map) {

        if (image.getDescription().equals(map.get("startHovered"))) {
            image = new ImageIcon((String) map.get("startPressed"));
            label.setIcon(image);
        }

        if (image.getDescription().equals(map.get("pauseHovered"))) {
            image = new ImageIcon((String) map.get("pausePressed"));
            label.setIcon(image);
        }

        if (image.getDescription().equals(map.get("continueHovered"))) {
            image = new ImageIcon((String) map.get("continuePressed"));
            label.setIcon(image);
        }

        if (image.getDescription().equals(map.get("initHovered"))) {
            image = new ImageIcon((String) map.get("initPressed"));
            label.setIcon(image);
        }

        return image;

    }

    public ImageIcon changeLookRenderButtonReleased(ImageIcon image, JLabel label, Map map) {

        if (image.getDescription().equals(map.get("startPressed"))) {
            image = new ImageIcon((String) map.get("pauseHovered"));
            label.setIcon(image);
        }

        if (image.getDescription().equals(map.get("pausePressed"))) {
            image = new ImageIcon((String) map.get("continueHovered"));
            label.setIcon(image);
        }

        if (image.getDescription().equals(map.get("continuePressed"))) {
            image = new ImageIcon((String) map.get("pauseHovered"));
            label.setIcon(image);
        }

        if (image.getDescription().equals(map.get("initPressed"))) {
            image = new ImageIcon((String) map.get("startInitial"));
            label.setIcon(image);
        }

        return image;

    }
    //</editor-fold>

    //** What's done after convergence
    public void stopAfterConvergenceProcess() {
        //** If an "after convergence" timer is running
        if (oRepository.getTmrAfterConvergence().isRunning()) {
            //** Stopping this timer
            oRepository.getTmrAfterConvergence().stop();
            //** Changing an image of a scattered balls to be back, red
            oRepository.getoLogic().setScatteredBallsLook(
                    oRepository.getBalls().getBallsScattered(),
                    oRepository.getBallsImages().getImgScattered());
            //** Setting an image of a selected ball to have a selected image
            if (!oRepository.getBalls().getBallSelected().equals(
                    oRepository.getBalls().getBallDummy())) {
                oRepository.getBalls().getBallSelected().setImage(
                        oRepository.getBallsImages().getImgSelected());
            }
            
            //** Set renderButton to start
//            oRepository.setImgRenderButton(
//                    new ImageIcon(oRepository.getStrImgPauseInitial()));
            //** Assigning an image to a button
//            oRepository.getLblRenderButton().setIcon(
//                    oRepository.getImgRenderButton());
            
            /**
             * Set balls roaming true. That means balls can roam, if mouse is
             * further clicked.
             */
            oRepository.setRoam(true);
            oRepository.setConverge(true);
            oRepository.getTmrRendering().start();
        }
    }

    public Logic() {
        oRepository.setTmrRendering(tmrRendering());
        oRepository.setTmrAfterConvergence(tmrRunAfterConvergence());
        oRepository.getTmrRendering().start();
    }
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    //</editor-fold>
}
