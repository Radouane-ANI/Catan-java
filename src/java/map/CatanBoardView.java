package map;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class CatanBoardView extends JPanel {

    private static final int TILE_SIZE = 60;
    private static final int[] ROW_TILE_COUNTS = {3, 4, 5, 4, 3};
    private static final int HEX_WIDTH = (ROW_TILE_COUNTS[2] + ROW_TILE_COUNTS[3] - 1) * TILE_SIZE;
    private static final int HEX_HEIGHT = (int) ((ROW_TILE_COUNTS[1] + ROW_TILE_COUNTS[2] + ROW_TILE_COUNTS[3]) * Math.sqrt(3) / 2 * TILE_SIZE);
    private static final Color[] TERRAIN_COLORS = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.RED, Color.GRAY, Color.ORANGE};

    private Tile[][] board;

    public CatanBoardView(Tile[][] board) {
        this.board = board;
        setPreferredSize(new Dimension(HEX_WIDTH, HEX_HEIGHT));
    }

    @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();

            int maxIndex = ROW_TILE_COUNTS.length - 1;

            for (int x = 0; x <= maxIndex; x++) {
                for (int y = 0; y < ROW_TILE_COUNTS[x]; y++) {
                    if (board[x][y] == null) {
                        System.out.println("board[" + x + "][" + y + "] is null");
                    } else {
                        double xOffset = (ROW_TILE_COUNTS[2] + ROW_TILE_COUNTS[3] - 1 - ROW_TILE_COUNTS[x]) / 2.0 * TILE_SIZE;
                        double yOffset = x * Math.sqrt(3) / 2 * TILE_SIZE + y * Math.sqrt(3) * TILE_SIZE;

                        Shape hexagon = createHexagon(xOffset, yOffset, TILE_SIZE);
                        TerrainType terrain = board[x][y].getTerrain();
                        g2d.setColor(TERRAIN_COLORS[terrain.ordinal()]);
                        g2d.fill(hexagon);
                        g2d.setColor(Color.BLACK);
                        g2d.draw(hexagon);
                    }
                }
            }

            g2d.dispose();
        }


    private Shape createHexagon(double xOffset, double yOffset, double size) {
        Path2D.Double hexagon = new Path2D.Double();
        for (int i = 0; i < 6; i++) {
            double angleRadians = Math.toRadians(60 * i);
            double x = xOffset + size * Math.cos(angleRadians);
            double y = yOffset + size * Math.sin(angleRadians);
            if (i == 0) {
                hexagon.moveTo(x, y);
            } else {
                hexagon.lineTo(x, y);
            }
        }
        hexagon.closePath();
        return hexagon;
    }
}
