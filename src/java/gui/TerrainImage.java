package gui;

import javax.swing.ImageIcon;

import util.TerrainType;

class TerrainImage {
    private static ImageIcon FOREST;
    private static ImageIcon FIELD;
    private static ImageIcon PASTURE;
    private static ImageIcon BRICK;
    private static ImageIcon MOUNTAIN;
    private static ImageIcon DESERT;

    public TerrainImage() {
        FOREST = new ImageIcon(getClass().getResource("/src/ressources/tuile_foret.png"));
        FIELD = new ImageIcon(getClass().getResource("/src/ressources/tuile_champ.png"));
        PASTURE = new ImageIcon(getClass().getResource("/src/ressources/tuile_plaine.png"));
        BRICK = new ImageIcon(getClass().getResource("/src/ressources/tuile_carriere.png"));
        MOUNTAIN = new ImageIcon(getClass().getResource("/src/ressources/tuile_montagne.png"));
        DESERT = new ImageIcon(getClass().getResource("/src/ressources/tuile_desert.png"));
    }

    public ImageIcon getTerrainImageIcons(TerrainType terrain) {
        switch (terrain) {
            case FOREST:
                return FOREST;
            case FIELD:
                return FIELD;
            case PASTURE:
                return PASTURE;
            case BRICK:
                return BRICK;
            case MOUNTAIN:
                return MOUNTAIN;
            case DESERT:
                return DESERT;
            default:
                return DESERT;
        }
    }
}
