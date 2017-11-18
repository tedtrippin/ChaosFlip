package com.trippin.chaosFlip.starfield;

import java.awt.Component;

/**
 * A star factory where stars start on the left and travel
 * right, with a random Y coordinate.
 *
 * @author robert.haycock
 *
 */
public class RightStarFactory extends AbstractStarFactory {

    @Override
    public void init(Component parent) {
        startAngle = 90;
        angleRange = 0;
        xRange = 0;
        yRange = parent.getHeight() - 4;
        xOffset = 1;
        yOffset = 0;
    }
}
