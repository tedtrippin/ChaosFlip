package com.trippin.chaosFlip.display;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

import com.trippin.chaosFlip.LevelLoader;
import com.trippin.chaosFlip.UserDataLoader;
import com.trippin.chaosFlip.Exception.CantLoadLevelException;
import com.trippin.chaosFlip.model.Level;
import com.trippin.chaosFlip.model.Tile;
import com.trippin.chaosFlip.model.UserData;
import com.trippin.chaosFlip.starfield.CenterStarFactory;

public class ArenaPanel
    extends ChaosPanel
    implements ActionListener, MouseListener {

    private static final long serialVersionUID = 1L;

    // These are used for working out ratios to multiple tiles co-ords
    // and dimensions.
    private static final double DEFAULT_WIDTH = 640;
    private static final double DEFAULT_HEIGHT = 960;

    private final JFrame parent;
    private double ratioX;
    private double ratioY;
    private Level level;
    private boolean busy = false;
    private boolean levelComplete = false;
    private final MenuButton menuButton;
    private final JButton musicButton;
    private Timer flipTimer;

    ArenaPanel (JFrame parent, int levelNumber)
        throws CantLoadLevelException {

        super(parent, new CenterStarFactory());

        this.parent = parent;
        this.addMouseListener(this);
        this.setLayout(null);
        setBackground(Color.BLACK);

        LevelLoader levelLoader = new LevelLoader();
        this.level = levelLoader.loadLevel(levelNumber);

        menuButton = new MenuButton("MENU", 34);
        menuButton.addActionListener(this);
        add(menuButton);

        // Add music toggle button
        musicButton = new MusicButton();
        musicButton.addActionListener(this);
        add(musicButton);
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        init();

        Graphics2D g2D = (Graphics2D) g;

        level.getTiles().forEach(t -> t.paint(g2D));

        if (!levelComplete)
            return;

        int x = (parent.getWidth() / 2) - 65;
        g.drawString("LEVEL COMPLETE", x,  50);
    }

    @Override
    protected void init() {

        if (getWidth() == 0)
            return;

        ratioX = getWidth() / DEFAULT_WIDTH;
        ratioY = getHeight() / DEFAULT_HEIGHT;

        menuButton.setLocation(10, 10);
        musicButton.setLocation(getWidth() - 60, 10);

        // Initialise tiles with the ratios
        level.getTiles().forEach(t -> t.initForArena(ratioX, ratioY));
    }

    private void levelCompleted() {

        NextLevelPanel nextLevelPanel = new NextLevelPanel(level.getLevel(), parent);

        Container parentContainer = parent.getContentPane();
        parentContainer.removeAll();
        parentContainer.add(nextLevelPanel);
        parentContainer.validate();

        UserDataLoader userDataLoader = new UserDataLoader();
        try {
            UserData userData = userDataLoader.load();
            userData.setLevel(level.getLevel());
            userDataLoader.save(userData);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (busy)
            return;

        if (levelComplete)
            return;

        Optional<Tile> tileOptional = level.getTiles().stream()
            .filter(t -> t.isIn(e.getX(), e.getY()))
            .findFirst();

        if (!tileOptional.isPresent())
            return;

        Tile tile = tileOptional.get();
        tile.flip();
        tile.getDependents().forEach(Tile::flip);

        busy = true;

        flipTimer = new Timer(15, new FlipListener(tile));
        flipTimer.setRepeats(true);
        flipTimer.start();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == menuButton)
            goToMenu();
    }

    class FlipListener implements ActionListener {

        private Tile tile;

        FlipListener(Tile tile) {
            this.tile = tile;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (tile.step())
                return;

            flipTimer.stop();
            busy = false;

            // Check complete
            levelComplete = true;
            int tileIdx = level.getTiles().get(0).getTileIdx();
            for (Tile tile : level.getTiles()) {
                if (tileIdx != tile.getTileIdx()) {
                    levelComplete = false;
                    break;
                }
            }

            if (levelComplete) {
                Timer endLevelTimer = new Timer(2000, (a) -> levelCompleted());
                endLevelTimer.setRepeats(false);
                endLevelTimer.start();
            }
        }
    }
}