package map;

import javax.swing.*;
import java.awt.*;

public class MapComponent extends JPanel {
    private static final int TILE_SIZE = 50; // taille à ajuster
    private Board board;

    // squelette du grahique de la map: ajoutouer les methodes, gettiles,getwidth et getheigth
    public MapComponent(Board board) {
        this.board = board;
        setPreferredSize(new Dimension(board.getWidth() * TILE_SIZE, board.getHeight() * TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Tile tile : board.getTiles()) {
            int x = tile.getX() * TILE_SIZE;
            int y = tile.getY() * TILE_SIZE;

            // Draw terrain type
            switch (tile.getTerrain()) {
                case FOREST:
                    g.setColor(Color.GREEN);
                    break;
                case FIELD:
                    g.setColor(Color.YELLOW);
                    break;
                case PASTURE:
                    g.setColor(Color.CYAN);
                    break;
                case BRICK:
                    g.setColor(Color.RED);
                    break;
                case MOUNTAIN:
                    g.setColor(Color.GRAY);
                    break;
                case DESERT:
                    g.setColor(Color.LIGHT_GRAY);
                    break;
            }
            g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

            // dessine le numéro obtenu au jet de dé
            if (tile.getTerrain() != TerrainType.DESERT) {
                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(tile.getDiceNumber()), x + TILE_SIZE / 2, y + TILE_SIZE / 2);
            }
        }
    }
}
