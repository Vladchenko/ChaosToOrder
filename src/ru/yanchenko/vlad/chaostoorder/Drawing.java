package ru.yanchenko.vlad.chaostoorder;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Date;
import javax.swing.JPanel;

public class Drawing extends JPanel {

    private Repository oRepository = Repository.getInstance();

    public static synchronized Drawing getInstance(Repository Repository) {
        if (Repository.getoDrawing() == null) {
            Repository.setoDrawing(new Drawing());
        }
        return Repository.getoDrawing();
    }

    @Override
    public void paintComponent(Graphics g) {

        //** Erasing a previously drawn bitmap 
        super.paintComponent(g);

        //** Passing through all the balls and draw them
        for (int i = 0; i < oRepository.getDotsQuantity(); i++) {
            g.drawImage(oRepository.getBalls().getBallsDestination()[i].getImage(),
                    (int) oRepository.getBalls().getBallsDestination()[i].getX(),
                    (int) oRepository.getBalls().getBallsDestination()[i].getY(),
                    this);
            g.drawImage(oRepository.getBalls().getBallsScattered()[i].getImage(),
                    (int) oRepository.getBalls().getBallsScattered()[i].getX(),
                    (int) oRepository.getBalls().getBallsScattered()[i].getY(),
                    this);
        }

    }
}
