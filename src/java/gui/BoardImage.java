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

public class BoardImage {

    private HashMap<TerrainType, ImageIcon> terrainImageMap;
    private HashMap<Player, ImageIcon> cityImageMap;
    private HashMap<Player, ImageIcon> settlementImageMap;

    public BoardImage() {
        terrainImageMap = createTerrainImageMap();
        cityImageMap = createColoredImageMap("src/ressources/city.png", ViewControleur.getPlayers(),
                CatanBoardView.TILE_SIZE / 2);
        settlementImageMap = createColoredImageMap("src/ressources/settlement.png", ViewControleur.getPlayers(),
                CatanBoardView.TILE_SIZE / 3);
    }

    private HashMap<TerrainType, ImageIcon> createTerrainImageMap() {
        int width = CatanBoardView.TILE_SIZE;
        int height = (int) (CatanBoardView.TILE_SIZE * 1.15);
        HashMap<TerrainType, ImageIcon> map = new HashMap<>();
        map.put(TerrainType.FOREST, createResizedImageIcon("/src/ressources/tuile_foret.png", width, height));
        map.put(TerrainType.FIELD, createResizedImageIcon("/src/ressources/tuile_champ.png", width, height));
        map.put(TerrainType.PASTURE, createResizedImageIcon("/src/ressources/tuile_plaine.png", width, height));
        map.put(TerrainType.BRICK, createResizedImageIcon("/src/ressources/tuile_carriere.png", width, height));
        map.put(TerrainType.MOUNTAIN, createResizedImageIcon("/src/ressources/tuile_montagne.png", width, height));
        map.put(TerrainType.DESERT, createResizedImageIcon("/src/ressources/tuile_desert.png", width,
                height));
        return map;
    }

    private ImageIcon createResizedImageIcon(String imagePath, int width, int height) {
        return new ImageIcon(new ImageIcon(getClass().getResource(imagePath)).getImage().getScaledInstance(width,
                height, Image.SCALE_SMOOTH));
    }

    private HashMap<Player, ImageIcon> createColoredImageMap(String imagePath, List<Player> players, int dimension) {
        HashMap<Player, ImageIcon> map = new HashMap<>();
        try {
            BufferedImage baseImage = ImageIO.read(new File(imagePath));
            for (Player player : players) {
                map.put(player, createColoredImageIcon(baseImage, player.getColor(), dimension));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private ImageIcon createColoredImageIcon(BufferedImage image, Color targetColor, int dimension) {
        BufferedImage coloredImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                if (rgb == 0) {
                    continue;
                }
                Color color = new Color(rgb);

                int red = (targetColor.getRed() + color.getRed()) / 2;
                int green = (targetColor.getGreen() + color.getGreen()) / 2;
                int blue = (targetColor.getBlue() + color.getBlue()) / 2;

                Color newColor = new Color(red, green, blue);
                coloredImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return new ImageIcon(coloredImage.getScaledInstance(dimension, -1, Image.SCALE_DEFAULT));
    }

    public ImageIcon getTerrainImageIcon(TerrainType terrain) {
        return terrainImageMap.get(terrain);
    }

    public ImageIcon getCityImageIcon(Player player) {
        return cityImageMap.get(player);
    }

    public ImageIcon getSettlementImageIcon(Player player) {
        return settlementImageMap.get(player);
    }
}
