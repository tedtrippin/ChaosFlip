package com.trippin.chaosFlip.display;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.trippin.chaosFlip.UserDataLoader;
import com.trippin.chasoFlip.model.Level;
import com.trippin.chasoFlip.model.Tile;
import com.trippin.chasoFlip.model.UserData;

public class ArenaPanel
    extends JPanel
    implements MouseListener {

    private static final long serialVersionUID = 1L;

    // These are used for working out ratios to multiple tiles co-ords
    // and dimensions.
    private static final double DEFAULT_WIDTH = 640;
    private static final double DEFAULT_HEIGHT = 960;

    private final JFrame parent;
    private double ratioX;
    private double ratioY;
    private Level level;
    private StarField starField;
    private boolean levelComplete = false;
    private boolean initialised = false;

    ArenaPanel (JFrame parent, Level level) {

        this.parent = parent;
        this.level = level;
        this.addMouseListener(this);
        setBackground(Color.BLACK);

        starField = new StarField(this);

//        URL backgroundUrl = ArenaMask.class.getClassLoader().getResource("background.jpg");
//        try {
//            background = ImageIO.read(backgroundUrl);
//            background = background.getScaledInstance(engine.getArenaWidth(), engine.getArenaHeight(), 0);
//        } catch (IOException ex) {
//            ex.printStackTrace(System.err);
//        }
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        init();

        Graphics2D g2D = (Graphics2D) g;

        starField.draw(g2D, getWidth(), getHeight());

//        g.drawImage(background, 0, 0, null);

        level.getTiles().forEach(t -> paintTile(t, g2D));

        if (!levelComplete)
            return;

        g.drawString("LEVEL COMPLETE", 25,  50);
    }

    private void init() {

        if (getWidth() == 0)
            return;

        if (initialised)
            return;

        starField.start();

        ratioX = getWidth() / DEFAULT_WIDTH;
        ratioY = getHeight() / DEFAULT_HEIGHT;

        // Initialise tiles with the ratios
        level.getTiles().forEach(t -> t.initForArena(ratioX, ratioY));

        initialised = true;
    }

    private void paintTile(Tile tile, Graphics2D g2D) {

        int i = tile.getTileIdx();
        Image img = tile.getTileImages()[i];
        g2D.drawImage(
            img,
            tile.getX(),
            tile.getY(),
            tile.getWidth(),
            tile.getHeight(),
            null);
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
    public void removeNotify() {

        super.removeNotify();

        starField.stop();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (levelComplete)
            return;

        Optional<Tile> tileOptional = level.getTiles().stream()
            .filter(t -> t.isIn(e.getX(), e.getY()))
            .findFirst();

        if (!tileOptional.isPresent())
            return;

        tileOptional.get().flip();

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

        super.repaint();
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
}