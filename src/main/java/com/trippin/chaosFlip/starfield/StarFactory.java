package com.trippin.chaosFlip.starfield;

import java.awt.Component;

public interface StarFactory {

    /**
     * Should be called once the parent has been laid out so
     * its dimensions are available.
     *
     * @param parent
     */
    void init(Component parent);

    /**
     * Creates a star with a location/speed/direction.
     *
     * @param if true, the star starts at the factories origin
     *      else starts somewhere random.
     * @return
     */
    Star createStar(boolean origin);
}
