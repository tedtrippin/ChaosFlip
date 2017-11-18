package com.trippin.chaosFlip.starfield;

import java.awt.Component;

/**
 * A star factory where stars start in the top right corner.
 *
 * @author robert.haycock
 *
 */
public class TopRightCornerStarFactory extends AbstractStarFactory {

    @Override
    public void init(Component parent) {
        startAngle = 180;
        angleRange = 90;
        xRange = 0;
        yRange = 0;
        xOffset = parent.getWidth() - 1;;
        yOffset = 1;
    }
}
