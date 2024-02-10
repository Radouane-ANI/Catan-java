package map;

import javax.swing.*;
import java.awt.*;

public class MapComponent extends JPanel {
    private static final int TILE_SIZE = 70; // à ajuster
    private Board board;

    public MapComponent(Board board) {
        this.board = board;
        setPreferredSize(new Dimension(board.getWidth() * TILE_SIZE, board.getHeight() * TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Tile[][] tiles = board.getTiles();

        if (tiles != null) { 
            for (int i = 0; i < board.getWidth(); i++) {
                for (int j = 0; j < board.getHeight(); j++) {
                    Tile tile = tiles[i][j];

                    if (tile != null) {
                        int x = i * TILE_SIZE;
                        int y = j * TILE_SIZE;

                        // dessine les types de terrains
                        TerrainType terrain = tile.getTerrain();
                        if (terrain != null) {
                            switch (terrain) {
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
                    }
                        g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

                        // dessine numéro
                        if (tile.getTerrain() != TerrainType.DESERT) {
                            g.setColor(Color.BLACK);
                            g.drawString(Integer.toString(tile.getDiceNumber()), x + TILE_SIZE / 2, y + TILE_SIZE / 2);
                        }
                    }
                }
            }
        }
    }
}
