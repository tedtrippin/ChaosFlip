package com.trippin.chaosFlip.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

public class StarField implements ActionListener {

    private static final double[] cos = new double[361];
    private static final double[] sin = new double[361];
    private static final int STAR_FIELD_DELAY_MS = 10;

    static {
        for (int i = 0; i <= 360; i++) {
            cos[i] = Math.cos(Math.toRadians(i));
            sin[i] = Math.sin(Math.toRadians(i));
        }
    }

    private final Star[] stars = new Star[30];
    private int count = 0;
    private Timer starFieldTimer;
    private JComponent container;

    public StarField(JComponent container) {
        this.container = container;
        starFieldTimer = new Timer(STAR_FIELD_DELAY_MS, this);
    }

    public void start() {
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

    public void draw(Graphics2D g, int width, int height) {

        g.setColor(Color.WHITE);

        // Randomly add a star if we don't have enough
        if (count < stars.length) {
            if (Math.random() > 0.9) {
                stars[count++] = new Star();
            }
        }

        int centreX = width/2;
        int centreY = height/2;

        for (int x = 0; x < stars.length; x++) {

            Star star = stars[x];
            if (star == null)
                continue;

            if (outOfBounds(star, centreX, centreY))
                stars[x] = new Star();
            else
                star.draw(g, centreX, centreY);
        }
    }

    private boolean outOfBounds(Star star, int centreX, int centreY) {

        if (Math.abs(star.getX()) > centreX)
            return true;

        if (Math.abs(star.getY()) > centreY)
            return true;

        return false;
    }

    static class Star {

        /**
         * 0-360 degrees.
         */
        private int direction;

        private double step;
        private int size;
        private int x;
        private int y;

        public Star() {
            direction = (int)(Math.random() * 360.0);
            size = (int)(Math.random() * 40) / 10;
            if (size < 1)
                size = 1;
            else if (size > 3)
                size = 3;
        }

        public int getSize() {
            return size;
        }

        public int getDirection() {
            return direction;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void step() {

            step++;
            x = (int)(sin[direction] * step);
            y = (int)(cos[direction] * step);
        }

        public void draw(Graphics2D g, int width, int height) {

            g.drawRect(x  + width, y + height, size, size);
        }
    }
}
