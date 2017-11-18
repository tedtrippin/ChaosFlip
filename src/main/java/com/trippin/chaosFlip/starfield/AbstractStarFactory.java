package com.trippin.chaosFlip.starfield;

/**
 * Abstract star factory for creating stars between 1-3 in size
 * and a random speed.
 *
 * @author robert.haycock
 *
 */
abstract class AbstractStarFactory implements StarFactory {

    protected int startAngle; // Initial angle of travel
    protected int angleRange; // Direction of travel range
    protected int xRange; // Range of starting X coordinate
    protected int yRange; // Range of starting Y coordinate
    protected int xOffset; // X coordinate offset
    protected int yOffset; // Y coordinate offset

    @Override
    public Star createStar() {

        int direction = (int)(Math.random() * angleRange) + startAngle;
        int x = (int)(Math.random() * xRange) + xOffset;
        int y = (int)(Math.random() * yRange) + yOffset;
        int size = (int)(Math.random() * 40) / 10;
        if (size < 1)
            size = 1;
        else if (size > 3)
            size = 3;
        double speed = Math.random();

        return new Star(direction, speed, size, x, y);
    }
}
