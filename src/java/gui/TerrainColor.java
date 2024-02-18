package gui;

import java.awt.Color;

import map.TerrainType;

class TerrainColor {
    private static final Color[] TERRAIN_COLORS = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.RED, Color.GRAY, Color.ORANGE};

    public static Color getTerrainColors(TerrainType terrain) {
        return TERRAIN_COLORS[terrain.ordinal()];
    }
}
