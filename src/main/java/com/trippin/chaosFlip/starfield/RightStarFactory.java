package com.trippin.chaosFlip.starfield;

import javax.swing.JComponent;

public class RightStarFactory extends AbstractStarFactory {

    public RightStarFactory(JComponent parent) {
        super(90, 0, 0, 1, parent.getHeight() - 4, 0);
    }
}
