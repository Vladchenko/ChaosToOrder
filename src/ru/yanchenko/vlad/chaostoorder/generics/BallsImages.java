/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yanchenko.vlad.chaostoorder.generics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author v.yanchenko
 */
public class BallsImages {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    //** Images for a different kind of balls
    private BufferedImage imgSelected;
    private BufferedImage imgScattered;
    private BufferedImage imgConverged;
    private BufferedImage imgDestination;
    //** Strings that stand for a paths where an images of a balls reside
    private String strImgCircluar = "pics/Circular.png";
    private String strImgRandom = "pics/Random.png";
    private String strImgSelected = "pics/Selected.png";
    private String strImgConverged = "pics/Converged.png";
    //** In charge of an actual file that is to hold an image of a ball.
    private File fileImg;
    //</editor-fold>

    public BallsImages() {

        //<editor-fold defaultstate="collapsed" desc="Images instantiating">
        try {
            fileImg = new File(strImgCircluar);
            imgDestination = ImageIO.read(fileImg);
        } catch (Exception ie) {
            System.out.println("Error: " + ie.getMessage());
        }

        try {
            fileImg = new File(strImgRandom);
            imgScattered = ImageIO.read(fileImg);
        } catch (Exception ie) {
            System.out.println("Error: " + ie.getMessage());
        }

        try {
            fileImg = new File(strImgConverged);
            imgConverged = ImageIO.read(fileImg);
        } catch (Exception ie) {
            System.out.println("Error: " + ie.getMessage());
        }

        try {
            fileImg = new File(strImgSelected);
            imgSelected = ImageIO.read(fileImg);
        } catch (Exception ie) {
            System.out.println("Error: " + ie.getMessage());
        }
        //</editor-fold>

    }

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public BufferedImage getImgSelected() {
        return imgSelected;
    }

    public BufferedImage getImgScattered() {
        return imgScattered;
    }

    public BufferedImage getImgConverged() {
        return imgConverged;
    }

    public BufferedImage getImgDestination() {
        return imgDestination;
    }
    //</editor-fold>
}
