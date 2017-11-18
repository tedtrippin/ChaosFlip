package com.trippin.chaosFlip.display;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.trippin.chaosFlip.starfield.StarFactory;
import com.trippin.chaosFlip.starfield.StarField;

public abstract class ChaosPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final StarFactory starFactory;
    private StarField starField;
    private boolean initialised;

    ChaosPanel(StarFactory starFactory) {
        this.starFactory = starFactory;
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        if (!initialised) {
            initialised = true;
            starField = new StarField(this, starFactory);
            starField.start();
            init();
        }

        starField.draw((Graphics2D) g);

        paintComponents(g);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        starField.stop();
    }

    /**
     * Called first time we paint, so we have the width.
     * Child components should have their location set here.
     */
    abstract protected void init();

}
