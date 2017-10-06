package com.trippin.chaosFlip.display;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.trippin.chaosFlip.LevelLoader;
import com.trippin.chaosFlip.Exception.CantLoadLevelException;
import com.trippin.chasoFlip.model.Level;

public class GamePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private ArenaPanel arena;
    private LevelLoader levelLoader = new LevelLoader();

    public GamePanel(JFrame parent, int levelNumber)
        throws CantLoadLevelException {

        setLayout(new BorderLayout());

        // Add the control panel to the bottom
        ControlsPanel controlsPanel = new ControlsPanel(parent);
        add(controlsPanel, BorderLayout.SOUTH);

        // Add the main game panel
        Level level = levelLoader.loadLevel(levelNumber);
        arena = new ArenaPanel(parent, level);
        add(arena, BorderLayout.CENTER);
    }
}
