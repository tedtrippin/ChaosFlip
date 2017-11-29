package com.trippin.chaosFlip.model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;

public class Tile {

    private int id;
    private int x;
    private int y;
    private int width;
    private int height;
    private Image[] tileImages;
    private int tileIdx;
    private List<Tile> dependents;
    private int flipStep;
    private boolean flipping;

    // These are set when initiated, the fields are
    // multiplied by the arena ratios
    private int arenaX;
    private int arenaY;
    private int arenaWidth;
    private int arenaHeight;

    public Tile(int id, int x, int y, int width, int height, Image[] tileImages, int tileIdx) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tileImages = tileImages;
        this.tileIdx = tileIdx;
    }

    public void initForArena(double ratiox, double ratioy) {

        arenaWidth = (int) (width * ratiox);
        arenaHeight = (int) (height * ratioy);
        arenaX = ((int) (x * ratiox)) - (arenaWidth / 2);
        arenaY = ((int) (y * ratioy)) - (arenaHeight / 2);
    }

    public boolean isIn(int x, int y) {

        if (x < arenaX )
            return false;

        if (y < arenaY )
            return false;

        if (x > arenaX + arenaWidth)
            return false;

        if (y > arenaY + arenaHeight)
            return false;

        return true;
    }

    public void flip() {
        flipping = true;
    }

    public int getId() {
        return id;
    }

    public int getTileIdx() {
        return tileIdx;
    }

    public void setTileIdx(int tileIdx) {
        this.tileIdx = tileIdx;
    }

    public int getX() {
        return arenaX;
    }

    public int getY() {
        return arenaY;
    }

    public int getWidth() {
        return arenaWidth;
    }

    public int getHeight() {
        return arenaHeight;
    }

    public Image[] getTileImages() {
        return tileImages;
    }

    public List<Tile> getDependents() {
        return dependents;
    }

    public void setDependents(List<Tile> dependents) {
        this.dependents = dependents;
    }

    /**
     * Increases the degree-of-flip. Returns true if the tile
     * is still flipping.
     *
     * @return true if still flipping.
     */
    public boolean step() {

        dependents.forEach(Tile::step);

        if (flipping) {
            flipStep++;

            if (flipStep >= 10) {
                flipping = false;

                // Move to next tile
                tileIdx++;
                if (tileIdx >= tileImages.length)
                    tileIdx = 0;
            }

        } else if (flipStep > 0) {
            flipStep--;
        }

        return flipStep != 0;
    }

    public void paint(Graphics2D g) {

        Image img = tileImages[tileIdx];
        if (flipStep == 0) {
            // Draw the tile
            g.drawImage(img, arenaX, arenaY, arenaWidth, arenaHeight, null);

        } else {
            // Draw the flipping tile
            double delta = arenaHeight / 20d;
            int yOffset = (int) (delta * flipStep);
            int flipHeight = (int) (arenaHeight - (delta * 2 * flipStep));
            g.drawImage(img, arenaX, arenaY + yOffset, arenaWidth, flipHeight, null);
        }
    }
}
