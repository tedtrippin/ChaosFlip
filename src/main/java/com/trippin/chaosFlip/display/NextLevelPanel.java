package com.trippin.chaosFlip.display;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.trippin.chaosFlip.Exception.CantLoadLevelException;

public class NextLevelPanel
    extends JPanel
    implements ActionListener {

    /*
     * The level just completed.
     */
    private final int lastLevelNumber;

    private final JFrame parent;
    private final JButton nextButton;

    public NextLevelPanel(int lastLevelNumber, JFrame parent) {

        this.lastLevelNumber = lastLevelNumber;
        this.parent = parent;

        JLabel nextLevelLabel = new JLabel("Next level is " + (lastLevelNumber + 1));
        add(nextLevelLabel);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        add(nextButton);
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
