package map;

import java.awt.Color;

public class TerrainColor {

    public static Color getTerrainColors(TerrainType terrain) {
        switch (terrain) {
            case FOREST:
                return Color.GREEN;
            case FIELD:
                return Color.YELLOW;
            case MOUNTAIN:
                return Color.GRAY;
            case BRICK:
                return Color.ORANGE;
            case PASTURE:
                return Color.BLUE;
            case DESERT:
                return Color.LIGHT_GRAY;
            default:
                return Color.WHITE;
        }
    }
}
