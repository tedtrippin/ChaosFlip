package com.trippin.chaosFlip.display;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.trippin.chaosFlip.Exception.CantLoadLevelException;
import com.trippin.chaosFlip.starfield.CenterStarFactory;
import com.trippin.chaosFlip.starfield.StarField;

public class NextLevelPanel
    extends JPanel
    implements ActionListener {

    private static final long serialVersionUID = 1L;

    /*
     * The level just completed.
     */
    private final int lastLevelNumber;

    private final JFrame parent;
    private final JButton nextButton;
    private StarField starField;

    public NextLevelPanel(int lastLevelNumber, JFrame parent) {

        this.lastLevelNumber = lastLevelNumber;
        this.parent = parent;
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);

        JLabel nextLevelLabel = new JLabel("Next level is " + (lastLevelNumber + 1));
        add(nextLevelLabel);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        add(nextButton);
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        if (starField == null) {
            starField = new StarField(this, new CenterStarFactory(this));
            starField.start();
        }

        starField.draw((Graphics2D) g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == nextButton)
            nextLevel();
    }

    private void nextLevel() {

        // Create the game panel
        GamePanel gamePanel;
        try {
            gamePanel = new GamePanel(parent, lastLevelNumber + 1);
            Container parentContainer = parent.getContentPane();
            parentContainer.removeAll();
            parentContainer.add(gamePanel);
            parentContainer.validate();
        } catch (CantLoadLevelException ex) {
            ex.printStackTrace();
        }
    }
}
