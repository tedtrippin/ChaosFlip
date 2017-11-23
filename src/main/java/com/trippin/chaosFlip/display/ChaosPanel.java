package com.trippin.chaosFlip.display;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.trippin.chaosFlip.Exception.CantLoadLevelException;
import com.trippin.chaosFlip.starfield.StarFactory;
import com.trippin.chaosFlip.starfield.StarField;

public abstract class ChaosPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final StarFactory starFactory;
    private StarField starField;
    private boolean initialised;
    protected final JFrame parent;

    ChaosPanel(JFrame parent, StarFactory starFactory) {
        this.parent = parent;
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

    protected void goToMenu() {
        changeScreen(new MainMenu(parent));
    }

    protected void goToLevel(int level) {

        try {
            changeScreen(new ArenaPanel(parent, level));
        } catch (CantLoadLevelException ex) {
            ex.printStackTrace();
        }
    }

    private void changeScreen(Component c) {

        Container parentContainer = parent.getContentPane();
        parentContainer.removeAll();
        parentContainer.add(c);
        parentContainer.validate();
    }

    /**
     * Called first time we paint, so we have the width.
     * Child components should have their location set here.
     */
    abstract protected void init();

}
