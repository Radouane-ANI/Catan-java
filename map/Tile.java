package map;

import logic.Thief;

public class Tile {
    private int diceNumber;
    private Thief thief = null;
    private TerrainType terrain = null;


    public int getDiceNumber() {
        return diceNumber;
    }

    public TerrainType getTerrain() {
        return terrain;
    }

    public Thief getThief() {
        return thief;
    }
}
