package map;

import java.util.ArrayList;

/**
 * MapElementsInterface
 */
class MapElementsApi {
    public static Tile[] getTileArray() {
        return Tile.getTilesIntern();
    }

    public static ArrayList<Tile> getTileByDiceNumberArray(int n) {
        ArrayList<Tile> res = new ArrayList<Tile>();
        for (Tile t : Tile.getTilesIntern()) {
            if (t.getDiceNumber() == n) {
                res.add(t);
            }
        }

        return res;
    }

    public static Node[] getNodeArray() {
        return Node.getNodesIntern();
    }

    public static ArrayList<Edge> getEdgeArray() {
        return Edge.getEdgesIntern();
    }
    
}
