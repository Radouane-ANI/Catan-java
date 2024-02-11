package map;

import javax.swing.*;
import java.awt.*;

public class MapComponent extends JPanel {
    private static final int TILE_SIZE = 70; // à changer
    private static final int HEX_RADIUS = TILE_SIZE / 2;

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
                        int cx = i * TILE_SIZE + TILE_SIZE / 2;
                        int cy = j * TILE_SIZE + TILE_SIZE / 2;

                        Polygon hexagon = createHexagon(cx, cy);
                        g.setColor(getTerrainColor(tile.getTerrain()));
                        g.fillPolygon(hexagon);

                        if (tile.getTerrain() != TerrainType.DESERT) {
                            g.setColor(Color.BLACK);
                            drawCenteredString(g, Integer.toString(tile.getDiceNumber()), cx, cy);
                        }
                    }
                }
            }
        }
    }

    private Polygon createHexagon(int cx, int cy) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.PI / 3 * (i + 0.5);
            int x = (int) (cx + HEX_RADIUS * Math.cos(angle));
            int y = (int) (cy + HEX_RADIUS * Math.sin(angle));
            hexagon.addPoint(x, y);
        }
        return hexagon;
    } 

    private Color getTerrainColor(TerrainType terrain) {
        if (terrain != null) {
            switch (terrain) {
                case FOREST:
                    return Color.GREEN;
                case FIELD:
                    return Color.YELLOW;
                case PASTURE:
                    return Color.CYAN;
                case BRICK:
                    return Color.RED;
                case MOUNTAIN:
                    return Color.GRAY;
                case DESERT:
                    return Color.LIGHT_GRAY;
                default:
                    return Color.PINK;
            }
        } else {
            return Color.WHITE;
        }
    }

    private void drawCenteredString(Graphics g, String text, int cx, int cy) {
        FontMetrics metrics = g.getFontMetrics();
        int x = cx - metrics.stringWidth(text) / 2;
        int y = cy - metrics.getHeight() / 2 + metrics.getAscent();
        g.drawString(text, x, y);
    }
}
