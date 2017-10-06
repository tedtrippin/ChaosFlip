package com.trippin.chaosFlip.json;

public class LevelPojo {

    private int level;
    private TilePojo[] tiles;

    public LevelPojo() {
    }

    public TilePojo[] getTiles() {
        return tiles;
    }

    public void setTiles(TilePojo[] tiles) {
        this.tiles = tiles;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
