package com.trippin.chaosFlip.starfield;

abstract class AbstractStarFactory implements StarFactory {

    private final int startAngle;
    private final int angleRange;
    private final int xRange;
    private final int xOffset;
    private final int yRange;
    private final int yOffset;

    AbstractStarFactory (int startAngle, int angleRange, int xRange, int xOffset, int yRange, int yOffset) {
        this.startAngle = startAngle;
        this.angleRange = angleRange;
        this.xRange = xRange;
        this.xOffset = xOffset;
        this.yRange = yRange;
        this.yOffset = yOffset;
    }

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
