package com.trippin.chaosFlip.starfield;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

public class StarField implements ActionListener {

    private final static int STAR_FIELD_DELAY_MS = 10;

    private final Star[] stars = new Star[30];
    private int count = 0;
    private Timer starFieldTimer;
    private JComponent container;
    private StarFactory starFactory;
    private int width;
    private int height;

    public StarField(JComponent container, StarFactory starFactory) {
        this.container = container;
        this.starFactory = starFactory;
    }

    public void start() {

        width = container.getWidth();
        height = container.getHeight();

        starFieldTimer = new Timer(STAR_FIELD_DELAY_MS, this);
        starFieldTimer.start();
    }

    public void stop() {
        starFieldTimer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        step();
        container.repaint();
    }

    public void step() {

        for (Star star : stars) {
            if (star == null)
                continue;

            star.step();
        }
    }

    public void draw(Graphics2D g) {

        g.setColor(Color.WHITE);

        // Randomly add a star if we don't have enough
        if (count < stars.length) {
            if (Math.random() > 0.9) {
                stars[count++] = starFactory.createStar();
            }
        }

        for (int x = 0; x < stars.length; x++) {

            Star star = stars[x];
            if (star == null)
                continue;

            if (isOutOfBounds(star))
                stars[x] = starFactory.createStar();
            else
                star.draw(g);
        }
    }

    private boolean isOutOfBounds(Star star) {

        if (star.getX() < 0 || star.getX() >= width)
            return true;

        if (star.getY() < 0 || star.getY() >= height)
            return true;

        return false;
    }

}
