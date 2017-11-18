package com.trippin.chaosFlip.starfield;

import java.awt.Graphics2D;

public class Star {

    private static final double[] cos = new double[361];
    private static final double[] sin = new double[361];
//    private static Image starImage;

    static {
        for (int i = 0; i <= 360; i++) {
            cos[i] = Math.cos(Math.toRadians(i+180));
            sin[i] = Math.sin(Math.toRadians(i));
        }

//        try {
//            starImage = ImageIO.read(Star.class.getResourceAsStream("/elephant.png"));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }

    private int step;
    private final double speed;
    private final int direction;
    private final int size;
    private final int origX;
    private final int origY;
    private int x;
    private int y;

    public Star(int direction, double speed, int size, int origX, int origY) {
        super();
        this.direction = direction;
        this.speed = speed;
        this.size = size;
        this.origX = x = origX;
        this.origY = y = origY;
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
        x = ((int)(sin[direction] * step * speed)) + origX;
        y = ((int)(cos[direction] * step * speed)) + origY;
    }

    public void draw(Graphics2D g) {
        g.drawRect(x, y, size, size);
//        g.drawImage(starImage, x, y, null);
    }
}
