package gui;

import javax.swing.ImageIcon;

import controleur.ViewControleur;
import logic.Player;
import util.TerrainType;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class BoardImage {
    private HashMap<TerrainType, ImageIcon> tuileImageMap = new HashMap<>();
    private HashMap<Player, ImageIcon> cityImageMap = new HashMap<>();
    private HashMap<Player, ImageIcon> settlImageMap = new HashMap<>();

    public BoardImage() {
        tuileImageMap.put(TerrainType.FOREST,
                createResizedImageIcon("/src/ressources/tuile_foret.png", CatanBoardView.TILE_SIZE,
                        (int) (CatanBoardView.TILE_SIZE * 1.15)));
        tuileImageMap.put(TerrainType.FIELD,
                createResizedImageIcon("/src/ressources/tuile_champ.png", CatanBoardView.TILE_SIZE,
                        (int) (CatanBoardView.TILE_SIZE * 1.15)));
        tuileImageMap.put(TerrainType.PASTURE,
                createResizedImageIcon("/src/ressources/tuile_plaine.png", CatanBoardView.TILE_SIZE,
                        (int) (CatanBoardView.TILE_SIZE * 1.15)));
        tuileImageMap.put(TerrainType.BRICK,
                createResizedImageIcon("/src/ressources/tuile_carriere.png", CatanBoardView.TILE_SIZE,
                        (int) (CatanBoardView.TILE_SIZE * 1.15)));
        tuileImageMap.put(TerrainType.MOUNTAIN,
                createResizedImageIcon("/src/ressources/tuile_montagne.png", CatanBoardView.TILE_SIZE,
                        (int) (CatanBoardView.TILE_SIZE * 1.15)));
        tuileImageMap.put(TerrainType.DESERT,
                createResizedImageIcon("/src/ressources/tuile_desert.png", CatanBoardView.TILE_SIZE,
                        (int) (CatanBoardView.TILE_SIZE * 1.15)));

        try {
            File cityFile = new File("src/ressources/11.png");
            BufferedImage imageCity = ImageIO.read(cityFile);
            File SettlFile = new File("src/ressources/8.png");
            BufferedImage imageSettl = ImageIO.read(SettlFile);

            generateColorImageMap(cityImageMap, imageCity, ViewControleur.getPlayers());
            generateColorImageMap(settlImageMap, imageSettl, ViewControleur.getPlayers());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ImageIcon createResizedImageIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public ImageIcon getTerrainImageIcons(TerrainType terrain) {
        return tuileImageMap.get(terrain);
    }

    public ImageIcon getCityImageIcons(Player p) {
        return cityImageMap.get(p);
    }

    public ImageIcon getSettlementImageIcons(Player p) {
        return settlImageMap.get(p);
    }

    private HashMap<Player, ImageIcon> generateColorImageMap(HashMap<Player, ImageIcon> colorImageMap,
            BufferedImage image, List<Player> players) {
        for (Player player : players) {
            ImageIcon coloredIcon = createColoredImageIcon(image, player.getColor());
            colorImageMap.put(player, coloredIcon);
        }
        return colorImageMap;
    }

    private ImageIcon createColoredImageIcon(BufferedImage image, Color colorCible) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                if (rgb == 0) {
                    continue;
                }
                Color colorAct = new Color(rgb);

                int red = (colorCible.getRed() + colorAct.getRed()) / 2;
                int green = (colorCible.getGreen() + colorAct.getGreen()) / 2;
                int blue = (colorCible.getBlue() + colorAct.getBlue()) / 2;

                Color newColor = new Color(red, green, blue);

                filteredImage.setRGB(x, y, newColor.getRGB());
            }
        }
        width = CatanBoardView.TILE_SIZE / 2;

        return new ImageIcon(filteredImage.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }
}
