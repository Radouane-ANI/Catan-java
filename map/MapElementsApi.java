package map;

import java.util.ArrayList;

import map.Tile;

/**
 * MapElementsInterface
 */
class MapElementsApi {
    public static Tile[] getTileArray() {
        return Tile.getTilesIntern();
    }

    public static Node[] getNodeArray() {
        return Node.getNodesIntern();
    }

    public static ArrayList<Edge> getEdgeArray() {
        return Edge.getEdgesIntern();
    }
    
}
