package map;

import logic.Thief;

public class Tile extends Vector {
    private int diceNumber = 0;
    private Thief thief = null;
    private TerrainType terrain = TerrainType.DESERT;

    Tile(int x, int y, int diceNumber, TerrainType terrain) {
        super(x, y);
        this.diceNumber = diceNumber;
        this.terrain = terrain;
    }

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
