package com.trippin.chaosFlip.model;

import java.util.List;

public class Level {

    private int level;
    private List<Tile> tiles;

    public Level(List<Tile> tiles, int level) {
        this.tiles = tiles;
        this.level = level;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public int getLevel() {
        return level;
    }
}
