package com.trippin.chaosFlip.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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

    private final JLabel nextLevelLabel;
    private final JButton nextButton;
    private final JButton menuButton;

    public NextLevelPanel(int lastLevelNumber, JFrame parent) {

        super(parent, new TopRightCornerStarFactory());

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

        menuButton = new MenuButton("MENU", 34);
        menuButton.addActionListener(this);
        add(menuButton);
    }

    @Override
    protected void init() {

        int width = (int) parent.getSize().getWidth();
        int middle = width / 2;

        nextLevelLabel.setLocation(middle - (nextLevelLabel.getWidth() / 2), 150);
        nextButton.setLocation(middle - (MenuButton.WIDTH / 2), 200);
        menuButton.setLocation(middle - (MenuButton.WIDTH / 2), 350);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == nextButton)
            goToLevel(lastLevelNumber + 1);
        else if (e.getSource() == menuButton)
            goToMenu();
    }
}
