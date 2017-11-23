package com.trippin.chaosFlip;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.trippin.chaosFlip.Exception.CantLoadLevelException;
import com.trippin.chaosFlip.json.LevelPojo;
import com.trippin.chaosFlip.json.TilePojo;
import com.trippin.chasoFlip.model.Level;
import com.trippin.chasoFlip.model.Tile;

public class LevelLoader {

    private final static String LEVELS_PATH = "levels/";

    private Gson gson = new Gson();

    /**
     * Loads the requested level.
     *
     * Creates a level object from the corresponding
     * JSON and tile images.
     *
     * @param level
     * @return
     * @throws CantLoadLevelException
     */
    public Level loadLevel(int level)
        throws CantLoadLevelException {

        String path = LEVELS_PATH + String.valueOf(level) + ".json";

        try (
            InputStream in = this.getClass().getResourceAsStream(path);
            Reader reader = new InputStreamReader(in);
        ) {

            LevelPojo levelPojo = gson.fromJson(reader, LevelPojo.class);

            return convert(levelPojo);

        } catch (Exception ex) {
            throw new CantLoadLevelException("Couldnt load level[" + level + "]", ex);
        }
    }

    /**
     * returns true if the requested level exists.
     *
     * @param level
     * @return
     */
    public boolean exists(int level) {

        String path = LEVELS_PATH + String.valueOf(level) + ".json";
        return this.getClass().getResource(path) != null;
    }

    /**
     * Converts a level POJO to a level object;
     *
     * @param levelPojo
     * @return
     * @throws IOException
     */
    private Level convert(LevelPojo levelPojo)
        throws IOException {

        List<Tile> tiles = new ArrayList<>();
        Map<String, Image> tileMap = new HashMap<>();

        for (TilePojo tp : levelPojo.getTiles()) {
            tiles.add( convert(tp, tileMap) );
        }

        assignDependents(tiles, levelPojo.getTiles());

        return new Level(tiles, levelPojo.getLevel());
    }

    /**
     * Convert a tile POJO into a tile object.
     *
     * @param tp
     * @param tileMap
     * @return
     * @throws IOException
     */
    private Tile convert(TilePojo tp, Map<String, Image> tileMap)
        throws IOException {

        Image[] tileImages = convert(tp.getTiles(), tileMap);
        return new Tile(tp.getId(), tp.getX(), tp.getY(), tp.getWidth(), tp.getHeight(),
            tileImages, tp.getStart());
    }

    /**
     * If map doesnt contain the tile then loads it.
     *
     * @param tileNames
     * @param tileMap
     * @return
     * @throws IOException
     */
    private Image[] convert(String[] tileNames, Map<String, Image> tileMap)
        throws IOException {

        Image[] images = new Image[tileNames.length];

        int idx = 0;
        for (String s : tileNames) {
            if (tileMap.containsKey(s)) {
                images[idx++] = tileMap.get(s);
            } else {
                try (InputStream in = this.getClass().getResourceAsStream("tiles/" + s + ".png")) {
                    Image i = ImageIO.read(in);
                    tileMap.put(s, i);
                    images[idx++] = i;
                }
            }
        }
        return images;
    }

    /**
     * Resolves the tile dependents.
     *
     * @param tiles
     */
    private void assignDependents(List<Tile> tiles, TilePojo[] pojos) {

        Map<Integer, Tile> tileMap = tiles.stream()
            .collect( Collectors.toMap(Tile::getId, t -> t) );

        for (TilePojo pojo : pojos) {
            List<Tile> tileDependents = new ArrayList<>();
            for (Integer i : pojo.getDependents()) {
                tileDependents.add(tileMap.get(i));
            }

            Tile tile = tileMap.get(pojo.getId());
            tile.setDependents(tileDependents);
        }
    }
}
