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
import javax.swing.JPanel;

import com.trippin.chaosFlip.LevelLoader;
import com.trippin.chaosFlip.UserDataLoader;
import com.trippin.chaosFlip.Exception.CantLoadLevelException;

public class MainMenu
    extends JPanel
    implements ActionListener {

    private static final long serialVersionUID = 1L;

    private final JFrame parent;
    private final JButton startButton;
    private final JComboBox<Integer> levelSelector;
    private StarField starField;
    private boolean initialised;

    public MainMenu(JFrame parent) {

        this.parent = parent;
        setBackground(Color.BLACK);

        // Get level (current level + 1 or max level)
        int level = 0;
        try {
            level = new UserDataLoader().load().getLevel();
            if (new LevelLoader().exists(level + 1))
                level++;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Add level select
        Integer[] levelList = new Integer[level];
        int i = 0;
        do {
            levelList[i] = ++i;
        } while (i < level);
        levelSelector = new JComboBox<>(levelList);
        levelSelector.setSelectedIndex(levelList.length - 1); // Select the highest level
        add(levelSelector);

        // Add start button
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        add(startButton);

        starField = new StarField(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startButton)
            start();
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        init();

        starField.draw((Graphics2D) g, getWidth(), getHeight());
    }

    private void init() {

        if (initialised)
            return;

        starField.start();
    }

    @Override
    public void removeNotify() {

        super.removeNotify();

        starField.stop();
    }

    private void start() {

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
