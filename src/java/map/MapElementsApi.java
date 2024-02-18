package map;

import java.util.ArrayList;

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
