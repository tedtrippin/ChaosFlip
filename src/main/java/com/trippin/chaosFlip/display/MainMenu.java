package com.trippin.chaosFlip.display;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.trippin.chaosFlip.LevelLoader;
import com.trippin.chaosFlip.UserDataLoader;
import com.trippin.chaosFlip.Exception.CantLoadLevelException;
import com.trippin.chaosFlip.starfield.RightStarFactory;
import com.trippin.chaosFlip.starfield.StarField;

public class MainMenu
    extends JPanel
    implements ActionListener {

    private static final long serialVersionUID = 1L;

    private final JFrame parent;
    private final JButton startButton;
    private final JComboBox<Integer> levelSelector;
    private StarField starField;

    public MainMenu(JFrame parent) {

        this.parent = parent;
        setBackground(Color.BLACK);
//        setLayout(null);

        // Get level (current level + 1 or max level)
        int level = 0;
        try {
            level = new UserDataLoader().load().getLevel();
            if (new LevelLoader().exists(level + 1))
                level++;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Add level select label
        JLabel selectLevelLabel = new JLabel("Choose level");
        selectLevelLabel.setLocation(100,  100);
        selectLevelLabel.setSize(100,  25);
        selectLevelLabel.setForeground(Color.WHITE);
        add(selectLevelLabel);

        // Add level select
        Integer[] levelList = new Integer[level];
        int i = 0;
        do {
            levelList[i] = ++i;
        } while (i < level);
        levelSelector = new JComboBox<>(levelList);
        levelSelector.setSelectedIndex(levelList.length - 1); // Select the highest level
        add(levelSelector);
        levelSelector.setLocation(100,  125);
        levelSelector.setSize(100,  25);

        // Add start button
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        add(startButton);
        startButton.setLocation(100, 200);
        startButton.setSize(100,  25);
    }

    public void start() {
        starField = new StarField(this, new RightStarFactory(this));
        starField.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startButton)
            startGame();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        starField.draw((Graphics2D) g);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        starField.stop();
    }

    public void startGame() {

        // Create the game panel
        GamePanel gamePanel;
        try {
            int level = levelSelector.getSelectedIndex() + 1;

            gamePanel = new GamePanel(parent, level);
            Container parentContainer = parent.getContentPane();
            parentContainer.removeAll();
            parentContainer.add(gamePanel);
            parentContainer.validate();
        } catch (CantLoadLevelException ex) {
            ex.printStackTrace();
        }
    }
}
