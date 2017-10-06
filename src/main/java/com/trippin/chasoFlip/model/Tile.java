package com.trippin.chasoFlip.model;

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

        tileIdx++;
        if (tileIdx >= tileImages.length)
            tileIdx = 0;

        // Flip the dependents
        dependents.forEach(Tile::flip);
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
}
