package com.trippin.chaosFlip.starfield;

import java.awt.Component;

/**
 * Creates a star factory where stars start at the middle
 * of the parent and can travel in any direction.
 *
 * @author robert.haycock
 *
 */
public class CenterStarFactory extends AbstractStarFactory {

    @Override
    public void init(Component parent) {
        startAngle = 0;
        angleRange = 360;
        xRange = 1;
        yRange = 1;
        xOffset = parent.getWidth() / 2;
        yOffset = parent.getHeight() / 2;
    }
}
