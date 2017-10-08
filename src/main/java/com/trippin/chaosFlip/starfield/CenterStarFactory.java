package com.trippin.chaosFlip.starfield;

import javax.swing.JComponent;

public class CenterStarFactory extends AbstractStarFactory {

    public CenterStarFactory(JComponent parent) {
        super(0, 360, 1, parent.getWidth()/2, 1, parent.getHeight()/2);
    }
}
