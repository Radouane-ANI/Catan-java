package src.java.gui;

import java.awt.Color;

import src.java.util.TerrainType;

class TerrainColor {
    private static final Color DARK_GREEN = new Color(0, 100, 0);
    private static final Color MUSTARD_YELLOW = new Color(255, 219, 88);
    private static final Color LIGHT_GREEN = new Color(144, 238, 144);
    private static final Color RED_ORANGE = new Color(255, 69, 0);
    private static final Color GREY = Color.GRAY;
    private static final Color GREY_ORANGE = new Color(255, 140, 0);

    public static Color getTerrainColors(TerrainType terrain) {
        switch (terrain) {
            case FOREST:
                return DARK_GREEN;
            case FIELD:
                return MUSTARD_YELLOW;
            case PASTURE:
                return LIGHT_GREEN;
            case BRICK:
                return RED_ORANGE;
            case MOUNTAIN:
                return GREY;
            case DESERT:
                return GREY_ORANGE;
            default:
                return Color.BLACK;
        }
    }
}
