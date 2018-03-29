package com.trippin.chaosFlip.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.trippin.chaosFlip.audio.BackgroundMusic;

public class MusicButton extends MenuButton {

    private static final long serialVersionUID = 1L;

    private Image imgOn;
    private Image imgOnHover;
    private Image imgOff;
    private Image imgOffHover;
    private boolean initialised;

    MusicButton() {
        super();
        setSize(HEIGHT, HEIGHT); // TODO: hmmmm
        setBorderPainted(false);
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {

        if (!initialised)
            init();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));

        if (mouseIn) {
            g2d.fillRoundRect(1, 1, HEIGHT-6, HEIGHT-6, ARC_RADIUS, ARC_RADIUS);
            if (BackgroundMusic.instance().isPlaying())
                g.drawImage(imgOnHover, 8, 8, null);
            else
                g.drawImage(imgOffHover, 8, 8, null);
        } else {
            g2d.drawRoundRect(1, 1, HEIGHT-6, HEIGHT-6, ARC_RADIUS, ARC_RADIUS);
            if (BackgroundMusic.instance().isPlaying())
                g.drawImage(imgOn, 8, 8, null);
            else
                g.drawImage(imgOff, 8, 8, null);
        }
    }

    private void init() {

        initialised = true;

        imgOn = loadImage("music.png");
        imgOnHover = loadImage("musichover.png");
        imgOff = loadImage("nomusic.png");
        imgOffHover= loadImage("nomusichover.png");
    }

    private Image loadImage(String name) {

        try (InputStream in = this.getClass().getResourceAsStream("/img/" + name)) {
            return  ImageIO.read(in);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (BackgroundMusic.instance().isPlaying())
            BackgroundMusic.instance().stop();
        else
            BackgroundMusic.instance().start();
    }
}
