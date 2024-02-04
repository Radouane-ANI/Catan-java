package map;

import logic.Thief;

public class Tile {
    private int diceNumber;
    private Thief thief = null;
    private TerrainType terrain = null;

    private Edge[] neighborEdge = new Edge[6];

    private Node[] neighborNode = new Node[6];

    private Tile[] neighborTile = new Tile[6];

    Tile(int diceNumber, TerrainType terrain) {
        this.diceNumber = diceNumber;
        this.terrain = terrain;
    }

    void setTileConnection(Tile t, Direction d) {
        neighborTile[d.ordinal()] = t;
    }

    Tile getTileConnection(Direction d) {
        return neighborTile[d.ordinal()];
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
