package com.trippin.chaosFlip.display;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.trippin.chaosFlip.Exception.CantLoadLevelException;
import com.trippin.chaosFlip.starfield.TopRightCornerStarFactory;

public class NextLevelPanel
    extends ChaosPanel
    implements ActionListener {

    private static final long serialVersionUID = 1L;

    /*
     * The level just completed.
     */
    private final int lastLevelNumber;

    private final JFrame parent;

    private final JButton nextButton;
    private final JLabel nextLevelLabel;

    public NextLevelPanel(int lastLevelNumber, JFrame parent) {

        super(new TopRightCornerStarFactory());

        this.lastLevelNumber = lastLevelNumber;
        this.parent = parent;
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setLayout(null);

        nextLevelLabel = new JLabel("Next level is " + (lastLevelNumber + 1));
        nextLevelLabel.setForeground(Color.WHITE);
        nextLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nextLevelLabel.setSize(new Dimension(200, 50));
        add(nextLevelLabel);

        nextButton = new MenuButton("NEXT", 37);
        nextButton.addActionListener(this);
        add(nextButton);
    }

    @Override
    protected void init() {

        int width = (int) parent.getSize().getWidth();
        int middle = width / 2;

        nextLevelLabel.setLocation(middle - (nextLevelLabel.getWidth() / 2), 150);
        nextButton.setLocation(middle - (MenuButton.WIDTH / 2), 200);
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
