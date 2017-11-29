package com.trippin.chaosFlip.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class MenuButton extends JButton implements MouseListener {

    private static final long serialVersionUID = 1L;

    public final static int WIDTH = 100;
    public final static int HEIGHT = 50;
    public final static int ARC_RADIUS = 20;

    private int textOffset;
    private String text;
    protected boolean mouseIn = false;

    MenuButton() {
        super();
    }

    MenuButton(String text, int textOffset) {
        super();
        this.text = text;
        this.textOffset = textOffset;
        setSize(WIDTH, HEIGHT);
        setBorderPainted(false);
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(3));
        if (mouseIn) {
            g2d.fillRoundRect(1, 1, WIDTH-6, HEIGHT-6, ARC_RADIUS, ARC_RADIUS);
            g2d.setColor(Color.BLACK);
        } else {
            g2d.drawRoundRect(1, 1, WIDTH-6, HEIGHT-6, ARC_RADIUS, ARC_RADIUS);
        }
        g2d.drawString(text, textOffset, 27);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseIn = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseIn = false;
    }
}
