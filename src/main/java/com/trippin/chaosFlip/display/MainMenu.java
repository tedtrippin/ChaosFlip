package com.trippin.chaosFlip.display;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.trippin.chaosFlip.LevelLoader;
import com.trippin.chaosFlip.UserDataLoader;
import com.trippin.chaosFlip.starfield.RightStarFactory;

public class MainMenu
    extends ChaosPanel
    implements ActionListener {

    private static final long serialVersionUID = 1L;

    // Menu components
    private final JButton startButton;
    private final JButton exitButton;
    private final JLabel selectLevelLabel;
    private final JComboBox<Integer> levelSelector;

    public MainMenu(JFrame parent) {

        super(parent, new RightStarFactory());

        setBackground(Color.BLACK);
        setLayout(null);

        // Get level (current level + 1 or max level)
        int level = 0;
        try {
            level = new UserDataLoader().load().getLevel();
            if (new LevelLoader().exists(level + 1))
                level++;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Add start button
        startButton = new MenuButton("START", 32);
        startButton.addActionListener(this);
        add(startButton);

        // Add level select label
        selectLevelLabel = new JLabel("Choose level");
        selectLevelLabel.setForeground(Color.WHITE);
        selectLevelLabel.setSize(200, 25);
        selectLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(selectLevelLabel);

        // Add level select
        Integer[] levelList = new Integer[level];
        int i = 0;
        do {
            levelList[i] = ++i;
        } while (i < level);
        levelSelector = new JComboBox<>(levelList);
        levelSelector.setSelectedIndex(levelList.length - 1); // Select the highest level
        levelSelector.setSize(MenuButton.WIDTH, 25);
        add(levelSelector);

        // Add exitbutton
        exitButton = new MenuButton("EXIT", 39);
        exitButton.addActionListener(this);
        add(exitButton);
    }

    @Override
    protected void init() {

        int width = (int) parent.getSize().getWidth();
        int middle = width / 2;

        int buttonX = middle - (MenuButton.WIDTH / 2);
        startButton.setLocation(buttonX, 200);
        levelSelector.setLocation(buttonX, 310);
        exitButton.setLocation(buttonX, 500);

        selectLevelLabel.setLocation(middle - (selectLevelLabel.getWidth() / 2), 280);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startButton)
            goToLevel(levelSelector.getSelectedIndex() + 1);
        else if (e.getSource() == exitButton)
        	System.exit(0);
    }
}
