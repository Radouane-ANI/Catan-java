package gui;

import javax.swing.ImageIcon;
import java.awt.Image;

import util.TerrainType;

class TerrainImage {
    private static ImageIcon FOREST;
    private static ImageIcon FIELD;
    private static ImageIcon PASTURE;
    private static ImageIcon BRICK;
    private static ImageIcon MOUNTAIN;
    private static ImageIcon DESERT;

    public TerrainImage() {
        FOREST = createResizedImageIcon("/src/ressources/tuile_foret.png", CatanBoardView.TILE_SIZE,
                (int) (CatanBoardView.TILE_SIZE * 1.15));
        FIELD = createResizedImageIcon("/src/ressources/tuile_champ.png", CatanBoardView.TILE_SIZE,
                (int) (CatanBoardView.TILE_SIZE * 1.15));
        PASTURE = createResizedImageIcon("/src/ressources/tuile_plaine.png", CatanBoardView.TILE_SIZE,
                (int) (CatanBoardView.TILE_SIZE * 1.15));
        BRICK = createResizedImageIcon("/src/ressources/tuile_carriere.png", CatanBoardView.TILE_SIZE,
                (int) (CatanBoardView.TILE_SIZE * 1.15));
        MOUNTAIN = createResizedImageIcon("/src/ressources/tuile_montagne.png", CatanBoardView.TILE_SIZE,
                (int) (CatanBoardView.TILE_SIZE * 1.15));
        DESERT = createResizedImageIcon("/src/ressources/tuile_desert.png", CatanBoardView.TILE_SIZE,
                (int) (CatanBoardView.TILE_SIZE * 1.15));
    }

    private ImageIcon createResizedImageIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
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
